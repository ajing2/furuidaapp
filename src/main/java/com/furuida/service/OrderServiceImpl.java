package com.furuida.service;

import com.furuida.mapper.OrderMapper;
import com.furuida.model.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: furuidaapp
 * @description: OrderServiceImpl
 * @author: fuzhengquan
 * @create: 2018-10-27 20:16
 **/
@Component
public class OrderServiceImpl implements OrderService {
    @Resource
    OrderMapper orderMapper;
    @Override
    public void addOrder(Order order) {
        orderMapper.insertSelective(order);
    }
}
