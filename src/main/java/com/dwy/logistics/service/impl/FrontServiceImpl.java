package com.dwy.logistics.service.impl;

import com.dwy.logistics.mapper.CarMapper;
import com.dwy.logistics.mapper.GoodsMapper;
import com.dwy.logistics.mapper.OrdersMapper;
import com.dwy.logistics.mapper.PlaceMapper;
import com.dwy.logistics.model.dto.front.CarFrontDTO;
import com.dwy.logistics.model.dto.front.PlaceFrontDTO;
import com.dwy.logistics.model.dto.front.RouteFrontDTO;
import com.dwy.logistics.model.dto.front.TransportFrontDTO;
import com.dwy.logistics.model.entities.*;
import com.dwy.logistics.service.IFrontService;
import com.dwy.logistics.service.IRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/12 15:44
 */
@Service
@Slf4j
public class FrontServiceImpl implements IFrontService {


    @Resource
    OrdersMapper ordersMapper;

    @Resource
    PlaceMapper placeMapper;

    @Resource
    GoodsMapper goodsMapper;

    @Resource
    CarMapper carMapper;

    @Resource
    IRouteService routeService;


    @Override
    public List<PlaceFrontDTO> getPlaceFrontDTO(Date date) {
        List<PlaceFrontDTO> placeFrontDTOS = new ArrayList<>();
        OrdersExample example = new OrdersExample();
        OrdersExample.Criteria criteria = example.createCriteria();
        criteria.andTimeEqualTo(date);
        List<Orders> ordersList = ordersMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(ordersList)){
            return placeFrontDTOS;
        }
        PlaceFrontDTO placeFrontDTO = new PlaceFrontDTO();
        PlaceKey placeKey = new PlaceKey();
        placeKey.setUid(ordersList.get(0).getStartPlaceID());
        Place startPlace = placeMapper.selectByPrimaryKey(placeKey);
        placeFrontDTO.setId(startPlace.getUid());
        placeFrontDTO.setLat(startPlace.getLat());
        placeFrontDTO.setLng(startPlace.getLng());
        placeFrontDTO.setVolume(getTotalVolume(date));
        placeFrontDTO.setName(startPlace.getName());
        placeFrontDTOS.add(placeFrontDTO);
        Map<String, Double> placeIDAndVolumeMap = getPlaceIDAndVolumeMap(date);
        Iterator<Map.Entry<String,Double>> iterator = placeIDAndVolumeMap.entrySet().iterator();
        while (iterator.hasNext()){
            Place endPlace = new Place();
            PlaceFrontDTO endPlaceFrontDTO = new PlaceFrontDTO();
            Map.Entry<String,Double> entry = iterator.next();
            placeKey.setUid(entry.getKey());
            endPlace = placeMapper.selectByPrimaryKey(placeKey);
            endPlaceFrontDTO.setId(endPlace.getUid());
            endPlaceFrontDTO.setLat(endPlace.getLat());
            endPlaceFrontDTO.setLng(endPlace.getLng());
            endPlaceFrontDTO.setVolume(entry.getValue());
            endPlaceFrontDTO.setName(endPlace.getName());
            placeFrontDTOS.add(endPlaceFrontDTO);
        }
        log.info("placeFrontDTOS:"+placeFrontDTOS);
        return placeFrontDTOS;
    }

    @Override
    public List<CarFrontDTO> getCarFrontDTO(Date date) {
        List<Double> carVolumeList = getCarVolumeList();
        carVolumeList.sort(Comparator.comparing(Double::doubleValue));
        Map<List<List<Double>>, Double> highestLoadRateListMap = getHighestLoadRateList(carVolumeList, getTotalVolume(date));
        log.info("highestLoadRateListMap" +highestLoadRateListMap);
        return getResultList(highestLoadRateListMap);
    }

    @Override
    public List<TransportFrontDTO> getTransportFrontDTO(Date date) {
        List<TransportFrontDTO> resultList = new ArrayList<>();
        List<PlaceFrontDTO> placeFrontDTOS = getPlaceFrontDTO(date);
        List<CarFrontDTO> carFrontDTOS = getCarFrontDTO(date);
        Collections.sort(placeFrontDTOS);
        Collections.sort(carFrontDTOS);
        PlaceFrontDTO origin = placeFrontDTOS.get(0);
        int carFrontDTOPosition = 0 ;
        CarFrontDTO carFrontDTO = oneCarToOnePlace(placeFrontDTOS, resultList, carFrontDTOS, carFrontDTOPosition);//当前最大体积的车一次能送达
        while (carFrontDTOS.get(carFrontDTOPosition) != carFrontDTO){
            carFrontDTOPosition++;
        }
        List<Map<PlaceFrontDTO, List<PlaceFrontDTO>>> inDistancePlaceList = new ArrayList<>();
        double placeMinDistance = minDistancePlaceListToPlaceList(placeFrontDTOS,placeFrontDTOS);
        double placeMaxDistance = maxDistancePlaceListToPlaceList(placeFrontDTOS,placeFrontDTOS);
        double carAccount = getCarAccount(carFrontDTOS);
        double carNowVolume = carFrontDTO.getVolume();
        //将距离为最小距离加最大距离和最小距离差值除以汽车数量的作为一个范围，每次增加最大距离和最小距离差值除以汽车数量。
        for (double distance = placeMinDistance + (placeMaxDistance-placeMinDistance)/carAccount ; distance<=placeMaxDistance ; distance = distance + (placeMaxDistance-placeMinDistance)/carAccount) {
            inDistancePlaceList.clear();
            //获得距离内的地点list
            inDistancePlaceList = getInDistancePlaceList(placeFrontDTOS , distance , origin);
            for(Map<PlaceFrontDTO, List<PlaceFrontDTO>> inDistancePlaceMap : inDistancePlaceList){
                Map.Entry<PlaceFrontDTO, List<PlaceFrontDTO>> placeFrontDTOListEntry = inDistancePlaceMap.entrySet().iterator().next();//只有一个key
                if (placeFrontDTOListEntry.getValue().size() != 0){
                    PlaceFrontDTO startPlace = placeFrontDTOListEntry.getKey();
                    List<PlaceFrontDTO> endPlaces = placeFrontDTOListEntry.getValue();
                    List<PlaceFrontDTO> allEndPlaces = new ArrayList<>(endPlaces);
                    allEndPlaces.add(startPlace);
                    sortPlaceFrontDTOSByDistance(origin,allEndPlaces);
                    double totalVolume = startPlace.getVolume();
                    for (PlaceFrontDTO endPlace : endPlaces){
                        totalVolume = totalVolume+endPlace.getVolume();
                    }
                    while (totalVolume >= carFrontDTO.getVolume()){
                        PlaceFrontDTO startPlaceDTO = new PlaceFrontDTO();
                        List<PlaceFrontDTO> removeList = new ArrayList<>();
                        startPlaceDTO = origin;
                        for (PlaceFrontDTO placeFrontDTO : allEndPlaces){
                            if (placeFrontDTO.getVolume() == 0.0){
                                removeList.add(placeFrontDTO);
                            }
                        }
                        for (PlaceFrontDTO placeFrontDTO : removeList){
                            allEndPlaces.remove(placeFrontDTO);
                        }
                        if (allEndPlaces.size() == 0){
                            break;
                        }
                        PlaceFrontDTO firstEndPlaceDTO = allEndPlaces.get(0);
                        TransportFrontDTO transportFrontDTO = new TransportFrontDTO();
                        transportFrontDTO.setStartLat(startPlaceDTO.getLat());
                        transportFrontDTO.setStartLng(startPlaceDTO.getLng());
                        transportFrontDTO.setEndLat(firstEndPlaceDTO.getLat());
                        transportFrontDTO.setEndLng(firstEndPlaceDTO.getLng());
                        transportFrontDTO.setVolume(firstEndPlaceDTO.getVolume());
                        transportFrontDTO.setStartName(startPlaceDTO.getName());
                        transportFrontDTO.setEndName(firstEndPlaceDTO.getName());
                        origin.setVolume(origin.getVolume()-transportFrontDTO.getVolume());
                        resultList.add(transportFrontDTO);
                        carNowVolume = carNowVolume - firstEndPlaceDTO.getVolume();
                        firstEndPlaceDTO.setVolume(0.0);
                        startPlaceDTO = firstEndPlaceDTO;
                        for (int i = 1 ; i < allEndPlaces.size() ; i++){
                            if (allEndPlaces.get(i).getVolume() <= carNowVolume){
                                TransportFrontDTO transportFrontDTO1 = new TransportFrontDTO();
                                transportFrontDTO1.setStartLat(startPlaceDTO.getLat());
                                transportFrontDTO1.setStartLng(startPlaceDTO.getLng());
                                transportFrontDTO1.setEndLat(allEndPlaces.get(i).getLat());
                                transportFrontDTO1.setEndLng(allEndPlaces.get(i).getLng());
                                transportFrontDTO1.setVolume(allEndPlaces.get(i).getVolume());
                                transportFrontDTO1.setStartName(startPlaceDTO.getName());
                                transportFrontDTO1.setEndName(allEndPlaces.get(i).getName());
                                origin.setVolume(origin.getVolume()-transportFrontDTO1.getVolume());
                                resultList.add(transportFrontDTO1);
                                carNowVolume = carNowVolume - allEndPlaces.get(i).getVolume();
                                allEndPlaces.get(i).setVolume(0.0);
                                startPlaceDTO = allEndPlaces.get(i);
                            } else {
                                TransportFrontDTO transportFrontDTO1 = new TransportFrontDTO();
                                transportFrontDTO1.setStartLat(startPlaceDTO.getLat());
                                transportFrontDTO1.setStartLng(startPlaceDTO.getLng());
                                transportFrontDTO1.setEndLat(allEndPlaces.get(i).getLat());
                                transportFrontDTO1.setEndLng(allEndPlaces.get(i).getLng());
                                transportFrontDTO1.setVolume(carNowVolume);
                                transportFrontDTO1.setStartName(startPlaceDTO.getName());
                                transportFrontDTO1.setEndName(allEndPlaces.get(i).getName());
                                origin.setVolume(origin.getVolume()-transportFrontDTO1.getVolume());
                                resultList.add(transportFrontDTO1);
                                allEndPlaces.get(i).setVolume(allEndPlaces.get(i).getVolume() - carNowVolume);
                                carNowVolume = 0;
                                totalVolume = totalVolume - carFrontDTO.getVolume();
                                break;
                            }
                        }
                        carFrontDTO.setAccount(carFrontDTO.getAccount()-1);
                        if (carFrontDTO.getAccount() == 0 ){
                            carFrontDTOPosition ++;
                            if (carFrontDTOPosition < carFrontDTOS.size()){
                                carFrontDTO = carFrontDTOS.get(carFrontDTOPosition);
                                carNowVolume = carFrontDTO.getVolume();
                                break;
                            }
                        }else {
                            carNowVolume = carFrontDTO.getVolume();
                        }
                    }
                }
            }
            if ( carFrontDTOPosition >= carFrontDTOS.size()){
                break;
            }
        }
        log.info("resultList:"+resultList);
        return resultList;
    }

    @Override
    public List<RouteFrontDTO> getRouteFrontDTO(Date date) {
        List<RouteFrontDTO> routeFrontDTOS = new ArrayList<>();
        List<TransportFrontDTO> transportFrontDTOS = getTransportFrontDTO(date);
        TransportFrontDTO firstTransportFrontDTO = transportFrontDTOS.get(0);
        double totalVolume = 0.0;
        for (int i = 0 ; i <transportFrontDTOS.size() ; i++){
            if (transportFrontDTOS.get(i).getStartName().equals(firstTransportFrontDTO.getStartName())) {
                RouteFrontDTO routeFrontDTO = new RouteFrontDTO();
                routeFrontDTO.setTransportInformation(new ArrayList<>());
                routeFrontDTO.setStartName(transportFrontDTOS.get(i).getStartName());
                routeFrontDTO.setStartLng(transportFrontDTOS.get(i).getStartLng());
                routeFrontDTO.setStartLat(transportFrontDTOS.get(i).getStartLat());
                while (i < transportFrontDTOS.size() - 1) {
                    if (transportFrontDTOS.get(i).getEndName().equals(transportFrontDTOS.get(i + 1).getStartName())) {
                        routeFrontDTO.getTransportInformation().add(transportFrontDTOS.get(i));
                        totalVolume = totalVolume + transportFrontDTOS.get(i).getVolume();
                    } else {
                        routeFrontDTO.setEndLng(transportFrontDTOS.get(i).getEndLng());
                        routeFrontDTO.setEndLat(transportFrontDTOS.get(i).getEndLat());
                        routeFrontDTO.setEndName(transportFrontDTOS.get(i).getEndName());
                        routeFrontDTO.getTransportInformation().add(transportFrontDTOS.get(i));
                        totalVolume = totalVolume + transportFrontDTOS.get(i).getVolume();
                        routeFrontDTO.setTotalVolume(totalVolume);
                        totalVolume = 0;
                        routeFrontDTOS.add(routeFrontDTO);
                        break;
                    }
                    i++;
                }
                if (i == transportFrontDTOS.size() - 1) {
                routeFrontDTO.setEndLng(transportFrontDTOS.get(i).getEndLng());
                routeFrontDTO.setEndLat(transportFrontDTOS.get(i).getEndLat());
                routeFrontDTO.setEndName(transportFrontDTOS.get(i).getEndName());
                routeFrontDTO.getTransportInformation().add(transportFrontDTOS.get(i));
                totalVolume = totalVolume + transportFrontDTOS.get(i).getVolume();
                routeFrontDTO.setTotalVolume(totalVolume);
                totalVolume = 0;
                routeFrontDTOS.add(routeFrontDTO);
                }
            }
        }
        log.info("size:"+routeFrontDTOS.size());
        return routeFrontDTOS;
    }

    private List<Map<PlaceFrontDTO, List<PlaceFrontDTO>>> getInDistancePlaceList(List<PlaceFrontDTO> placeFrontDTOS ,double distance , PlaceFrontDTO origin){
        List<Map<PlaceFrontDTO, List<PlaceFrontDTO>>> inDistancePlaceList = new ArrayList<>();
        for (int i = 1; i < placeFrontDTOS.size(); i++) {
            PlaceFrontDTO placeFrontDTO = placeFrontDTOS.get(i);
            if (placeFrontDTO.getVolume() != 0.0) {
                Map<PlaceFrontDTO, List<PlaceFrontDTO>> placeFrontDTOListMap = new HashMap<>();
                placeFrontDTOListMap = inDistancePlace(placeFrontDTO, placeFrontDTOS, distance, origin);
                inDistancePlaceList.add(placeFrontDTOListMap);
            }
        }
        return inDistancePlaceList;
    }
    public CarFrontDTO oneCarToOnePlace(List<PlaceFrontDTO> placeFrontDTOS ,List<TransportFrontDTO> resultList , List<CarFrontDTO> carFrontDTOS , int CarFrontDTOPosition){
        Iterator<PlaceFrontDTO> placeFrontDTOIterator = placeFrontDTOS.iterator();
        CarFrontDTO carFrontDTO = carFrontDTOS.get(CarFrontDTOPosition);
        PlaceFrontDTO startPlaceFrontDTO = placeFrontDTOIterator.next();//第一个是起点
        while (placeFrontDTOIterator.hasNext()) {
            PlaceFrontDTO placeFrontDTO = placeFrontDTOIterator.next();
            while (placeFrontDTO.getVolume() >= carFrontDTO.getVolume()) {
                TransportFrontDTO transportFrontDTO = new TransportFrontDTO();
                transportFrontDTO.setStartLat(startPlaceFrontDTO.getLat());
                transportFrontDTO.setStartLng(startPlaceFrontDTO.getLng());
                transportFrontDTO.setEndLat(placeFrontDTO.getLat());
                transportFrontDTO.setEndLng(placeFrontDTO.getLng());
                transportFrontDTO.setVolume(carFrontDTO.getVolume());
                transportFrontDTO.setStartName(startPlaceFrontDTO.getName());
                transportFrontDTO.setEndName(placeFrontDTO.getName());
                resultList.add(transportFrontDTO);
                placeFrontDTO.setVolume(placeFrontDTO.getVolume() - carFrontDTO.getVolume());
                startPlaceFrontDTO.setVolume(startPlaceFrontDTO.getVolume() - carFrontDTO.getVolume());
                carFrontDTO.setAccount(carFrontDTO.getAccount() - 1);
                if (carFrontDTO.getAccount() == 0) {
                    CarFrontDTOPosition ++;
                    carFrontDTO = carFrontDTOS.get(CarFrontDTOPosition);
                }
            }
        }
        return carFrontDTO;
    }



    private  List<Double> getCarVolumeList(){
        List<Double> resultList = new ArrayList<>();
        CarExample example = new CarExample();
        CarExample.Criteria criteria = example.createCriteria();
        criteria.andVolumeIsNotNull();
        List<Car> cars = carMapper.selectByExample(example);
        Iterator iterator = cars.iterator();
        while (iterator.hasNext()){
            resultList.add(((Car)iterator.next()).getVolume());
        }
        return resultList.stream().distinct().collect(Collectors.toList());
    }

    private Map<String , Double> getPlaceIDAndVolumeMap(Date date){
        Map<String , Double> resultMap = new HashMap<>();
        OrdersExample example = new OrdersExample();
        OrdersExample.Criteria criteria = example.createCriteria();
        criteria.andTimeEqualTo(date);
        List<Orders> orders = ordersMapper.selectByExample(example);
        for (Orders order : orders){
            GoodsKey goodsKey = new GoodsKey();
            goodsKey.setGoodsID(order.getGoodsID());
            Goods goods = goodsMapper.selectByPrimaryKey(goodsKey);
            resultMap.put(order.getEndPlaceID(),
                    order.getGoodsNumber()*goods.getVolume()+
                            Optional.ofNullable(resultMap.get(order.getEndPlaceID())).orElse(0.0));
        }
        return resultMap;
    }

    private double getTotalVolume(Date time) {
        double totalVolume = 0;
        OrdersExample ordersExample = new OrdersExample();
        OrdersExample.Criteria criteria = ordersExample.createCriteria();
        criteria.andTimeEqualTo(time);
        List<Orders> ordersList = ordersMapper.selectByExample(ordersExample);
        Iterator<Orders> iterator = ordersList.iterator();
        while (iterator.hasNext()) {
            Orders orders = iterator.next();
            GoodsKey goodsKey = new GoodsKey();
            goodsKey.setGoodsID(orders.getGoodsID());
            Goods goods = goodsMapper.selectByPrimaryKey(goodsKey);
            totalVolume = totalVolume + goods.getVolume() * orders.getGoodsNumber();
        }
        return totalVolume;
    }

    private Map<List<List<Double>>,Double> getHighestLoadRateList(List<Double> carVolumeList , Double totalVolume){
        //赋初值
        List<List<Double>> doubleListList = new ArrayList<>();
        List<List<Double>> resultList = new ArrayList<>();
        Map<List<List<Double>>,Double> resultMap = new HashMap<>();
        double minSum = Double.MAX_VALUE;
        for (Double aDouble : carVolumeList) {
            List<Double> doubleList = new ArrayList<>();
            List<Double> list = new ArrayList<>();
            if (aDouble >= totalVolume) {
                if (aDouble < minSum) {
                    list.clear();
                    minSum = aDouble;
                    list.add(aDouble);
                    resultList.add(list);
                } else if (aDouble.equals(minSum)) {
                    list.add(aDouble);
                    resultList.add(list);
                }
            }
            doubleList.add(aDouble);
            doubleListList.add(doubleList);
        }
        while (doubleListList.size() != 0) {
            int size = doubleListList.size();
            for (int i = 0; i < carVolumeList.size(); i++) {
                for (int j = i; j < size ; j++) {
                    boolean resultExist = false;
                    boolean doubleExist = false;
                    List<List<Double>> doubleListList1 = new ArrayList<>(doubleListList);
                    List<Double> addList = new ArrayList<>(doubleListList1.get(j));
                    addList.add(carVolumeList.get(i));
                    doubleListList1.add(addList);
                    double getSum = addList.stream().mapToDouble(Double::doubleValue).sum();
                    if (getSum >= totalVolume && getSum<= minSum) {
                        Iterator resultIterator = resultList.iterator();
                        while (resultIterator.hasNext()) {
                            if (isListEqual((List<Double>) resultIterator.next() , addList)) {
                                resultExist = true;
                                break;
                            }
                        }
                        if (!resultExist){
                            if (getSum == minSum) {
                                resultList.add(addList);
                            }else {
                                if (!resultList.isEmpty()){
                                    resultList.clear();
                                }
                                resultList.add(addList);
                                minSum = getSum;
                            }
                        }
                    }else if (getSum < totalVolume){
                        Iterator IntegerIterator = doubleListList.iterator();
                        while (IntegerIterator.hasNext()) {
                            if ((isListEqual((List<Double>) IntegerIterator.next() , addList))) {
                                doubleExist = true;
                                break;
                            }
                        }
                        if (!doubleExist){
                            doubleListList.add(addList);
                        }
                    }
                }
            }
            for (int l = 0 ; l < size ; l++) {
                doubleListList.remove(0);
            }
        }
        resultMap.put(resultList , minSum);
        return resultMap;
    }

    /**
     * 判断2个集合元素是否相等（可以顺序不相等）
     * @param list
     * @param list1
     * @return boolean
     * @create 2021/1/7 16:24
     */
    private boolean isListEqual(List<Double> list, List<Double> list1) {
        list.sort(Comparator.comparing(Double::doubleValue));
        list1.sort(Comparator.comparing(Double::doubleValue));
        return list.toString().equals(list1.toString());
    }

    private  List<CarFrontDTO> getResultList(Map<List<List<Double>>, Double> resultMap){
        List<Map<Double , Integer>> resultList = new ArrayList<>();
        List<CarFrontDTO> carFrontDTOS = new ArrayList<>();
        Iterator ResultMapIterator = resultMap.keySet().iterator();
        while (ResultMapIterator.hasNext()){
            Iterator listListDoubleIterator = ((List<List<Double>>)ResultMapIterator.next()).iterator();
            while (listListDoubleIterator.hasNext()){
                Map<Double , Integer> listIteratorMap = new HashMap<>();
                List<Double> list = (List<Double>) listListDoubleIterator.next();
                for(Double d:list){
                    Integer i = 1; //定义一个计数器，用来记录重复数据的个数
                    if(listIteratorMap.get(d) != null){
                        i=listIteratorMap.get(d)+1;
                    }
                    listIteratorMap.put(d,i);
                }
                resultList.add(listIteratorMap);
            }
        }
        Map<Double , Integer> map = resultList.get(0);
        Iterator<Map.Entry<Double , Integer>> entry = map.entrySet().iterator();
        while (entry.hasNext()){
            Map.Entry<Double , Integer> entry1 = entry.next();
            CarFrontDTO carFrontDTO = new CarFrontDTO();
            carFrontDTO.setVolume(entry1.getKey());
            carFrontDTO.setAccount(entry1.getValue());
            carFrontDTOS.add(carFrontDTO);
        }
        return carFrontDTOS;
    }

    /**
     * 根据经纬度，计算两点间的距离
     *
     * @param longitude1 第一个点的经度
     * @param latitude1  第一个点的纬度
     * @param longitude2 第二个点的经度
     * @param latitude2  第二个点的纬度
     * @return 返回距离 单位千米
     */
    private static double getDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
        double EARTH_RADIUS = 6378.137;
        // 纬度
        double lat1 = Math.toRadians(latitude1);
        double lat2 = Math.toRadians(latitude2);
        // 经度
        double lng1 = Math.toRadians(longitude1);
        double lng2 = Math.toRadians(longitude2);
        // 纬度之差
        double a = lat1 - lat2;
        // 经度之差
        double b = lng1 - lng2;
        // 计算两点距离的公式
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(b / 2), 2)));
        // 弧长乘地球半径, 返回单位: 千米
        s =  s * EARTH_RADIUS;
        return s;
    }
