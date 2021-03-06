package com.furuida.service;

import com.furuida.model.ResultBean;
import com.furuida.model.User;
import com.furuida.model.UserInfo;
import com.furuida.model.UserInfoReturn;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface UserService {
    public void addUser(User user);

//    public void testTrans();
    public void updateUser(User user);
    public void updateDefaultUser(User user);

    public void deleteUser(Long id);

    public void deleteUserByUserId(String userId);

    public List selectUser(User user);

    Map<String, String> getToken(HttpSession session, String code);
    UserInfoReturn getUserInfo(HttpSession session, String code, String parentId);
    public User selectByUserId(String userId);
    public User selectByopenId(String openId);
    public Boolean getChildrenInfo(String parentId);


    public ResultBean getQr(String userId);
}
