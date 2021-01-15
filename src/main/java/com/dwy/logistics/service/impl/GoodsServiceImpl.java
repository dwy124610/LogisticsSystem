package com.dwy.logistics.service.impl;

import com.dwy.logistics.mapper.GoodsMapper;
import com.dwy.logistics.model.entities.Goods;
import com.dwy.logistics.model.entities.GoodsKey;
import com.dwy.logistics.service.IGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/5 17:33
 */
@Service
@Slf4j
public class GoodsServiceImpl implements IGoodsService {

    @Resource
    GoodsMapper goodsMapper;
    @Override
    public Goods selectGoodsByID(Integer id) {
        GoodsKey goodsKey = new GoodsKey();
        goodsKey.setGoodsID(id);
        return goodsMapper.selectByPrimaryKey(goodsKey);
    }

    @Override
    public int insertGoods(Goods goods) {
        log.debug("insertGoods:" + goods.toString());
        return goodsMapper.insert(goods);
    }

    @Override
    public int deleteGoods(Integer id) {
        GoodsKey goodsKey = new GoodsKey();
        goodsKey.setGoodsID(id);
        return goodsMapper.deleteByPrimaryKey(goodsKey);
    }
}