//    public Map<PlaceFrontDTO , List<PlaceFrontDTO>> inDistancePlace(PlaceFrontDTO startPlace , List<PlaceFrontDTO> placeFrontDTOS , double distance){
//        Map<PlaceFrontDTO , List<PlaceFrontDTO>> resultMap = new HashMap<>();
//        List<PlaceFrontDTO> resultList = new ArrayList<>();
//        for (PlaceFrontDTO endPlace : placeFrontDTOS){
//            if (!endPlace.equals(startPlace)){
//                if (routeService.getCarMinDistance(startPlace.getId(),endPlace.getId()) < distance){
//                    resultList.add(endPlace);
//                }
//            }
//        }
//        resultMap.put(startPlace , resultList);
//        return resultMap;
//    }

    public Map<PlaceFrontDTO , List<PlaceFrontDTO>> inDistancePlace(PlaceFrontDTO startPlace , List<PlaceFrontDTO> placeFrontDTOS , double distance , PlaceFrontDTO origin){
        Map<PlaceFrontDTO , List<PlaceFrontDTO>> resultMap = new HashMap<>();
        List<PlaceFrontDTO> resultList = new ArrayList<>();
        for (PlaceFrontDTO endPlace : placeFrontDTOS){
            if (!endPlace.equals(startPlace)){
                if (getDistance(startPlace.getLng(),startPlace.getLat(),endPlace.getLng(),endPlace.getLat()) <= distance){
                    if (endPlace.getVolume() != 0.0){
                        resultList.add(endPlace);
                    }
                }
            }
        }
        if (resultList.contains(origin)){
            resultList.remove(origin);
        }
        resultMap.put(startPlace , resultList);
        return resultMap;
    }

    private double minDistancePlaceToPlaceList(PlaceFrontDTO startPlace , List<PlaceFrontDTO> placeFrontDTOS){
        double minDistance = Double.MAX_VALUE;
        double distance = 0;
        for (PlaceFrontDTO endPlace : placeFrontDTOS){
            if (!endPlace.equals(startPlace)){
                distance = getDistance(startPlace.getLng(),startPlace.getLat(),endPlace.getLng(),endPlace.getLat());
                if (distance <= minDistance){
                    minDistance = distance;
                }
            }
        }
        return minDistance;
    }

    private double minDistancePlaceListToPlaceList(List<PlaceFrontDTO> startPlaces , List<PlaceFrontDTO> placeFrontDTOS){
        double minDistance = Double.MAX_VALUE;
        double distance = 0;
        for (PlaceFrontDTO startPlace : startPlaces){
            distance = minDistancePlaceToPlaceList(startPlace , placeFrontDTOS);
            if (minDistancePlaceToPlaceList(startPlace , placeFrontDTOS) <= minDistance){
                minDistance = distance;
            }
        }
        return minDistance;
    }

    private double maxDistancePlaceToPlaceList(PlaceFrontDTO startPlace , List<PlaceFrontDTO> placeFrontDTOS){
        double maxDistance = Double.MIN_VALUE;
        double distance = 0;
        for (PlaceFrontDTO endPlace : placeFrontDTOS){
            if (!endPlace.equals(startPlace)){
                distance = getDistance(startPlace.getLng(),startPlace.getLat(),endPlace.getLng(),endPlace.getLat());
                if (distance >= maxDistance){
                    maxDistance = distance;
                }
            }
        }
        return maxDistance;
    }

    private double maxDistancePlaceListToPlaceList(List<PlaceFrontDTO> startPlaces , List<PlaceFrontDTO> placeFrontDTOS){
        double maxDistance = Double.MIN_VALUE;
        double distance = 0;
        for (PlaceFrontDTO startPlace : startPlaces){
            distance = maxDistancePlaceToPlaceList(startPlace , placeFrontDTOS);
            if (minDistancePlaceToPlaceList(startPlace , placeFrontDTOS) >= maxDistance){
                maxDistance = distance;
            }
        }
        return maxDistance;
    }

    public int getCarAccount(List<CarFrontDTO> carFrontDTOS){
        int account = 0;
        for (CarFrontDTO carFrontDTO : carFrontDTOS){
            account = account+carFrontDTO.getAccount();
        }
        return account;
    }

    public double getBiggestCaVolume(List<CarFrontDTO> carFrontDTOS){
        for (CarFrontDTO carFrontDTO : carFrontDTOS){
            if (carFrontDTO.getAccount() > 0){
                return carFrontDTO.getVolume();
            }
        }
        return 0;
    }

    public  List<PlaceFrontDTO> sortPlaceFrontDTOSByDistance(PlaceFrontDTO placeFrontDTO , List<PlaceFrontDTO> placeFrontDTOS){
         Collections.sort(placeFrontDTOS, new Comparator<PlaceFrontDTO>() {
            @Override
            public int compare(PlaceFrontDTO o1, PlaceFrontDTO o2) {
                if (getDistance(placeFrontDTO.getLng(),placeFrontDTO.getLat(),o1.getLng(),o1.getLat()) >
                        getDistance(placeFrontDTO.getLng(),placeFrontDTO.getLat(),o2.getLng(),o2.getLat())) {
                    return 1;
                }else if (getDistance(placeFrontDTO.getLng(),placeFrontDTO.getLat(),o1.getLng(),o1.getLat()) ==
                        getDistance(placeFrontDTO.getLng(),placeFrontDTO.getLat(),o2.getLng(),o2.getLat())){
                    return 0;
                }else {
                    return -1;
                }
            }
        });
         return placeFrontDTOS;
    }


}
