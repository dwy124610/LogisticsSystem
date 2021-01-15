package com.dwy.logistics.service.impl;


import com.dwy.logistics.mapper.GoodsMapper;
import com.dwy.logistics.mapper.OrdersMapper;
import com.dwy.logistics.mapper.PlaceMapper;
import com.dwy.logistics.model.dto.front.PlaceFrontDTO;
import com.dwy.logistics.model.entities.*;
import com.dwy.logistics.service.IOrdersService;
import com.dwy.logistics.service.IPlaceDTOService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/5 17:44
 */
@Service
@Slf4j
public class OrdersServiceImpl implements IOrdersService {

    @Resource
    OrdersMapper ordersMapper;

    @Resource
    PlaceMapper placeMapper;

    @Resource
    GoodsMapper goodsMapper;

    @Resource
    IPlaceDTOService placeDTOService;

    @Override
    public Orders selectOrderByID(Integer id) {
        OrdersKey ordersKey = new OrdersKey();
        ordersKey.setOrderID(id);
        return ordersMapper.selectByPrimaryKey(ordersKey);
    }

    @Override
    public int insertOrder(Orders order) {
        log.debug("insertOrder:" + order.toString());
        return ordersMapper.insert(order);
    }

    @Override
    public int insertOrderByName(int orderID, String startPlaceCity , String startPlaceName , String endPlaceCity , String endPlaceName , int goodsID , int goodsNumber , Date date) {
        Orders order = new Orders();
        order.setOrderID(orderID);
        order.setStartPlaceID( placeDTOService.getFirstPlaceDTO(startPlaceName,startPlaceCity).getId());
        order.setEndPlaceID(placeDTOService.getFirstPlaceDTO(endPlaceName,endPlaceCity).getId());
        order.setGoodsID(goodsID);
        order.setGoodsNumber(goodsNumber);
        order.setTime(date);
        log.debug("insertOrder:" + order.toString());
        return ordersMapper.insert(order);
    }


    @Override
    public int deleteOrder(Integer id) {
        OrdersKey orderKey = new OrdersKey();
        orderKey.setOrderID(id);
        return ordersMapper.deleteByPrimaryKey(orderKey);
    }
}
