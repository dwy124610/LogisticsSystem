package com.dwy.logistics.model.dto.place;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

/**
 * @Author: DongWenYu
 * @Date: 2020/12/28 17:41
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDTO {
    private String id;

    private String name;

    private String adcode;

    private String location;

    private String address;
}
