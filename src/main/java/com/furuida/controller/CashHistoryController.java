package com.furuida.controller;

import com.furuida.model.CashHistory;
import com.furuida.model.Order;
import com.furuida.model.User;
import com.furuida.service.CashHistoryService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName CashHistoryController
 * @Description TODO
 * @Author lingxiangxiang
 * @Date 9:40 AM
 * @Version 1.0
 **/

@RestController
@RequestMapping("cashHistory")
public class CashHistoryController {
    Log log = LogFactory.getLog(CashHistoryController.class);

    @Resource
    CashHistoryService cashHistoryService;



    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ResponseBody
    private List<CashHistory> getAllCashHistory(CashHistory cashHistory) {
        try {
            return cashHistoryService.getAllCashHistory(cashHistory);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
