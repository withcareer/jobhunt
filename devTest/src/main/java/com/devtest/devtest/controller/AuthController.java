package com.devtest.devtest.controller;

import com.devtest.devtest.model.User_RefreshToken;
import com.devtest.devtest.repository.RedisRepository;
import com.devtest.devtest.service.LoginService;
import com.devtest.devtest.service.SecurityService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/jobhunt/v1/Auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    RedisRepository redisRepository;

    @Autowired
    private LoginService loginService;

    //access 접근토큰이 만료되었을시 refresh 토큰으로 다시 access토큰을 발급해주기위한 api
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    @ApiOperation(value="refresh_token", notes ="Token 값을 authorization에 담아서 넘겨줘야함 access 접근토큰이 만료되었을시 refresh 토큰으로 다시 access토큰을 발급해주기위한 api")
    @CrossOrigin(origins = "http://localhost:3000")
    public String refresh(HttpServletRequest req) throws ExpiredJwtException {

        System.out.println("리프래쉬 토큰으로 access 토큰 발급요청");
        String expireCheck = null;
        String refreshTokenId = req.getHeader("refreshTokenId");

        try{

            User_RefreshToken user_refreshToken = redisRepository.findById(refreshTokenId).get(); //redis에서 refresh토큰값에 해당하는 이메일과 refresh토큰을 출력

            String refreshPayload = securityService.getSubject(refreshTokenId);

            JSONObject refreshJson = new JSONObject(refreshPayload.replaceAll("=", ":"));


            int exp = refreshJson.getInt("exp");
            System.out.println("리프레쉬토큰 유효시간 : " + exp);

            expireCheck = securityService.jwtExpireCheck(exp);

//            System.out.println("test :::" + expireCheck);

            if (expireCheck == "true"){ //리프래쉬토큰 만료기간이 남아있을 시
                Map<String, Object> token_map = new HashMap<>();
                int time = (int) ((new Date().getTime() + 60 * 60 * 1000) / 1000);
                token_map.put("Email", user_refreshToken.getEmail());
                token_map.put("exp", time + 60);
                String reToken = securityService.createToken(token_map.toString(), 1000 * 60); //토큰 재발행 및 return
                System.out.println("재발급 완료!!");
                return reToken;
            }

        }catch (JwtException e){
            expireCheck = "false";
            redisRepository.deleteById(refreshTokenId); //refresh token값 redis에서 삭제
            System.out.println("refresh토큰 만료됨");
            e.printStackTrace();
        }

        return expireCheck;
    }

}