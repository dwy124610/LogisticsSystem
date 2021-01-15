package com.dwy.logistics.service;

import com.dwy.logistics.model.entities.Car;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/5 17:17
 */
public interface ICarService {

    Car selectCarByID(Integer id);

    int insertCar(Car car);

    int deleteCar(Integer id);
}
