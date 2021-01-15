package com.dwy.logistics;

import com.dwy.logistics.model.dto.front.PlaceFrontDTO;
import com.dwy.logistics.service.IRouteService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/13 16:31
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RouteServiceTest {

    @Resource
    IRouteService routeService;

//    public void getCarMinDistanceTest(){
//        PlaceFrontDTO startPlace = new PlaceFrontDTO();
//        PlaceFrontDTO endPlace = new PlaceFrontDTO();
//        startPlace.set
//        routeService.getCarMinDistance()
//    }
}
