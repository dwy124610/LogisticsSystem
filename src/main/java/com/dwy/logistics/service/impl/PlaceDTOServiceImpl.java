package com.dwy.logistics.service.impl;

import com.dwy.logistics.consts.CONST;
import com.dwy.logistics.mapper.CodeMapper;
import com.dwy.logistics.model.dto.place.report.SearchPlaceByIDReportDTO;
import com.dwy.logistics.model.dto.place.report.SearchPlaceByNameReportDTO;
import com.dwy.logistics.model.dto.place.PlaceDTO;
import com.dwy.logistics.model.entities.Code;
import com.dwy.logistics.model.entities.CodeExample;
import com.dwy.logistics.service.IPlaceDTOService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: DongWenYu
 * @Date: 2020/12/29 9:41
 */
@Service
@Slf4j
public class PlaceDTOServiceImpl extends AbstractServiceImpl implements IPlaceDTOService {

    private int minDistance = -1;

    @Resource
    private CodeMapper codeMapper;

    @Override
    public List<PlaceDTO> getAllPlaceDTO(String keywords , String cityName) {

        String code = cityNameToCode(cityName);

       // https://restapi.amap.com/v3/assistant/inputtips?parameters //GET请求
        SearchPlaceByNameReportDTO searchPlaceByNameReportDTO =
                sendGet("https://restapi.amap.com/v3/assistant/inputtips?key="+CONST.GAODE_MAP_KEY+"&keywords="+keywords+"&city="+code)
                        .toJavaObject(SearchPlaceByNameReportDTO.class);
        log.info("keywords:"+keywords+" cityName:"+cityName+" 查询得allPlaceDTO:"+ searchPlaceByNameReportDTO.toString());
        return searchPlaceByNameReportDTO.getTips();
    }

    @Override
    public PlaceDTO getFirstPlaceDTO(String keywords, String cityName) {
        try {
            PlaceDTO placeDTO = getAllPlaceDTO(keywords, cityName).get(0);
            log.info("keywords:" + keywords + " cityName:" + cityName + " 查询得PlaceDTO:" + placeDTO.toString());
            return placeDTO;
        }catch (IndexOutOfBoundsException e){
            log.error("找不到该地点,keywords:"+keywords+",cityName:"+cityName);
            return null;
        }
    }

    @Override
    public PlaceDTO getPlaceDTOByID(String id) {
        SearchPlaceByIDReportDTO searchPlaceByIDReportDTO = sendGet("https://restapi.amap.com/v3/place/detail?key="+CONST.GAODE_MAP_KEY+"&&id="+id)
                .toJavaObject(SearchPlaceByIDReportDTO.class);
        return searchPlaceByIDReportDTO.getPois().get(0);
    }

    public String cityNameToCode(String cityName){
        CodeExample example = new CodeExample();
        CodeExample.Criteria criteria = example.createCriteria();
        criteria.andNameLike(cityName+"%");
        List<Code> codes = codeMapper.selectByExample(example);
        Code code = codes.get(0);
        return code.getCitycode()+"/"+code.getAdcode();
    }

}
