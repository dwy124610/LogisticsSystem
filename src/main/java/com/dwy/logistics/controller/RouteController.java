package com.dwy.logistics.controller;

import com.dwy.logistics.model.dto.place.PlaceDTO;
import com.dwy.logistics.service.IPlaceDTOService;
import com.dwy.logistics.service.IRouteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/4 16:28
 */
@RestController
@RequestMapping("/route")
public class RouteController {

    @Resource
    IRouteService routeService;
    @Resource
    IPlaceDTOService placeService;

    @GetMapping("/getTrunkMinDistance")
    public double getTrunkMinDistance(@RequestParam("startID") String startID,
                              @RequestParam("endID") String endID,
                              @RequestParam("size") int size){
        PlaceDTO startPlace = placeService.getPlaceDTOByID(startID);
        PlaceDTO endPlace = placeService.getPlaceDTOByID(endID);
        return routeService.getTrunkMinDistance(startPlace,endPlace,size);
    }

    @GetMapping("/getCarMinDistance")
    public double getCarMinDistance(@RequestParam("startID") String startID,
                              @RequestParam("endID") String endID){
        return routeService.getCarMinDistance(startID,endID);
    }
}
