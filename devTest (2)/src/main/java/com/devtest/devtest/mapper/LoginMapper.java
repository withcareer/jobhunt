package com.devtest.devtest.mapper;

import com.devtest.devtest.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    User getUser(String email); //select

    User getNickname(String nickname);

    int insertUser(User user);
}
