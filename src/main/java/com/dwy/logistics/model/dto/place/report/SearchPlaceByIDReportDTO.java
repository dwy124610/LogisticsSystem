package com.dwy.logistics.model.dto.place.report;

import com.dwy.logistics.model.dto.place.PlaceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/4 17:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchPlaceByIDReportDTO {

    private List<PlaceDTO> pois;

    private Integer status;

    private String info;
}
