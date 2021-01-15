package com.dwy.logistics.controller;

import com.dwy.logistics.model.entities. Goods;
import com.dwy.logistics.service.IGoodsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/5 17:35
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    
    @Resource
    IGoodsService goodsService;

    @GetMapping("/selectByID")
    public Goods selectGoodsByID(@RequestParam("id") int id){
        return goodsService.selectGoodsByID(id);
    }

    @PostMapping("/insert")
    public int insertGoods(@RequestBody Goods goods){
        return goodsService.insertGoods(goods);
    }

    @DeleteMapping("/delete")
    public int deleteGoods(@RequestParam("id") int id){
        return goodsService.deleteGoods(id);
    }
}
