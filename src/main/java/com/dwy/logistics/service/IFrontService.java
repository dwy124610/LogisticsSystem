package com.dwy.logistics.service;

import com.dwy.logistics.model.dto.front.CarFrontDTO;
import com.dwy.logistics.model.dto.front.PlaceFrontDTO;
import com.dwy.logistics.model.dto.front.RouteFrontDTO;
import com.dwy.logistics.model.dto.front.TransportFrontDTO;

import java.util.Date;
import java.util.List;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/12 15:43
 */
public interface IFrontService {

    /**
     * 获得当天订单中所有的地点
     * @param date 日期
     * @return java.util.List<com.dwy.logistics.model.dto.front.PlaceFrontDTO>
     * @create 2021/1/18 17:53
     */
    List<PlaceFrontDTO> getPlaceFrontDTO(Date date);

    /**
     * 获得配送当天订单装载率最高的汽车集合
     * @param date 日期
     * @return java.util.List<com.dwy.logistics.model.dto.front.CarFrontDTO>
     * @create 2021/1/18 17:54
     */
    List<CarFrontDTO> getCarFrontDTO(Date date);

    /**
     * 获得配送订单的配送集合（运输信息）
     * @param date 日期
     * @return java.util.List<com.dwy.logistics.model.dto.front.TransportFrontDTO>
     * @create 2021/1/18 17:54
     */
    List<TransportFrontDTO> getTransportFrontDTO(Date date);

    /**
     * 获得车子配送订单路途的集合（以车子为单位）
     * @param date 日期
     * @return java.util.List<com.dwy.logistics.model.dto.front.RouteFrontDTO>
     * @create 2021/1/18 17:56
     */
    List<RouteFrontDTO> getRouteFrontDTO(Date date);
}
