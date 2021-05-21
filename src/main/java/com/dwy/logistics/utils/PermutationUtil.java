package com.dwy.logistics.utils;

import com.dwy.logistics.model.dto.front.PlaceFrontDTO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: DongWenYu
 * @Date: 2021/5/20 15:53
 */
public class PermutationUtil {
    //当前打印的第几个排列
    private int row = 0;
    //存储排列的结果
    private List<List<PlaceFrontDTO>> result;


    public PermutationUtil(List<PlaceFrontDTO> placeFrontDTO , Integer start) {
        this.row = 0;
        result = new ArrayList<>();
        for (int i = 0; i < factor(placeFrontDTO.size() - start); i++) {
            result.add(new ArrayList<>());
        }

    }

    //计算阶乘
    public int factor(int a) {
        int r = 1;
        for (; a >= 1; a--) {
            r *= a;
        }
        return r;
    }

    public List<List<PlaceFrontDTO>> getResult() {
        return result;
    }

    public void perm(List<PlaceFrontDTO> array, int start) {
        if (start == array.size()) {
            for (int i = 0; i < array.size(); i++) {
                this.result.get(row).add(i, array.get(i));
            }
            this.row++;
        } else {
            for (int i = start; i < array.size(); i++) {
                swap(array, start, i);
                perm(array, start + 1);
            }
        }
    }


    public void swap(List<PlaceFrontDTO> array, int s, int i) {
        PlaceFrontDTO placeFrontDTO = array.get(s);
        PlaceFrontDTO placeFrontDTO1 = array.get(i);
        PlaceFrontDTO temp = new PlaceFrontDTO();
        PlaceFrontDTO temp1 = new PlaceFrontDTO();
        BeanUtils.copyProperties(placeFrontDTO, temp);
        BeanUtils.copyProperties(placeFrontDTO1, temp1);
        array.remove(placeFrontDTO);
        array.add(s, temp1);
        array.remove(placeFrontDTO1);
        array.add(i, temp);
    }


    public static void main(String[] args) {
        List<PlaceFrontDTO> a = new ArrayList<>();
        a.add(new PlaceFrontDTO("1"));
        a.add(new PlaceFrontDTO("2"));
        a.add(new PlaceFrontDTO("3"));
        PermutationUtil p = new PermutationUtil(a , 1);
        p.perm(a, 1);
        System.out.println(p.result);
    }

    public static List<List<PlaceFrontDTO>> getResult(List<PlaceFrontDTO> placeFrontDTOS , Integer start){
        PermutationUtil p = new PermutationUtil(placeFrontDTOS , start);
        p.perm(placeFrontDTOS,start);
        return p.getResult();
    }
}
