package com.dwy.logistics.model.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/16 15:57
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RouteFrontDTO {

    private String startName;
    private Double startLng;
    private Double startLat;

    private String endName;
    private Double endLng;
    private Double endLat;

    private List<TransportFrontDTO> transportInformation ;

    private Double totalVolume;
}
