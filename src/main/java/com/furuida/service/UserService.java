package com.furuida.service;

import com.furuida.model.Order;
import com.furuida.model.User;
import com.furuida.model.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    public void addUser(User user);

    public void testTrans();
    public void updateUser(User user);

    public void deleteUser(Long id);

    public List selectUser(User user);

    String getToken(HttpSession session, String code);
    UserInfo getUserInfo(HttpSession session, String code);
}
