package com.devtest.devtest.service;

import com.devtest.devtest.model.User;

public interface LoginService {
    User getUser(String email); //select

    int insertUser(User user);

    User getNickname(String nickname);

}
