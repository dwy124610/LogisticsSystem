package com.dwy.logistics.service;

import com.dwy.logistics.model.dto.front.PlaceFrontDTO;
import com.dwy.logistics.model.dto.place.PlaceDTO;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/4 16:52
 */
public interface IRouteService {

    /**
     * 通过起始地点和结束地点获得货车最短距离
     * @param startPlace 起始地点
     * @param endPlace 结束地点
     * @param size 货车类型 ：微型车，2：轻型车（默认值），3：中型车，4：重型车
     * @return void
     * @create 2021/1/4 16:05
     */
    double getTrunkMinDistance(PlaceDTO startPlace, PlaceDTO endPlace , int size);

    /**
     * 通过起始地点和结束地点获得驾车最短距离
     * @param startPlace 起始地点
     * @param endPlace 结束地点
     * @return int
     * @create 2021/1/5 9:23
     */
    double getCarMinDistance(PlaceDTO startPlace, PlaceDTO endPlace );

    double getCarMinDistance(String startPlaceID , String endPlaceID);

    double getCarMinDistance(PlaceFrontDTO startPlace, PlaceFrontDTO endPlace );

}
