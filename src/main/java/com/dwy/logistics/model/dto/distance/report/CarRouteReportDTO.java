package com.dwy.logistics.model.dto.distance.report;

import com.dwy.logistics.model.dto.distance.RouteDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/5 9:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarRouteReportDTO {

    private Integer status;

    private String info;

    private Integer count;

    private RouteDTO route;
}
