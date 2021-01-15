package com.dwy.logistics;

import com.dwy.logistics.service.ITransportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/6 13:45
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TransportServiceTest {

    @Resource
    ITransportService transportService;

    @Test
    public void getHighestLoadRateTest(){
//        transportService.getHighestLoadRate(2021-01-06);
    }
}
