package com.furuida.controller;

import com.furuida.model.Order;
import com.furuida.service.OrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    private String deleteOrder(@RequestParam Long id) {
        try {
            orderService.deleteOrder(id);
            return "ok";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "failed";
        }
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    private String updateOrder(@RequestBody Order order) {
        try {
            orderService.updateOrder(order);
            return "ok";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "failed";
        }
    }

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ResponseBody
    private List<Order> selectOrder(@RequestBody Order order) {
        try {
            return orderService.selectOrder(order);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
