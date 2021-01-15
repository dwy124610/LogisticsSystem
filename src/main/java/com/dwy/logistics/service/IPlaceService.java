package com.dwy.logistics.service;

import com.dwy.logistics.model.entities.Place;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/5 17:51
 */
public interface IPlaceService {

    Place selectPlaceByID(String id);

    int insertPlace(Place place);

    int insertPlaceByName(String keywords , String cityName);

    int deletePlace(String id);
}
