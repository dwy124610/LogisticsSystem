package com.dwy.logistics;

import com.dwy.logistics.model.dto.place.PlaceDTO;
import com.dwy.logistics.model.entities.Place;
import com.dwy.logistics.service.IPlaceDTOService;
import com.dwy.logistics.service.IPlaceService;
import com.dwy.logistics.service.IRouteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: DongWenYu
 * @Date: 2020/12/29 9:51
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PlaceServiceTest {

    @Resource
    IPlaceDTOService placeDTOService;
    @Resource
    IPlaceService placeService;
    @Resource
    IRouteService routeService;

    @Test
    public void getAllPlaceTest(){
        String keywords = "孔雀蓝轩";
        String cityName = "杭州市";
        placeDTOService.getAllPlaceDTO(keywords,cityName);
    }

    @Test
    public void getFirstPlaceAndInsertTest(){
        String keywords = "西溪国家湿地公园";
        String cityName = "杭州市";
        PlaceDTO placeDTO= placeDTOService.getFirstPlaceDTO(keywords,cityName);
        Place place = new Place();
        String s[] = placeDTO.getLocation().split(",");
        place.setUid(placeDTO.getId());
        place.setLat(Double.parseDouble(s[1]));
        place.setLng(Double.parseDouble(s[0]));
        place.setName(placeDTO.getName());
        placeService.insertPlace(place);
    }

    @Test
    public void getPlaceDTOByID(){
        String id = "B0FFJ28CTS";
        System.out.println(placeDTOService.getPlaceDTOByID(id).toString());
    }

    @Test
    public void getTrunkDistanceTest(){
        String keywords = "孔雀蓝轩";
        String cityName = "杭州市";
        String keywords1 = "浙江工业大学";
        int size = 2;
        PlaceDTO startPlace = placeDTOService.getAllPlaceDTO(keywords, cityName).get(0);
        System.out.println("startPlace:"+startPlace.toString());
        PlaceDTO endPlace = placeDTOService.getAllPlaceDTO(keywords1,cityName).get(0);
        System.out.println("endPlace:"+endPlace.toString());
        routeService.getTrunkMinDistance(startPlace,endPlace,size);
    }

    @Test
    public void getCarDistanceTest(){
        String keywords = "孔雀蓝轩";
        String cityName = "杭州市";
        String keywords1 = "浙江工业大学";
        PlaceDTO startPlace = placeDTOService.getAllPlaceDTO(keywords, cityName).get(0);
        System.out.println("startPlace:"+startPlace.toString());
        PlaceDTO endPlace = placeDTOService.getAllPlaceDTO(keywords1,cityName).get(0);
        System.out.println("endPlace:"+endPlace.toString());
        System.out.println(routeService.getCarMinDistance(startPlace,endPlace));
    }


}
