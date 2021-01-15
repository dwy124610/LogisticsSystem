package com.dwy.logistics.service.impl;

import com.dwy.logistics.consts.CONST;
import com.dwy.logistics.model.dto.distance.report.CarRouteReportDTO;
import com.dwy.logistics.model.dto.distance.report.TruckRouteReportDTO;
import com.dwy.logistics.model.dto.distance.PathDTO;
import com.dwy.logistics.model.dto.front.PlaceFrontDTO;
import com.dwy.logistics.model.dto.place.PlaceDTO;
import com.dwy.logistics.service.IPlaceDTOService;
import com.dwy.logistics.service.IRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/4 16:52
 */
@Service
@Slf4j
public class RouteServiceImpl extends AbstractServiceImpl implements IRouteService {

    @Resource
    IPlaceDTOService placeService;

    @Override
    public double getTrunkMinDistance(PlaceDTO startPlace, PlaceDTO endPlace, int size) {
        //https://restapi.amap.com/v4/direction/truck?parameters
        TruckRouteReportDTO truckRouteReportDTO =
                sendGet("https://restapi.amap.com/v4/direction/truck?key="+ CONST.GAODE_MAP_KEY+"&origin="+startPlace.getLocation()+"&originid="+startPlace.getId()+"&destination="+endPlace.getLocation()+"&destinationid="+endPlace.getId()+"&size="+size)
                        .toJavaObject(TruckRouteReportDTO.class);
        List<PathDTO> paths = truckRouteReportDTO.getData().getRoute().getPaths();
        return getMinDistance(paths);
    }

    @Override
    public double getCarMinDistance(PlaceDTO startPlace, PlaceDTO endPlace) {
        CarRouteReportDTO carRouteReportDTO =
                sendGet("https://restapi.amap.com/v3/direction/driving?key="+ CONST.GAODE_MAP_KEY+"&origin="+startPlace.getLocation()+"&originid="+startPlace.getId()+"&destination="+endPlace.getLocation()+"&destinationid="+endPlace.getId()+"&strategy="+"10")
                        .toJavaObject(CarRouteReportDTO.class);
        List<PathDTO> paths = carRouteReportDTO.getRoute().getPaths();
        return getMinDistance(paths);
    }

    @Override
    public double getCarMinDistance(String startPlaceID, String endPlaceID) {
        PlaceDTO startPlace = placeService.getPlaceDTOByID(startPlaceID);
        PlaceDTO endPlace = placeService.getPlaceDTOByID(endPlaceID);
        return getCarMinDistance(startPlace,endPlace);
    }

    @Override
    public double getCarMinDistance(PlaceFrontDTO startPlace, PlaceFrontDTO endPlace) {
        String url = "https://restapi.amap.com/v3/direction/driving?key="+ CONST.GAODE_MAP_KEY+"&origin="+startPlace.getLng()+","+startPlace.getLat()+"&destination="+endPlace.getLng()+","+endPlace.getLat()+"&strategy="+"10";
        log.info("url:"+url);
        CarRouteReportDTO carRouteReportDTO =
                sendGet(url)
                        .toJavaObject(CarRouteReportDTO.class);
        List<PathDTO> paths = carRouteReportDTO.getRoute().getPaths();
        return getMinDistance(paths);
    }

    private double getMinDistance(List<PathDTO> paths){
        double minDistance = paths.get(0).getDistance();
        for (int i = 1 ; i <paths.size() ; i++){
            if (minDistance > paths.get(i).getDistance()) {
                minDistance = paths.get(i).getDistance();
            }
        }
        return minDistance;
    }
}
