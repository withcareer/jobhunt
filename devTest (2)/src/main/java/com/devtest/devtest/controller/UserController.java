package com.devtest.devtest.controller;

import com.devtest.devtest.model.User;
import com.devtest.devtest.service.LoginService;
import com.devtest.devtest.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired

    private LoginService loginService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/login")
    @CrossOrigin(origins = "http://localhost:3000")
    public String login2(@RequestBody final User params) {


        User loginUser = loginService.getUser(params.getEmail());

        String token=securityService.createToken(params.getEmail(), 1000*60*60);
        Map<String,Object> map =new HashMap<>();
        map.put("Email", params.getEmail());
        map.put("token", token);

        if(loginUser == null){
            return null;
        }
        //로그인 성공시
        else if(params.getPass().equals(loginUser.getPass())){
            return token;
        }

        else {
            return null;
        }
    }

    @RequestMapping(value = "/idCheck", method = RequestMethod.POST)//ID중복체크
    @CrossOrigin(origins = "http://localhost:3000")
    public int idCheck(@RequestBody final User params){

        String userId = params.getEmail();

        User joinUser = this.loginService.getUser(userId);

//        System.out.println(loginUser.getNickname());

        if(joinUser != null){ //읽어온 유저 정보가 있으면 패일

            return 0;
        }
        else {

            return 1;
        }
    }

    @RequestMapping(value = "/nickname", method = RequestMethod.POST)//ID중복체크
    @CrossOrigin(origins = "http://localhost:3000")
    public int nickCheck(@RequestBody final User params){

        String userNickname = params.getNickname();

//        System.out.println(userNickname);

        User joinUser = this.loginService.getNickname(userNickname);

        if(joinUser != null){ //읽어온 유저 정보가 있으면 패일

            return 0;
        }
        else {

            return 1;
        }
    }

    @RequestMapping(value = "/passCheck", method = RequestMethod.POST)//PW중복체크
    @CrossOrigin(origins = "http://localhost:3000")
    public int passCheck(@RequestBody final User params){

        if(!params.getPass().equals(params.getPass2())){ //읽어온 유저 정보가 있으면 패일

            return 0;
        }
        else {

            return 1;
        }
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)//회원가입
    @CrossOrigin(origins = "http://localhost:3000")
    public int join(@RequestBody final User params){

        System.out.println(params.getEmail());
        System.out.println(params.getPass());
        System.out.println(params.getPass2());
        System.out.println(params.getNickname());
        System.out.println(params.getSex());
        System.out.println(params.getBirth());
        System.out.println(params.getPhone());

        if(this.loginService.insertUser(params) != 0){
            return 1;
        }

        else {
            return 2;
        }
    }



    @RequestMapping(value = "/mypage", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:3000")
    public User getAuthInfo(HttpServletRequest req) {
        String authorization = req.getHeader("Authorization");
        String email=securityService.getSubject(authorization);

        System.out.println(email);
        System.out.println(authorization);

        User user=loginService.getUser(email);
        System.out.println(user.getPass());
        return user;
    }



}