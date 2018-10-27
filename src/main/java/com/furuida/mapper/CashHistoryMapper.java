package com.furuida.mapper;

import com.furuida.model.CashHistory;
import com.furuida.model.CashHistoryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CashHistoryMapper {
    long countByExample(CashHistoryExample example);

    int deleteByExample(CashHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CashHistory record);

    int insertSelective(CashHistory record);

    List<CashHistory> selectByExample(CashHistoryExample example);

    CashHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CashHistory record, @Param("example") CashHistoryExample example);

    int updateByExample(@Param("record") CashHistory record, @Param("example") CashHistoryExample example);

    int updateByPrimaryKeySelective(CashHistory record);

    int updateByPrimaryKey(CashHistory record);
}