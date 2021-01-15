package com.dwy.logistics.model.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Comparator;
import java.util.Objects;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/12 10:19
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlaceFrontDTO implements Comparable<PlaceFrontDTO> {

    private String id;

    private String name;

    private Double lng;

    private Double lat;

    private Double volume = 0.0;
    @Override
    public int compareTo(PlaceFrontDTO o) {
        if (this.volume < o.volume) {
            return 1;
        }else if (this.volume.equals(o.volume)){
            return 0;
        }else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceFrontDTO that = (PlaceFrontDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(lng, that.lng) &&
                Objects.equals(lat, that.lat) &&
                Objects.equals(volume, that.volume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lng, lat, volume);
    }
}
