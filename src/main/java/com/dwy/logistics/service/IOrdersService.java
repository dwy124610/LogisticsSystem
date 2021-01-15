package com.dwy.logistics.service;

import com.dwy.logistics.model.dto.front.PlaceFrontDTO;
import com.dwy.logistics.model.entities.Orders;

import java.util.Date;
import java.util.List;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/5 17:43
 */
public interface IOrdersService {

    Orders selectOrderByID(Integer id);

    int insertOrder(Orders order);

    int insertOrderByName(int orderID, String startPlaceCity , String startPlaceName , String endPlaceCity , String endPlaceName , int goodsID , int goodsNumber , Date date);

    int deleteOrder(Integer id);
}
