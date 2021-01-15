package com.dwy.logistics.model.dto.distance.report;

import com.dwy.logistics.model.dto.distance.DataDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: DongWenYu
 * @Date: 2020/12/29 10:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TruckRouteReportDTO {

    private DataDTO data;

    private Integer errcode;

    private String errmsg;

    private String errdetail;

}
