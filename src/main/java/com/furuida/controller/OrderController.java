package com.furuida.controller;

import com.furuida.model.Order;
import com.furuida.model.ResultBean;
import com.furuida.model.User;
import com.furuida.service.OrderService;
import com.furuida.utils.CacheQueueManager;
import org.apache.commons.lang.StringUtils;
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

    /**
     * 下订单
     * @param order
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    private ResultBean addOrder(@RequestBody Order order, @RequestParam String parentId) {
        try {
            orderService.addOrder(order);
            return ResultBean.success("ok");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultBean.fail("fail");
        }
    }

    /**
     * 支付
     * @param userId
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    @ResponseBody
    private ResultBean pay(@RequestParam String userId, @RequestParam String parentId) {
        try {
            return orderService.pay(userId, parentId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultBean.fail("fail");
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
    private List<Order> selectOrder(Order order) {
        try {
            return orderService.selectOrder(order);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    private List<Order> queryAndUser(Order order) {
        try {
            return orderService.queryAndUser(order);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
