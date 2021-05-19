package com.dwy.logistics;

import com.dwy.logistics.service.impl.FrontServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @Author: DongWenYu
 * @Date: 2021/5/19 10:17
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FrontTest {
    @Autowired
    FrontServiceImpl frontService;

    @Test
    public void test(){
        frontService.getByRegional(new Date(System.currentTimeMillis()-1000*60*60*24));
    }
}
