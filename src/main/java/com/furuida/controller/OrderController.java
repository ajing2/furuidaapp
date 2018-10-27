package com.furuida.controller;

import com.furuida.model.Order;
import com.furuida.service.OrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: furuidaapp
 * @description: OrderController
 * @author: fuzhengquan
 * @create: 2018-10-27 20:18
 **/
@RestController
@RequestMapping("order")
public class OrderController {
    Log log = LogFactory.getLog(OrderController.class);
    @Resource
    OrderService orderService;
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    private String addOrder(@RequestBody Order order) {
        try {
            orderService.addOrder(order);
            return "ok";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "failed";
        }
    }
}
