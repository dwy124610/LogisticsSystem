package com.dwy.logistics.controller;

import com.dwy.logistics.model.dto.place.PlaceDTO;
import com.dwy.logistics.service.IPlaceDTOService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/4 16:19
 */
@RestController
@RequestMapping("/place")
public class PlaceDTOController {

    @Resource
    IPlaceDTOService placeDTOService;

    @GetMapping("/getAll")
    public List<PlaceDTO> getAllPlace(@RequestParam("keywords") String keywords,
                                      @RequestParam("cityName") String cityName){
        return placeDTOService.getAllPlaceDTO(keywords,cityName);
    }

    @GetMapping("/getFirst")
    public PlaceDTO getFirstPlace(@RequestParam("keywords") String keywords,
                                      @RequestParam("cityName") String cityName){
        return placeDTOService.getFirstPlaceDTO(keywords,cityName);
    }

}
