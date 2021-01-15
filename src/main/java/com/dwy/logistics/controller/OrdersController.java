package com.dwy.logistics.controller;

import com.dwy.logistics.model.dto.front.PlaceFrontDTO;
import com.dwy.logistics.model.entities.Orders;
import com.dwy.logistics.service.IOrdersService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/5 17:46
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Resource
    IOrdersService orderService;

    @GetMapping("/selectByID")
    public Orders selectOrderByID(@RequestParam("id") int id){
        return orderService.selectOrderByID(id);
    }

    @PostMapping("/insert")
    public int insertOrder(@RequestBody Orders order){
        return orderService.insertOrder(order);
    }

    @PostMapping("/insertByName")
    public int insertOrderByName(@RequestParam("orderID") int orderID,
                                 @RequestParam("startPlaceCity") String startPlaceCity ,
                                 @RequestParam("startPlaceName") String startPlaceName ,
                                 @RequestParam("endPlaceCity") String endPlaceCity ,
                                 @RequestParam("endPlaceName") String endPlaceName ,
                                 @RequestParam("goodsID") int goodsID ,
                                 @RequestParam("goodsNumber") int goodsNumber ,
                                 @RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date){
        return orderService.insertOrderByName(orderID,startPlaceCity,startPlaceName,endPlaceCity,endPlaceName,goodsID,goodsNumber,date);
    }


    @DeleteMapping("/delete")
    public int deleteOrder(@RequestParam("id") int id){
        return orderService.deleteOrder(id);
    }

}
