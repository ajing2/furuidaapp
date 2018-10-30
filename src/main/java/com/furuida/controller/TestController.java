package com.furuida.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

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



    @RequestMapping("/ajax")
    @ResponseBody
    public HashMap<String, Object> getAjax(String action) {
        HashMap<String, Object> data = new HashMap<>();
        if (action.equals("addcart")){
            data.put("error", 500);
            data.put("message", "同意协议");
        }
        return data;
    }
}
