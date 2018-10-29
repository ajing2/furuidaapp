package com.furuida.service;

import com.furuida.mapper.OrderMapper;
import com.furuida.model.Order;
import com.furuida.model.OrderExample;
import com.jd.jsf.gd.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public void deleteOrder(Long id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List selectOrder(Order order) {
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        if (order.getId() != null){
            criteria.andIdEqualTo(order.getId());
        }
        if (StringUtils.isNotEmpty(order.getUserId())){
            criteria.andUserIdEqualTo(order.getUserId());
        }
        if (StringUtils.isNotEmpty(order.getOrderNum())){
            criteria.andOrderNumEqualTo(order.getOrderNum());
        }
        if (order.getPayPrice() != null){
            criteria.andPayPriceEqualTo(order.getPayPrice());
        }
        if (order.getIsPay() != null){
            criteria.andIsPayEqualTo(order.getIsPay());
        }
        if (order.getIsShip() != null){
            criteria.andIsShipEqualTo(order.getIsShip());
        }
        if (order.getIsReceipt() != null){
            criteria.andIsReceiptEqualTo(order.getIsReceipt());
        }
        if (StringUtils.isNotEmpty(order.getShipNum())){
            criteria.andShipNumEqualTo(order.getShipNum());
        }
        return orderMapper.selectByExample(orderExample);
    }


    @Override
    public void updateOrder(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public boolean pay() {
        //1、pay
        //2、打钱升级逻辑
        return false;
    }
}
