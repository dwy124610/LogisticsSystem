package com.dwy.logistics.service.impl;

import com.dwy.logistics.mapper.CarMapper;
import com.dwy.logistics.mapper.GoodsMapper;
import com.dwy.logistics.mapper.OrdersMapper;
import com.dwy.logistics.model.entities.*;
import com.dwy.logistics.service.IRouteService;
import com.dwy.logistics.service.ITransportHighestLoadRate;
import com.dwy.logistics.service.ITransportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/6 11:43
 */
@Service
@Slf4j
public class TransportHighestLoadRateServiceImpl implements ITransportHighestLoadRate {
    @Resource
    OrdersMapper ordersMapper;
    
    @Resource
    CarMapper carMapper;

    @Resource
    GoodsMapper goodsMapper;

    @Resource
    IRouteService routeService;

    @Resource
    ITransportService transportService;


    @Override
    public void getHighestLoadRate(Date date) {
        //获得当天的总体积
        double totalVolume = getTotalVolume(date);
        log.info("totalVolume:"+totalVolume);
        //获得所有车子的体积集合
        List<Double> carVolumeList = getCarVolumeList();
        carVolumeList.sort(Comparator.comparing(Double::doubleValue));
        log.info("carVolumeList"+carVolumeList);
        Map<String , Double> placeIDAndVolumeMap = getPlaceIDAndVolumeMap(date);
        log.info("placeIDAndVolumeMap"+placeIDAndVolumeMap);
        //获得最高装载率
        Map<List<List<Double>>, Double> highestLoadRateListMap = getHighestLoadRateList(carVolumeList, totalVolume);
        log.info("highestLoadRateListMap"+highestLoadRateListMap);
        DecimalFormat df = new DecimalFormat("0.00%");
        String rates = df.format(totalVolume/highestLoadRateListMap.values().iterator().next());
        log.info("满载率=" + rates);
        List<Map<Double , Integer>> allCarDistributionList = getResultList(highestLoadRateListMap);
        log.info("allCarDistributionList"+allCarDistributionList);
        //实际情况存在小数满载率最高只有一种，若有多种，前几种派的车的数量最少，这里不妨去list的第一个
        Map<Double,Integer> carDistributionMap = allCarDistributionList.get(0);
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

    private Map<Integer , Integer> getOrderOfPlace(Date time , String placeID) {
        Map<Integer , Integer> goodsIDAndNumMap = new HashMap<>();
        OrdersExample ordersExample = new OrdersExample();
        OrdersExample.Criteria criteria = ordersExample.createCriteria();
        criteria.andTimeEqualTo(time);
        criteria.andEndPlaceIDEqualTo(placeID);
        List<Orders> ordersList = ordersMapper.selectByExample(ordersExample);
        Iterator<Orders> iterator = ordersList.iterator();
        while (iterator.hasNext()) {
            goodsIDAndNumMap.put(iterator.next().getGoodsID() , iterator.next().getGoodsNumber());
        }
        return goodsIDAndNumMap;
    }

    private double getTotalVolumeOfOrder(Map<Integer , Integer> goodsIDAndNumMap) {
        double totalVolume = 0;
        Iterator<Map.Entry<Integer , Integer>> iterator = goodsIDAndNumMap.entrySet().iterator();
        while (iterator.hasNext()) {
            GoodsKey goodsKey = new GoodsKey();
            goodsKey.setGoodsID(iterator.next().getKey());
            Goods goods = goodsMapper.selectByPrimaryKey(goodsKey);
            totalVolume = totalVolume + goods.getVolume() * iterator.next().getValue();
        }
        return totalVolume;
    }

    /**
     *
     * @param carVolumeList 汽车可装体积的集合
     * @param totalVolume 总共需要装的体积
     * @return java.util.Map<java.util.List<java.util.List<java.lang.Double>>,java.lang.Double>
     * @create 2021/1/7 15:34
     * 思路：按照个数遍历，将大于总体积的取出减少遍历次数。
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

    /**
     * 得到数据库中所有体积类型的车
     * @return java.util.List<java.lang.Double> 
     * @create 2021/1/8 14:46
     */
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

    private  List<Map<Double , Integer>> getResultList(Map<List<List<Double>>, Double> resultMap){
        List<Map<Double , Integer>> resultList = new ArrayList<>();
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
        return resultList;
    }

    private  Map<String , Double> getPlaceIDAndVolumeMap(Date date){
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

    private Map<String , Double> searchMinDistanceFromStartPlace(String startPlaceID, List<String> endPlaceIDList){
        Map<String , Double> searchMap = new HashMap<>();
        Map<String , Double> resultMap = new HashMap<>();
        double minDistance = Double.MAX_VALUE;
        String minDistancePlaceID = null;
        for (String endPlaceID : endPlaceIDList){
            searchMap.put(endPlaceID , routeService.getCarMinDistance(startPlaceID,endPlaceID));
        }
        Iterator<Map.Entry<String , Double>> iterator = resultMap.entrySet().iterator();
        while (iterator.hasNext()){
            if (iterator.next().getValue()<minDistance){
                minDistance = iterator.next().getValue();
                minDistancePlaceID =iterator.next().getKey();
            }
        }
        resultMap.put(minDistancePlaceID,minDistance);
        return resultMap;
    }

    private void getCarRoutes(String startPlaceID , List<String> endPlaceIDList , Map<Double,Integer> carDistributionMap , Date date){
        Iterator<Map.Entry<Double , Integer>> iterator = carDistributionMap.entrySet().iterator();
        double placeTotalVolume = 0.0;
        Transport transport = new Transport();
        transport.setTime(date);
        while (iterator.hasNext()){
            int carNumber = 0;
            int carAccount = iterator.next().getValue();
            Double carNowVolume = iterator.next().getKey();
            transport.setCarVolume(carNowVolume);
            while (carAccount > 0 ){
                carNumber ++;
                transport.setStartPlaceID(startPlaceID);
                transport.setCarNumber(carNumber);
                Map<String , Double> map = searchMinDistanceFromStartPlace(startPlaceID , endPlaceIDList);
                String endPlaceID = map.keySet().iterator().next();
                Map<Integer, Integer> orderOfPlaceMap = getOrderOfPlace(date, endPlaceID);
                transport.setEndPlaceID(endPlaceID);
                Iterator<Map.Entry<Integer , Integer>> it = orderOfPlaceMap.entrySet().iterator();
                int goodsID = it.next().getKey();
                transport.setGoodsID(goodsID);
                if (placeTotalVolume == 0.0){
                    placeTotalVolume = getTotalVolumeOfOrder(orderOfPlaceMap);
                    dealCarAndPlaceTotalVolume(carNowVolume, placeTotalVolume , map , endPlaceIDList ,
                            it , transport, goodsID , carAccount, iterator);
                }else {
                    dealCarAndPlaceTotalVolume(carNowVolume, placeTotalVolume , map , endPlaceIDList ,
                            it , transport, goodsID , carAccount, iterator);
                }
            }
        }
    }
    private void dealCarAndPlaceTotalVolume(Double carNowVolume, Double placeTotalVolume,
                                            Map<String, Double> map, List<String> endPlaceIDList,
                                            Iterator<Map.Entry<Integer, Integer>> it, Transport transport,
                                            int goodsID, int carAccount, Iterator<Map.Entry<Double, Integer>> iterator){
        if (carNowVolume >= placeTotalVolume){
            carNowVolume = carNowVolume - placeTotalVolume;
            placeTotalVolume = 0.0;
            String startPlaceID = map.keySet().iterator().next();
            endPlaceIDList.remove(startPlaceID);
            while (it.hasNext()){
                transport.setGoodsNumber(it.next().getValue());
                log.info(transport.toString());
                transportService.insertTransport(transport);
            }
        }else {
            placeTotalVolume = placeTotalVolume -carNowVolume;
            GoodsKey goodsKey = new GoodsKey();
            goodsKey.setGoodsID(goodsID);
            Goods goods = goodsMapper.selectByPrimaryKey(goodsKey);
            transport.setGoodsNumber((int) (carNowVolume/goods.getVolume()));
            transportService.insertTransport(transport);
            carAccount --;
            carNowVolume = iterator.next().getKey();
        }
    }
}

