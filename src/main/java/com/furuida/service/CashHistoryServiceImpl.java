package com.furuida.service;

import com.furuida.mapper.CashHistoryMapper;
import com.furuida.model.CashHistory;
import com.furuida.model.CashHistoryExample;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName CashHistoryServiceImpl
 * @Description TODO
 * @Author lingxiangxiang
 * @Date 9:47 AM
 * @Version 1.0
 **/

@Component
public class CashHistoryServiceImpl implements CashHistoryService {


    Log log = LogFactory.getLog(CashHistoryServiceImpl.class);

    @Resource
    CashHistoryMapper cashHistoryMapper;

    @Override
    public List getAllCashHistory(CashHistory cashHistory) {
        CashHistoryExample cashHistoryExample = new CashHistoryExample();
        CashHistoryExample.Criteria criteria = cashHistoryExample.createCriteria();
        if (cashHistory.getId() != null){
            criteria.andIdEqualTo(cashHistory.getId());
        }
        if (StringUtils.isNotEmpty(cashHistory.getUserId())){
            criteria.andUserIdEqualTo(cashHistory.getUserId());
        }
        if (cashHistory.getMoney() != null){
            criteria.andMoneyEqualTo(cashHistory.getMoney());
        }
        if (StringUtils.isNotEmpty(cashHistory.getAccNum())){
            criteria.andAccNumEqualTo(cashHistory.getAccNum());
        }
        if (StringUtils.isNotEmpty(cashHistory.getTime())){
            criteria.andTimeEqualTo(cashHistory.getTime());
        }
        if (cashHistory.getState() != null){
            criteria.andstateEqualTo(cashHistory.getState());
        }
        return cashHistoryMapper.selectByExample(cashHistoryExample);
    }

    @Override
    public void updateCashHistory(CashHistory cashHistory) {
        cashHistoryMapper.updateByPrimaryKeySelective(cashHistory);
    }
}
