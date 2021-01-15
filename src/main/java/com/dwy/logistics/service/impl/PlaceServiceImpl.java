package com.dwy.logistics.service.impl;

import com.dwy.logistics.mapper.PlaceMapper;
import com.dwy.logistics.model.dto.place.PlaceDTO;
import com.dwy.logistics.model.entities.Place;
import com.dwy.logistics.model.entities.PlaceKey;
import com.dwy.logistics.service.IPlaceDTOService;
import com.dwy.logistics.service.IPlaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/5 17:52
 */
@Service
@Slf4j
public class PlaceServiceImpl implements IPlaceService {

    @Resource
    PlaceMapper placeMapper;
    @Resource
    IPlaceDTOService placeDTOService;

    @Override
    public Place selectPlaceByID(String id) {
        PlaceKey placeKey = new PlaceKey();
        placeKey.setUid(id);
        return placeMapper.selectByPrimaryKey(placeKey);
    }

    @Override
    public int insertPlace(Place place) {
        log.debug("insertPlace:"+place.toString());
        return placeMapper.insert(place);
    }

    @Override
    public int insertPlaceByName(String keywords, String cityName) {
        log.debug("insertPlaceByName,keywords:"+keywords+",cityName:"+cityName);
        PlaceDTO placeDTO = placeDTOService.getFirstPlaceDTO(keywords, cityName);
        Place place = PlaceDTOToPlace(placeDTO);
        log.debug("insertPlaceByName:"+place.toString());
        return placeMapper.insert(place);
    }

    @Override
    public int deletePlace(String id) {
        PlaceKey placeKey = new PlaceKey();
        placeKey.setUid(id);
        return placeMapper.deleteByPrimaryKey(placeKey);
    }

    private Place PlaceDTOToPlace(PlaceDTO placeDTO){
        Place place = new Place();
        String s[] = placeDTO.getLocation().split(",");
        place.setUid(placeDTO.getId());
        place.setLat(Double.parseDouble(s[1]));
        place.setLng(Double.parseDouble(s[0]));
        place.setName(placeDTO.getName());
        return place;
    }
}
