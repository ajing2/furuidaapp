package com.furuida.service;

import com.furuida.mapper.OrderMapper;
import com.furuida.mapper.UserMapper;
import com.furuida.model.*;
import com.furuida.utils.ExecCommand;
import com.furuida.utils.WeChatAccessToken;
import com.furuida.utils.WeChatUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.omg.SendingContext.RunTime;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
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
    Log log = LogFactory.getLog(UserServiceImpl.class);
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

//    @Transactional
//    @Override
//    public void testTrans() {
//        try {
//            Order order = new Order();
//            order.setIsPay(true);
//            order.setPayPrice(100);
////            order.setPayTime((int)new Date().getTime());
//            order.setUserId("111111111111111");
//            order.setCreateTime(1222323);
//            order.setIsReceipt(true);
//            order.setIsShip(true);
//            order.setOrderNum("sa");
//            order.setReceiptTime(232432);
//            order.setShipNum("sas");
//            order.setShipTime(35424);
//            order.setUpdateTime(888888888);
//            order.setPayTime(1);
//            orderMapper.insertSelective(order);
//            order.setUserId("test666666666666");
////            int a = 1/0;
//            orderMapper.insertSelective(order);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void deleteUser(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }
    @Override
    public void deleteUserByUserId(String userId) {
        userMapper.deleteUserByUserId(userId);
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
    public Map<String, String> getToken(HttpSession session, String code) {
        Map<String, String> map = new HashMap<>();
        WeChatAccessToken token = WeChatUtils.getAccessToken(code);
        log.info("token:" + token.getAccess_token());
        if (null != token && !token.equals("")) {
            session.setAttribute("access_token", token.getAccess_token());
            session.setAttribute("openid", token.getOpenid());
            log.info("sessionId:" + session.getId());
            map.put("token", token.getAccess_token());
            map.put("openid", token.getOpenid());
        }
        return map;
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
    public UserInfoReturn getUserInfo(HttpSession session, String code, String parentId) {
//        String token = getSessionToken(session);
//        String openid = getSessionOpenId(session);
        UserInfoReturn userInfoReturn = new UserInfoReturn();
        String token = null;
        String openid = null;
        UserInfo userInfo = null;
        try {
            if (null == token || "".equals(token)) {
                Map<String, String> map = getToken(session, code);
                token = map.get("token");
                openid = map.get("openid");
            }
            if (null == openid || "".equals(openid)) {
                Map<String, String> map = getToken(session, code);
                token = map.get("token");
                openid = map.get("openid");
            }
            userInfo = WeChatUtils.getWXUserInfoUrl(openid, token);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Map<String, String> map = getToken(session, code);
            token = map.get("token");
            openid = map.get("openid");
            userInfo = WeChatUtils.getWXUserInfoUrl(openid, token);
        }
        if (null != userInfo){
            try {
                BeanUtils.copyProperties(userInfoReturn, userInfo);
            } catch (IllegalAccessException e1) {

            } catch (InvocationTargetException e2) {

            }
            userInfoReturn.setOpenid(userInfo.getOpenid());
            User us = new User();
            us.setWebchat(userInfo.getOpenid());
            String userId = String.valueOf(userInfo.getOpenid().hashCode()).replace("-", "");
            userInfoReturn.setUserid(userId);
            List<User> u = selectUser(us);
            if (null == u || u.size()==0){
                User user = new User();
                user.setIspayed(0);
                user.setLevel(-1);
                user.setParentId(parentId);
                user.setPhone("");
                user.setReceiveName("");
                user.setReceiveAddr("");
                user.setUserId(userId);
                user.setWebchatName(userInfo.getNickname());
                user.setWebchatUrl(userInfo.getHeadimgurl());
                user.setWebchat(userInfo.getOpenid());
                userMapper.insertSelective(user);
                userInfoReturn.setParentId(parentId);
            }else{
//                log.error(u.get(0).toString());
//                log.error(parentId);
                if (u.get(0).getIspayed() != 1 || u.get(0).getLevel() == -1) {
                    String oldParentId = u.get(0).getParentId();
                    if (!oldParentId.equals(parentId)) {
                        User uu = new User();
                        uu.setIspayed(0);
                        uu.setLevel(-1);
                        uu.setParentId(parentId);
                        uu.setUserId(userId);
                        userMapper.updateByPrimaryKeySelective(uu);
//                        userInfoReturn.setParentId(parentId);
                    }
                    userInfoReturn.setParentId(parentId);
                    userInfoReturn.setIspayed(0);
                }else{
                    userInfoReturn.setIspayed(1);
                    userInfoReturn.setParentId(u.get(0).getParentId());
                }
            }
        }
        return userInfoReturn;
    }

    @Override
    public User selectByUserId(String userId) {
        return userMapper.selectByUserId(userId);
    }

    @Override
    public User selectByopenId(String openId) {
        return userMapper.selectByOpenId(openId);
    }

    private String getSessionOpenId(HttpSession session) {
        //先取session中的token
        String old_openid = (String) session.getAttribute("openid");
        if (!org.apache.commons.lang.StringUtils.isEmpty(old_openid)) {
            return old_openid;
        }
        log.info("token:" + old_openid);
        return null;
    }


    @Override
    public void updateUser(User user) {
//        if (user!=null) { //防止level被覆盖
//            User u = userMapper.selectByUserId(user.getUserId());
//            if (null!=u) {
//                user.setLevel(u.getLevel());
                userMapper.updateByPrimaryKeySelective(user);
//            }
//        }
    }


    @Override
    public void updateDefaultUser(User user) {
        if (user != null) {
            User u = userMapper.selectByUserId(user.getUserId());
            if (null!=u) {
                user.setLevel(u.getLevel());
                user.setIspayed(u.getIspayed());
                userMapper.updateByPrimaryKeySelective(user);
                log.debug("更新用户的其他信息, 级别和是否付款级别不做修改, userId=" + user.getUserId());
            }else {
                log.error("没有查到用户");
            }
        }else {
            log.error("传入的用户为空");
        }

    }



    @Override
    public Boolean getChildrenInfo(String parentId) {
        try {
            List<User> allChildren = userMapper.getChildrenInfo(parentId);
            if (allChildren.size() < 3){
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }
    @Override
    public ResultBean getQr(String userId){
        try {
            ExecCommand run = new ExecCommand();
            String cmd = "/usr/local/tomcat8/postMaker.sh " + userId;
//            String cmd = "python /Users/lingjing/IdeaProjects/furuidaapp/src/main/webapp/static/images/qr/postMaker.py " + userId;
            run.runLocal(cmd);
            return ResultBean.success("/static/images/qr/images/" + userId + ".jpg");
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultBean.success("/static/images/qr/images/error.jpg");
        }
    }



}
