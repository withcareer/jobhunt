package com.devtest.devtest.controller;

import com.devtest.devtest.service.LoginService;
import com.devtest.devtest.service.SecurityService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class DevController {

    @Autowired
    private SecurityService securityService;
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:3000")
    public List getAuthInfo(HttpServletRequest req) throws ExpiredJwtException {
        String expireCheck = null;
        String authorization = req.getHeader("Authorization");

        System.out.println("authorization : " + authorization);

        if(authorization.equals("null")) {
            System.out.println("noLogin");
            return Collections.singletonList("noLogin");
        }else {
            try{
                String payload = securityService.getSubject(authorization);

                JSONObject json = new JSONObject(payload.replaceAll("=", ":"));
                System.out.println(json);

                int exp = json.getInt("exp");
                String email = json.getString("Email");
                System.out.println(exp);
                System.out.println(email);
                List bookmarkList = loginService.BookMark_Check(email);

                expireCheck = securityService.jwtExpireCheck(exp);
                return bookmarkList;
            }catch (JwtException e){
                expireCheck = "false";

                e.printStackTrace();
            }
        }
        return Collections.singletonList(expireCheck);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:3000")
    public String refresh(HttpServletRequest req) throws ExpiredJwtException {
        String expireCheck = null;
        String refreshTokenId = req.getHeader("refreshTokenId");

        System.out.println("refreshTokenId : " + refreshTokenId);

            try{
                String payload = securityService.getSubject(refreshTokenId);

                JSONObject json = new JSONObject(payload.replaceAll("=", ":"));
//                System.out.println(json);

                int exp = json.getInt("exp");
                String email = json.getString("Email");
                System.out.println(exp);

                expireCheck = securityService.jwtExpireCheck(exp);

                if (expireCheck == "true"){
                    String reToken = securityService.createToken(email, 10000 * 6);
                    return reToken;
                }

            }catch (JwtException e){
                expireCheck = "false";

                e.printStackTrace();
            }

        return expireCheck;
    }

}
