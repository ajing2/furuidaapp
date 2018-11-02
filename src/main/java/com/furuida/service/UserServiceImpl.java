package com.furuida.service;

import com.furuida.mapper.UserMapper;
import com.furuida.model.User;
import com.furuida.model.UserExample;
import com.jd.jsf.gd.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: furuidaapp
 * @description: UserServiceImpl
 * @author: fuzhengquan
 * @create: 2018-10-27 20:16
 **/
@Component
public class UserServiceImpl implements UserService {
    /**
     * log
     */
    Log log = LogFactory.getLog(NodeServiceImpl.class);
    @Resource
    UserMapper userMapper;
    @Resource
    NodeService nodeService;
    @Override
    public void addUser(User user) {
        try {
            userMapper.insertSelective(user);
            nodeService.initALLNode();
            nodeService.payAndUpgrade(user.getParentId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List selectUser(User user) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (user.getId() != null){
            criteria.andIdEqualTo(user.getId());
        }
        if (StringUtils.isNotEmpty(user.getUserId())){
            criteria.andUserIdEqualTo(user.getUserId());
        }
        if (StringUtils.isNotEmpty(user.getWebchat())){
            criteria.andWebchatEqualTo(user.getWebchat());
        }
        if (StringUtils.isNotEmpty(user.getPhone())){
            criteria.andPhoneEqualTo(user.getPhone());
        }

        return userMapper.selectByExample(userExample);
    }


    @Override
    public void updateUser(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }
}
