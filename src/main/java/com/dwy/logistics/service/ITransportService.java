package com.dwy.logistics.service;

import com.dwy.logistics.model.entities.Transport;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/6 11:41
 */
public interface ITransportService {

    int insertTransport(Transport transport);

}
