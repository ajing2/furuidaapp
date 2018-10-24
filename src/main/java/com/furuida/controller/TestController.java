package com.furuida.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: furuidaapp
 * @description: contr
 * @author: fuzhengquan
 * @create: 2018-10-24 21:24
 **/
@Controller
@RequestMapping("test")
public class TestController {
    @RequestMapping("/t")
    @ResponseBody
    public String getTest() {
        return "66666";
    }
}
