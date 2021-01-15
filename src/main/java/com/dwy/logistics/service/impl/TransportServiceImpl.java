package com.dwy.logistics.service.impl;

import com.dwy.logistics.mapper.TransportMapper;
import com.dwy.logistics.model.entities.Transport;
import com.dwy.logistics.service.ITransportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/8 15:27
 */
@Service
@Slf4j
public class TransportServiceImpl implements ITransportService {

    @Resource
    TransportMapper transportMapper;

    @Override
    public int insertTransport(Transport transport) {
        return transportMapper.insert(transport);
    }

}
