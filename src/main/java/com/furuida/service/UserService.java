package com.furuida.service;

import com.furuida.model.User;

import java.util.List;

public interface UserService {
    public void addUser(User user);

    public void testTrans();
    public void updateUser(User user);

    public void deleteUser(Long id);

    public List selectUser(User user);

}
