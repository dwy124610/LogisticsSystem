package com.dwy.logistics.model.dto.place.report;

import com.dwy.logistics.model.dto.place.PlaceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @Author: DongWenYu
 * @Date: 2020/12/28 17:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchPlaceByNameReportDTO {

    private Integer status;

    private String info;

    private Integer count;

    private List<PlaceDTO> tips;
}
