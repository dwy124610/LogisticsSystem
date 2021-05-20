package com.dwy.logistics;

import com.dwy.logistics.mapper.OrdersMapper;
import com.dwy.logistics.mapper.PlaceMapper;
import com.dwy.logistics.model.entities.Orders;
import com.dwy.logistics.model.entities.OrdersExample;
import com.dwy.logistics.model.entities.Place;
import com.dwy.logistics.model.entities.PlaceExample;
import com.dwy.logistics.service.IOrdersService;
import com.dwy.logistics.service.IPlaceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @Author: DongWenYu
 * @Date: 2021/5/7 14:07
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderTest {
    @Resource
    OrdersMapper ordersMapper;
    @Resource
    PlaceMapper placeMapper;

    @Test
    public void insertOrder(){
        String startId = "B023B149VY";
        Random random = new Random();
        List<Place> places = placeMapper.selectByExample(new PlaceExample());
        List<String> placeIds = places.stream().filter(p -> !p.getUid().equals(startId)).map(p -> p.getUid()).collect(Collectors.toList());
        int size = placeIds.size();
        for (int i = 0 ; i <200 ; i++){
            Orders orders = new Orders();
            orders.setStartPlaceID(startId);
            orders.setEndPlaceID(placeIds.get(random.nextInt(size)));
            orders.setGoodsID(random.nextInt(10)+1);
            orders.setGoodsNumber(random.nextInt(40)+10);
            orders.setTime(new Date());
            ordersMapper.insert(orders);
        }
    }

    @Test
    public void deleteOrder(){
        OrdersExample ordersExample = new OrdersExample();
        OrdersExample.Criteria criteria = ordersExample.createCriteria();
        criteria.andTimeEqualTo(new Date());
        ordersMapper.deleteByExample(ordersExample);
    }
}
