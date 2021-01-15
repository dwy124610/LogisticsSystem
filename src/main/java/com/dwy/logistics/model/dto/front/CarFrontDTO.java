package com.dwy.logistics.model.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/12 15:28
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CarFrontDTO implements Comparable<CarFrontDTO>{

    private Integer account;

    private Double volume;


    @Override
    public int compareTo(CarFrontDTO o) {
        if (this.volume < o.volume) {
            return 1;
        }else if (this.volume.equals(o.volume)){
            return 0;
        }else {
            return -1;
        }
    }
}
