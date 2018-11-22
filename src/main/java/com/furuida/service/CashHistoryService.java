package com.furuida.service;

import com.furuida.model.CashHistory;
import com.furuida.model.User;

import java.util.List;

public interface CashHistoryService {


    public List getAllCashHistory(CashHistory cashHistory);
    public List queryAndUser(CashHistory cashHistory);
    public void updateCashHistory(CashHistory cashHistory);
}
