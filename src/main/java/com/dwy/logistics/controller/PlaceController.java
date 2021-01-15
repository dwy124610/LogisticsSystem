package com.dwy.logistics.controller;

import com.dwy.logistics.model.entities.Place;
import com.dwy.logistics.service.IPlaceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/5 17:55
 */
@RestController
@RequestMapping("/place")
public class PlaceController {

    @Resource
    IPlaceService placeService;

    @GetMapping("/selectByID")
    public Place selectPlaceByID(@RequestParam("id") String id){
        return placeService.selectPlaceByID(id);
    }

    @PostMapping("/insert")
    public int insertPlace(@RequestBody Place place){
        return placeService.insertPlace(place);
    }

    @PostMapping("/insertByName")
    public int insertPlaceByName(@RequestParam("keywords") String keywords,
                                 @RequestParam("cityName") String cityName){
        return placeService.insertPlaceByName(keywords,cityName);
    }

    @DeleteMapping("/delete")
    public int deletePlace(@RequestParam("id") String id){
        return placeService.deletePlace(id);
    }
}
