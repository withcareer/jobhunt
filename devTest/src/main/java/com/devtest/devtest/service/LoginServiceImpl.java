package com.devtest.devtest.service;

import com.devtest.devtest.mapper.LoginMapper;
import com.devtest.devtest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public User getUser(String email) {
        return loginMapper.getUser(email);
    }

    @Override
    public int insertUser(User user) {
        return loginMapper.insertUser(user);
    }

    @Override
    public User getNickname(String nickname) {
        return loginMapper.getNickname(nickname);
    }
}
