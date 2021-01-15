package com.dwy.logistics.model.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/13 14:18
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransportFrontDTO {

    private Double startLng;

    private Double startLat;

    private Double endLng;

    private Double endLat;

    private Double volume;
}
