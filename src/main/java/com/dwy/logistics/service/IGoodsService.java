package com.dwy.logistics.service;

import com.dwy.logistics.model.entities.Goods;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/5 17:32
 */
public interface IGoodsService {

    Goods selectGoodsByID(Integer id);

    int insertGoods(Goods goods);

    int deleteGoods(Integer id);
}
