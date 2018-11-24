package com.furuida.mapper;

import com.furuida.model.User;
import com.furuida.model.UserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Long id);

    int deleteUserByUserId(@Param("userId") String userId);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    List<User> getChildrenInfo(@Param("parentId") String parentId);

    List<User> selectAll();
    List<User> selectNode();
    User selectByUserId(String userId);
    User selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateUserNoLevel(User record);

    int updateByPrimaryKey(User record);

    User selectByOpenId(String openId);
}