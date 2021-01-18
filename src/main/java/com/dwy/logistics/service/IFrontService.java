package com.dwy.logistics.service;

import com.dwy.logistics.model.dto.front.CarFrontDTO;
import com.dwy.logistics.model.dto.front.PlaceFrontDTO;
import com.dwy.logistics.model.dto.front.RouteFrontDTO;
import com.dwy.logistics.model.dto.front.TransportFrontDTO;

import java.util.Date;
import java.util.List;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/12 15:43
 */
public interface IFrontService {

    List<PlaceFrontDTO> getPlaceFrontDTO(Date date);

    List<CarFrontDTO> getCarFrontDTO(Date date);

    List<TransportFrontDTO> getTransportFrontDTO(Date date);

    List<RouteFrontDTO> getRouteFrontDTO(Date date);
}
