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
import com.dwy.logistics.utils.PermutationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
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

    private static Map<Date , List<PlaceFrontDTO>> placeMap = new HashMap<>();

    @Override
    public  List<TransportFrontDTO> getByRegional(Date date){
        List<TransportFrontDTO> resultList = new ArrayList<>();
        List<PlaceFrontDTO> placeFrontDTOS = getPlaceFrontDTO(date);
        List<CarFrontDTO> carFrontDTOS = getCarFrontDTO(date);
        PlaceFrontDTO origin = placeFrontDTOS.get(0);
        placeFrontDTOS.remove(0);
        Integer times = 10;
        while (!CollectionUtils.isEmpty(placeFrontDTOS)){
            Map<String,List<PlaceFrontDTO>> regionalMap = getRegionalMap(placeFrontDTOS,times,origin);
            Iterator<List<PlaceFrontDTO>> iterator = regionalMap.values().iterator();
            while (iterator.hasNext()){
                List<PlaceFrontDTO> next = iterator.next();
                transport(carFrontDTOS,next,origin,resultList,placeFrontDTOS);
            }
            times = times + 10;
            placeFrontDTOS.removeIf( p -> p.getVolume() == 0);
        }
        return resultList;
    }

    public void transport(List<CarFrontDTO> carFrontDTOS , List<PlaceFrontDTO> next ,PlaceFrontDTO origin , List<TransportFrontDTO> resultList,List<PlaceFrontDTO> placeFrontDTOS){
        Double volume = 0.0;
        PlaceFrontDTO start = origin;
        for (PlaceFrontDTO placeFrontDTO : next) {
            //计算区域内的体积和
            volume = volume + placeFrontDTO.getVolume();
        }
        for (CarFrontDTO carFrontDTO : carFrontDTOS) {
            while (carFrontDTO.getAccount() > 0){
                if (carFrontDTO.getVolume() <= volume){
                    //达到起送标准,派出一辆车配送
                    Double remainVolume = carFrontDTO.getVolume();
                    carFrontDTO.setAccount(carFrontDTO.getAccount()-1);
                    //通过和起点的距离进行排序
                   // sortPlaceFrontDTOSByDistance(origin,next);
                    List<PlaceFrontDTO> sortPlaces = getSortPlaces(origin, next);
                    for (PlaceFrontDTO placeFrontDTO : sortPlaces) {
                        if (placeFrontDTO.getVolume() > 0){
                            //需要配送
                            if (remainVolume > placeFrontDTO.getVolume()){
                                //当前车辆可以配送,且配送和完后还能继续配送
                                TransportFrontDTO transportFrontDTO = createTransport(start,placeFrontDTO,placeFrontDTO.getVolume(),carFrontDTO.getVolume());
                                resultList.add(transportFrontDTO);
                                remainVolume = remainVolume - placeFrontDTO.getVolume();
                                volume = volume - placeFrontDTO.getVolume();
                                placeFrontDTO.setVolume(0.0);
                                placeFrontDTOS.stream().filter(p -> p.getId().equals(placeFrontDTO.getId())).forEach(p -> p.setVolume(0.0));
                                start = placeFrontDTO;
                            }else {
                                //当前车辆仅能配送最后这一个地方
                                TransportFrontDTO transportFrontDTO = createTransport(start,placeFrontDTO,remainVolume,carFrontDTO.getVolume());
                                resultList.add(transportFrontDTO);
                                placeFrontDTO.setVolume(placeFrontDTO.getVolume()-remainVolume);
                                placeFrontDTOS.stream().filter(p -> p.getId().equals(placeFrontDTO.getId())).forEach(p -> p.setVolume(placeFrontDTO.getVolume()));
                                volume = volume - remainVolume;
                                start=origin;
                                break;
                            }
                        }
                    }
                }else {
                    if (carFrontDTOS.stream().filter(c -> c.getAccount() != 0).count() == 1 && carFrontDTO.getAccount() == 1){
                        //最后一辆车
                        placeFrontDTOS.removeIf( p -> p.getVolume() == 0);
                        start = origin;
                        List<PlaceFrontDTO> sortPlaces = getSortPlaces(origin, placeFrontDTOS);
                        for (PlaceFrontDTO placeFrontDTO : sortPlaces){
                            if (placeFrontDTO.getVolume() > 0){
                                TransportFrontDTO transportFrontDTO = createTransport(start,placeFrontDTO,placeFrontDTO.getVolume(),carFrontDTO.getVolume());
                                resultList.add(transportFrontDTO);
                                volume = volume - placeFrontDTO.getVolume();
                                placeFrontDTO.setVolume(0.0);
                                placeFrontDTOS.stream().filter(p -> p.getId().equals(placeFrontDTO.getId())).forEach(p -> p.setVolume(placeFrontDTO.getVolume()));
                                start = placeFrontDTO;
                            }
                        }
                        carFrontDTO.setAccount(carFrontDTO.getAccount()-1);
                    }
                    break;
                }
            }
        }
    }

    /**
     *
     * @param origin 起点
     * @param next 区域内所有点的集合
     * @return void
     * @create 2021/5/19 17:08
     */
    private List<PlaceFrontDTO>  getSortPlaces(PlaceFrontDTO origin, List<PlaceFrontDTO> next) {
//        List<PlaceFrontDTO> placeFrontDTOS = new ArrayList<>();
//        placeFrontDTOS.add(origin);
//        for (PlaceFrontDTO placeFrontDTO : next) {
//            Double min = Double.MAX_VALUE;
//            Integer index = 0;
//            for (int i =0 ; i < placeFrontDTOS.size() ;i++) {
//                double distance = getDistance(placeFrontDTO.getLng(), placeFrontDTO.getLat(),
//                        placeFrontDTOS.get(i).getLng(), placeFrontDTOS.get(i).getLat());
//                min = Math.min(min,distance);
//                if (min == distance){
//                    index =  i;
//                }
//            }
//            placeFrontDTOS.add(index +1 , placeFrontDTO);
//        }
//        placeFrontDTOS.remove(0);
//        return placeFrontDTOS;
        int size = next.size();
        List<PlaceFrontDTO> roughList = new ArrayList<>();
        PlaceFrontDTO place = origin;
        if (size <= 1){
            return next;
        }
        while (size >= 8){
            PlaceFrontDTO placeFrontDTO = findMinDistance(place,next);
            roughList.add(placeFrontDTO);
            next.remove(placeFrontDTO);
            size = size -1;
            place = placeFrontDTO;
        }
        next.addAll(0,roughList);
        List<PlaceFrontDTO> sortPlace = new ArrayList<>();
        List<List<PlaceFrontDTO>> result = PermutationUtil.getResult(next,roughList.size());
        double min = Double.MAX_VALUE;
        for (List<PlaceFrontDTO> placeFrontDTOS : result) {
            placeFrontDTOS.add(0,origin);
            double distance = 0.0;
            for (int i = 0 ; i < placeFrontDTOS.size()-1 ; i++){
                distance = distance + getDistance(placeFrontDTOS.get(i).getLng(), placeFrontDTOS.get(i).getLat(),
                  placeFrontDTOS.get(i+1).getLng(), placeFrontDTOS.get(i+1).getLat());
            }
            if (distance < min){
                min = distance;
                sortPlace.clear();
                sortPlace.addAll(placeFrontDTOS);
            }
        }
        sortPlace.remove(0);
        return sortPlace;
    }

    private PlaceFrontDTO findMinDistance(PlaceFrontDTO origin, List<PlaceFrontDTO> next) {
        PlaceFrontDTO min = new PlaceFrontDTO();
        double minDistance = Double.MAX_VALUE;
        for (PlaceFrontDTO placeFrontDTO : next) {
            double distance = getDistance(origin.getLng(), origin.getLat(), placeFrontDTO.getLng(), placeFrontDTO.getLat());
            if (distance < minDistance){
                minDistance = distance ;
                min = placeFrontDTO;
            }
        }
        return min;
    }

    private TransportFrontDTO createTransport(PlaceFrontDTO origin, PlaceFrontDTO placeFrontDTO , Double volume , Double carVolume) {
        TransportFrontDTO transportFrontDTO = new TransportFrontDTO();
        transportFrontDTO.setStartName(origin.getName());
        transportFrontDTO.setEndName(placeFrontDTO.getName());
        transportFrontDTO.setStartLng(origin.getLng());
        transportFrontDTO.setStartLat(origin.getLat());
        transportFrontDTO.setEndLng(placeFrontDTO.getLng());
        transportFrontDTO.setEndLat(placeFrontDTO.getLat());
        transportFrontDTO.setVolume(volume);
        transportFrontDTO.setCarVolume(carVolume);
        return transportFrontDTO;
    }

    /**
     * 得到区域分割的map， key为坐标，value为当前坐标内的地点
     * @param placeFrontDTOS
     * @param times
     * @param origin
     * @return java.util.Map<java.lang.String,java.util.List<com.dwy.logistics.model.dto.front.PlaceFrontDTO>>
     * @create 2021/5/20 9:23
     */
    public Map<String,List<PlaceFrontDTO>> getRegionalMap(List<PlaceFrontDTO> placeFrontDTOS , Integer times , PlaceFrontDTO origin){
        Map<String,List<PlaceFrontDTO>> regionalMap = new HashMap<>();
        Double lng = origin.getLng();
        Double lat = origin.getLat();
        placeFrontDTOS.stream().forEach(p -> {
            int rotateDegrees = getRotateDegrees(lat, lng, p.getLat(), p.getLng())/times*times;
            String key = rotateDegrees+","+(rotateDegrees+times);
            if (regionalMap.get(key) == null){
                List<PlaceFrontDTO> placeFrontDTOList = new ArrayList<>();
                placeFrontDTOList.add(p);
                regionalMap.put(key,placeFrontDTOList);
            }else {
                regionalMap.get(key).add(p);
            }
        });
        return regionalMap;
    }

    @Override
    public List<PlaceFrontDTO> getPlaceFrontDTO(Date date){
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
//
        Map<Double, Map<Double, Long>> highestLoadRateMap = getHighestLoadRateMap(carVolumeList, getTotalVolume(date));
        log.info("highestLoadRateMap" +highestLoadRateMap);
        Collection<Map<Double, Long>> values = highestLoadRateMap.values();
        return getResultList(values);
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
                    sortPlaceFrontDTOSByDistance(origin,allEndPlaces);//按照与起点的距离排序。
                    double totalVolume = startPlace.getVolume();
                    for (PlaceFrontDTO endPlace : endPlaces){
                        totalVolume = totalVolume+endPlace.getVolume();
                    }
                    while (totalVolume >= carFrontDTO.getVolume()){ //所有地点加起来的体积大于车的体积才考虑运输
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
                        //运输第一个地点
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
                            if (allEndPlaces.get(i).getVolume() <= carNowVolume){ //车子还能运输这个地点
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
                            } else { //车子已满，运输不了这个地点
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
                        if (carFrontDTO.getAccount() == 0 ){ //该型号车用完，换下一辆。
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
            if ( carFrontDTOPosition >= carFrontDTOS.size()){ //车子全部用完
                break;
            }
        }
        log.info("resultList:"+resultList);
        return resultList;
    }

    @Override
    public List<RouteFrontDTO> getRouteFrontDTO(Date date) {
        List<RouteFrontDTO> routeFrontDTOS = new ArrayList<>();
        //List<TransportFrontDTO> transportFrontDTOS = getTransportFrontDTO(date);
        List<TransportFrontDTO> transportFrontDTOS = getByRegional(date);
        TransportFrontDTO firstTransportFrontDTO = transportFrontDTOS.get(0);
        double totalVolume = 0.0;
        for (int i = 0; i < transportFrontDTOS.size(); i++) {
            if (transportFrontDTOS.get(i).getStartName().equals(firstTransportFrontDTO.getStartName())) {
                RouteFrontDTO routeFrontDTO = new RouteFrontDTO();
                routeFrontDTO.setTransportInformation(new ArrayList<>());
                routeFrontDTO.setStartName(transportFrontDTOS.get(i).getStartName());
                routeFrontDTO.setStartLng(transportFrontDTOS.get(i).getStartLng());
                routeFrontDTO.setStartLat(transportFrontDTOS.get(i).getStartLat());
                while (i < transportFrontDTOS.size() - 1) {
                    if (transportFrontDTOS.get(i).getEndName().equals(transportFrontDTOS.get(i + 1).getStartName())) {
                        //终点名和起点名相同，说明是同一次配送
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
                        routeFrontDTO.setCarVolume(transportFrontDTOS.get(i).getCarVolume());
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
                    routeFrontDTO.setCarVolume(transportFrontDTOS.get(i).getCarVolume());
                    routeFrontDTOS.add(routeFrontDTO);
                }
            }
        }
        log.info("size:" + routeFrontDTOS.size());
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

    /*
    主要想法是动态规划，循环遍历加，如果超出货物体积就扔出循环，减少循环次数，同时比较是不是超出的最小的，是的话记录该值，把这个车子方案记入
    先确定最少需要几辆车
     */
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
     * <满载率,<体积，个数>>
     * @param carVolumeList
     * @param totalVolume
     * @return java.util.Map<java.util.List<java.util.List<java.lang.Double>>,java.lang.Double>
     * @create 2021/5/8 9:29
     */
    private static Map<Double,Map<Double,Long>> getHighestLoadRateMap(List<Double> carVolumeList , Double totalVolume){
        Map<Double,Map<Double,Long>> loadRateMap = new HashMap<>();
        Map<Double, List<Double>> volume = getVolume(totalVolume, carVolumeList);
        Double highestLoadRateVolume = volume.keySet().iterator().next();
        List<Double> doubleList = volume.get(highestLoadRateVolume);
        Map<Double, Long> carMap = doubleList.stream().collect(Collectors.groupingBy(c -> c.doubleValue(), (Collectors.counting())));
        loadRateMap.put(totalVolume/highestLoadRateVolume,carMap);
        return loadRateMap;
    }

//    /**
//     * 递归求最大装载体积
//     * @param totalVolume
//     * @param carVolumeList
//     * @param
//     * @return java.lang.Double
//     * @create 2021/5/8 14:01
//     */
//    private  static Double getHighestLoadRateVolume(Double totalVolume, List<Double> carVolumeList)   { //
//            if (totalVolume < 0 ) {
//                return totalVolume;
//            }else {
//                 return Math.max(Math.max(getHighestLoadRateVolume(totalVolume-carVolumeList.get(0),carVolumeList),
//                         getHighestLoadRateVolume(totalVolume-carVolumeList.get(1),carVolumeList)),
//                         getHighestLoadRateVolume(totalVolume-carVolumeList.get(2),carVolumeList));
//            }
//        //}
//    }

    /**
     * 动态规划求解，将已经算过的赋值到map，下一次只需要在上一次的基础上再加就可以.
     * 使用i个数，可以有j个解。记录这j个解中大于totalVolume的最小值。
     * 终止条件为:使用第i+1个数，有k个解，其中有m个解是和j个解不同的，如果这m个解都大于totalVolume程序停止。
     * @param totalVolume
     * @param carVolumeList
     * @return java.lang.Double
     * @create 2021/5/18 16:58
     */
    private  static Map<Double , List<Double>> getVolume(Double totalVolume, List<Double> carVolumeList)   {
        Map<Double,String> map = new HashMap<>();
        Map<Double,String> lastMap = new HashMap<>();
        map.put(0.0,"");
        Double result = Double.MAX_VALUE;
        for (int i = 1; i<= carVolumeList.size();i++){
            while (true){
                Map<Double,String> addMap = new HashMap();
                mapCopy(map,addMap);
                Set<Double> keySet = map.keySet();
                for (Double aDouble : keySet) {
                    if (addMap.get(aDouble) != "-1"){
                        String value;
                        if (addMap.get(aDouble) != ""){
                            value = addMap.get(aDouble) + "," + carVolumeList.get(i-1);
                        }else {
                            value = String.valueOf(carVolumeList.get(i-1));
                        }
                        if(addMap.get(aDouble+carVolumeList.get(i-1)) != null){
                            int length = String.valueOf(addMap.get(aDouble+carVolumeList.get(i-1))).split(",").length;
                            if (value.split(",").length < length){
                                addMap.put(aDouble+carVolumeList.get(i-1) , value);
                            }
                        }else {
                            addMap.put(aDouble+carVolumeList.get(i-1) , value);
                        }
                        if (aDouble + carVolumeList.get(i - 1) >= totalVolume) {
                            result = Math.min(result, aDouble + carVolumeList.get(i - 1));
                            if (result != aDouble + carVolumeList.get(i - 1)){
                                addMap.put(aDouble + carVolumeList.get(i - 1) , "-1");
                            }
                        }
                    }
                }
                mapCopy(map,lastMap);
                mapCopy(addMap,map);
                for (Double aDouble : lastMap.keySet()) {
                    addMap.remove(aDouble);
                }
                if (addMap.keySet().size() == 0 || addMap.keySet().stream().min(Comparator.comparing(Double::doubleValue)).get() >= totalVolume){
                    break;
                }
            }

        }
        Map<Double , List<Double>> resultMap = new HashMap<>();
        String s = map.get(result);
        List<Double> doubleList = Arrays.stream(s.split(",")).map(Double::parseDouble).collect(Collectors.toList());
        resultMap.put(result,doubleList);
        return resultMap;
    }
    /**
     * 复制map对象
     * @explain 将paramsMap中的键值对全部拷贝到resultMap中；
     * paramsMap中的内容不会影响到resultMap（深拷贝）
     * @param paramsMap
     *     被拷贝对象
     * @param resultMap
     *     拷贝后的对象
     */
    public static void mapCopy(Map paramsMap, Map resultMap) {
        if (resultMap == null) resultMap = new HashMap();
        if (paramsMap == null) return;

        Iterator it = paramsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            resultMap.put(key, paramsMap.get(key) != null ? paramsMap.get(key) : "");

        }
    }

//    private  static void getResultList(List<List<Double>> res , List<Double> carVolumeList, Double target, List<Double> list , Integer index) {
//        if( target == 0){
//            res.add(new ArrayList<>(list));
//        }
//
//        for (int i =  carVolumeList.size()-1; i >= 0; i--) {
//            if(carVolumeList.get(i) <= target){
//                list.add(carVolumeList.get(i));
//                getResultList(res , carVolumeList, target-carVolumeList.get(i), list, i);
//                list.remove(list.size()-1);
//            }
//        }
//    }


    public static void main(String[] args) {
        System.out.println(getRotateDegrees(0, 0, 1, -Math.sqrt(3)));
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

    private  List<CarFrontDTO> getResultList( Collection<Map<Double, Long>> resultList){
        List<CarFrontDTO> carFrontDTOS = new ArrayList<>();
        Iterator<Map<Double, Long>> iterator = resultList.iterator();
        while (iterator.hasNext()){
            Map<Double, Long> next = iterator.next();
            Set<Map.Entry<Double, Long>> entrySet = next.entrySet();
            Iterator<Map.Entry<Double, Long>> entryIterator = entrySet.iterator();
            while (entryIterator.hasNext()){
                Map.Entry<Double, Long> next1 = entryIterator.next();
                carFrontDTOS.add(new CarFrontDTO(next1.getKey(),Integer.parseInt(String.valueOf(next1.getValue()))));
            }
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

    private static int getRotateDegrees(double x1, double y1, double x2, double y2) {
        double x = (x2 - x1);
        double y = (y2 - y1);
        double z = Math.sqrt(x * x + y * y);
        if (x == 0 && y == 0) {
            return 0;
        } else if (y >= 0) {
            return (int) Math.toDegrees(Math.acos(x / z));
        } else if (x <= 0 && y < 0) {
            int jiaodu = (int) Math.toDegrees(Math.atan(y / x));
            return jiaodu + 180;
        } else {
            int jiaodu = (int) Math.toDegrees(Math.atan(y / x));
            return jiaodu+360;
        }
    }


}
