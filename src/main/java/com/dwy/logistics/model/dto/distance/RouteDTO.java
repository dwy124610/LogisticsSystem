package com.dwy.logistics.model.dto.distance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/4 15:04
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO {

    private String origin;

    private String destination;

    private List<PathDTO> paths;
}
