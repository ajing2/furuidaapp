package com.furuida.controller;

import com.furuida.model.Order;
import com.furuida.model.User;
import com.furuida.service.OrderService;
import com.furuida.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
@RequestMapping("user")
public class UserController {
    Log log = LogFactory.getLog(UserController.class);
    @Resource
    UserService userService;
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    private String addUser(@RequestBody User user) {
        try {
            userService.addUser(user);
            return "ok";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "failed";
        }
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    private String deleteUser(@RequestParam Long id) {
        try {
            userService.deleteUser(id);
            return "ok";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "failed";
        }
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    private String updateUser(@RequestBody User user) {
        try {
            userService.updateUser(user);
            return "ok";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "failed";
        }
    }

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ResponseBody
    private List<Order> selectUser(User user) {
        try {
            return userService.selectUser(user);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    @ResponseBody
    private String getToken(@RequestParam String code) {
        try {
            return userService.getToken(code);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
