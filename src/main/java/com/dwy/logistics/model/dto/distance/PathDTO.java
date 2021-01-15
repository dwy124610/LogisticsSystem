package com.dwy.logistics.model.dto.distance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/4 15:38
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PathDTO {

    private Integer distance;

    private Integer duration;

    private Integer tolls;

}
