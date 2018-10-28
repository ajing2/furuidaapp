package com.furuida.service;

import com.furuida.model.Order;

public interface OrderService {
    void addOrder(Order order);
    boolean pay();
}
