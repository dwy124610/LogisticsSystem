package com.dwy.logistics.service.impl;

import com.dwy.logistics.mapper.CarMapper;
import com.dwy.logistics.model.entities.Car;
import com.dwy.logistics.model.entities.CarKey;
import com.dwy.logistics.service.ICarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/5 17:19
 */
@Service
@Slf4j
public class CarServiceImpl implements ICarService {

    @Resource
    CarMapper carMapper;
    @Override
    public Car selectCarByID(Integer id) {
        CarKey carKey = new CarKey();
        carKey.setCarID(id);
        return carMapper.selectByPrimaryKey(carKey);
    }

    @Override
    public int insertCar(Car car) {
        log.debug("Car:"+car.toString());
        return carMapper.insert(car);
    }

    @Override
    public int deleteCar(Integer id) {
        CarKey carKey = new CarKey();
        carKey.setCarID(id);
        return carMapper.deleteByPrimaryKey(carKey);
    }
}
