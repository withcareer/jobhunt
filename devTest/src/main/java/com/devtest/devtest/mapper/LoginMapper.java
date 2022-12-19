package com.devtest.devtest.mapper;

import com.devtest.devtest.model.HUNTUSER_BOOKMARK;
import com.devtest.devtest.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoginMapper {
    User getUser(String email); //select

    List<User> getUserList(String email);

    User getNickname(String nickname);

    int insertUser(User user);

    int Insert_User_BookMark(HUNTUSER_BOOKMARK bookmark);

    List<HUNTUSER_BOOKMARK> get_HUNTUSER_BOOKMARK(int uno);
}