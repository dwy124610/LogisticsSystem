package com.dwy.logistics.controller;

import com.dwy.logistics.service.ITransportHighestLoadRate;
import com.dwy.logistics.service.ITransportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/6 13:50
 */
@RestController
@RequestMapping("/transport")
public class TransportHighestLoadRateController {

    @Resource
    ITransportHighestLoadRate transportHighestLoadRate;

    @GetMapping("/loadRate")
    public void getHighestLoadRate(@RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date){
        transportHighestLoadRate.getHighestLoadRate(date);
    }
}
