package com.dwy.logistics.service;

import com.dwy.logistics.model.dto.place.PlaceDTO;

import java.util.List;

/**
 * @Author: DongWenYu
 * @Date: 2020/12/29 9:39
 */
public interface IPlaceDTOService {

    /**
     *获得关键字的查询的所有地址
     * @param keywords 查询关键字
     * @param cityName 城市名字
     * @return List<PlaceDTO>
     * @create 2021/1/4 10:55
     */
    List<PlaceDTO> getAllPlaceDTO(String keywords , String cityName);

    PlaceDTO getFirstPlaceDTO(String keywords , String cityName);

    PlaceDTO getPlaceDTOByID(String id);
}
