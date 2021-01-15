package com.dwy.logistics.controller;

import com.dwy.logistics.model.entities.Car;
import com.dwy.logistics.service.ICarService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/5 17:22
 */
@RestController
@RequestMapping("/car")
public class CarController {

    @Resource
    ICarService carService;

    @GetMapping("/selectByID")
    public Car selectCarByID(@RequestParam("id") int id){
       return carService.selectCarByID(id);
    }

    @PostMapping("/insert")
    public int insertCar(@RequestBody Car car){
        return carService.insertCar(car);
    }

    @DeleteMapping("/delete")
    public int deleteCar(@RequestParam("id") int id){
        return carService.deleteCar(id);
    }
}
