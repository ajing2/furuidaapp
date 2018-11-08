package com.furuida.service;

import com.furuida.mapper.OrderMapper;
import com.furuida.mapper.UserMapper;
import com.furuida.model.Order;
import com.furuida.model.User;
import com.furuida.model.UserExample;
import com.furuida.model.UserInfo;
import com.furuida.utils.HttpClientUtils;
import com.furuida.utils.WeChatAccessToken;
import com.furuida.utils.WeChatUtils;
import com.jd.jsf.gd.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Resource
    OrderMapper orderMapper;
    @Transactional
    @Override
    public void addUser(User user) {
        try {
            userMapper.insertSelective(user);
            nodeService.initALLNode();
//            nodeService.payAndUpgrade(user.getParentId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void testTrans() {
        try {
            Order order = new Order();
            order.setIsPay(true);
            order.setPayPrice(100);
//            order.setPayTime((int)new Date().getTime());
            order.setUserId("111111111111111");
            order.setCreateTime(1222323);
            order.setIsReceipt(true);
            order.setIsShip(true);
            order.setOrderNum("sa");
            order.setReceiptTime(232432);
            order.setShipNum("sas");
            order.setShipTime(35424);
            order.setUpdateTime(888888888);
            orderMapper.insertSelective(order);
            order.setUserId("test666666666666");
            int a = 1/0;
            orderMapper.insertSelective(order);
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
    public String getToken(HttpSession session, String code) {
        WeChatAccessToken token = WeChatUtils.getAccessToken(code);
        log.info("token:" + token.getAccess_token());
        if (null != token && !token.equals("")) {
            session.setAttribute("access_token", token.getAccess_token());
            session.setAttribute("openid", token.getOpenid());
            log.info("sessionId:" + session.getId());
        }
        return token.getAccess_token();
    }
    public String getSessionToken(HttpSession session) {
        //先取session中的token
        String old_token = (String) session.getAttribute("access_token");
        if (!org.apache.commons.lang.StringUtils.isEmpty(old_token)) {
            return old_token;
        }
        log.info("token:" + old_token);
        return null;
    }
    @Override
    public UserInfo getUserInfo(HttpSession session, String code, String parentId) {
        String token = getSessionToken(session);
        UserInfo userInfo = null;
        try {
            if (null == token || "".equals(token)) {
                token = getToken(session, code);
            }
            userInfo = WeChatUtils.getWXUserInfoUrl((String) session.getAttribute("openid"), token);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            token = getToken(session, code);
            userInfo = WeChatUtils.getWXUserInfoUrl((String) session.getAttribute("openid"), token);
        }
        if (null != userInfo){
            User user = new User();
            user.setIspayed(0);
            user.setLevel(-1);
            user.setParentId(parentId);
            user.setPhone("");
            user.setWebchatName(userInfo.getOpenid());
            user.setReceiveAddr("");
            user.setUserId(String.valueOf(userInfo.getOpenid().hashCode()).replace("-", ""));
            user.setWebchatName(userInfo.getNickname());
            user.setWebchatUrl(userInfo.getHeadimgurl());
            user.setWebchat(userInfo.getOpenid());
            userMapper.insertSelective(user);
//            user.setUserId(String.valueOf(userInfo.getOpenid().hashCode()).replace("-", ""));
        }
        return userInfo;
    }


    @Override
    public void updateUser(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }
}
