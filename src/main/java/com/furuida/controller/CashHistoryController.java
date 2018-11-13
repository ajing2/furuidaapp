package com.furuida.controller;

import com.furuida.model.CashHistory;
import com.furuida.model.Order;
import com.furuida.model.ResultBean;
import com.furuida.model.User;
import com.furuida.service.CashHistoryService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    private ResultBean updateCashHistory(@RequestBody CashHistory cashHistory) {
        try {
            cashHistoryService.updateCashHistory(cashHistory);
            return ResultBean.success("ok");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultBean.fail("error.");
        }
    }

}
