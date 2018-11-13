package com.furuida.service;

import com.furuida.model.Order;
import com.furuida.model.ResultBean;

import java.util.List;

public interface OrderService {
    void addOrder(Order order);

    public void updateOrder(Order order);

    public void deleteOrder(Long id);

    public List selectOrder(Order order);

    ResultBean pay(String userId, String parentId);
}
