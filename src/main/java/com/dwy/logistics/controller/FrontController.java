package com.dwy.logistics.controller;

import com.dwy.logistics.model.dto.front.CarFrontDTO;
import com.dwy.logistics.model.dto.front.PlaceFrontDTO;
import com.dwy.logistics.model.dto.front.RouteFrontDTO;
import com.dwy.logistics.model.dto.front.TransportFrontDTO;
import com.dwy.logistics.service.IFrontService;
import com.dwy.logistics.service.IOrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/12 15:42
 */
@RestController
@Slf4j
@RequestMapping("/front")
public class FrontController {

    @Resource
    IFrontService frontService;

    @GetMapping("/place")
    public List<PlaceFrontDTO> getPlaceFrontDTO(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return frontService.getPlaceFrontDTO(date);
    }

    @GetMapping("/car")
    public  List<CarFrontDTO> getCarFrontDTO(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return frontService.getCarFrontDTO(date);
    }

    @GetMapping("/transport")
    public  List<TransportFrontDTO> getTransportFrontDTO(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return frontService.getTransportFrontDTO(date);
    }

    @GetMapping("/route")
    public  List<RouteFrontDTO> getRouteFrontDTO(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return frontService.getRouteFrontDTO(date);
    }
}
