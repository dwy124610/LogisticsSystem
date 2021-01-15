package com.dwy.logistics.model.dto.distance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/4 15:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataDTO {

    private Integer count;

    private RouteDTO route;
}
