package com.furuida.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @ClassName WechatPayController
 * @Description TODO
 * @Author lingxiangxiang
 * @Date 9:23 PM
 * @Version 1.0
 **/
@Controller
@RequestMapping("/wxpay")
public class WechatPayController {

    Log log = LogFactory.getLog(WechatPayController.class);

//    @RequestMapping("jspay")
//    public Map<String, Object> jsPay(@ModelAttribute(value = "params") UnifiedOrderParams params) {
//
//    }
}
