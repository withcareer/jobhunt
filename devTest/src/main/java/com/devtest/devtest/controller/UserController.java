package com.devtest.devtest.controller;

import com.devtest.devtest.model.HUNTUSER_BOOKMARK;
import com.devtest.devtest.model.User;
import com.devtest.devtest.model.User_HUNTUSER_BOOKMARK;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/jobhunt/v1/Users")
@RequiredArgsConstructor
public class UserController {

    @Autowired

    private LoginService loginService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    RedisRepository redisRepository;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    @ApiOperation(value="메인화면", notes ="로그인성공시 들어오는 홈 화면 JWT 토큰생성")
    @CrossOrigin(origins = "http://localhost:3000")
    public List home(HttpServletRequest req) throws ExpiredJwtException {
        String expireCheck = null;
        String authorization = req.getHeader("Authorization");
        String refreshToken = req.getHeader("refreshTokenId");


        System.out.println("로그인 된 authorization : " + authorization);
        System.out.println("로그인 된 refreshToken : " + refreshToken);

        if(authorization.equals("null")) {
            System.out.println("noLogin");
            return Collections.singletonList("noLogin");
        }else {
            try{
                String payload = securityService.getSubject(authorization);

                JSONObject json = new JSONObject(payload.replaceAll("=", ":"));

                int exp = json.getInt("exp");
                String email = json.getString("Email");
                System.out.println("user : " + email);
                System.out.println("인증토큰 유효시간 : " + exp);
                List bookmarkList = loginService.BookMark_Check(email); // 즐겨찾기 리스트

                expireCheck = securityService.jwtExpireCheck(exp);
//                System.out.println("home" + expireCheck);

                return bookmarkList;
            }catch (JwtException e){
                expireCheck = "false";

                e.printStackTrace();
            }
        }
        return Collections.singletonList(expireCheck);
    }


    //로긴
    @RequestMapping(value = "/login")
    @ApiOperation(value="로그인", notes ="User의 아이디와 비밀번호를 입력해야함")
    @CrossOrigin(origins = "http://localhost:3000")
    public String login2(@RequestBody final User params) {


        User loginUser = loginService.getUser(params.getEmail());
        Map<String, Object> token_map = new HashMap<>();
        Map<String, Object> refresh_map = new HashMap<>();
        int time = (int) ((new Date().getTime() + 60 * 60 * 1000) / 1000); //시분초가 아닌 ms 값으로 변환시키는 과정

        token_map.put("Email", params.getEmail());
        token_map.put("exp", time + 60);
        refresh_map.put("exp", time + 60 * 60 * 24);


        System.out.println("로그인된 시간 : " + time);

        String token = securityService.createToken(token_map.toString(), 1000 * 60); //access token값 유효시간
        String refresh_token = securityService.createToken(refresh_map.toString(), 1000 * 60 * 60 * 24); // 1000 * 60 * 60*24 refresh토큰 유효시간

        if (loginUser == null) {
            return null;
        }
        //로그인 성공시
        else if (params.getPass().equals(loginUser.getPass())) {
            System.out.println("발급한 인증 token : " + token);
            System.out.println("발급한 인증 refresh token : " + refresh_token);

            // redis를 이용하여 캐시에 이메일에 해당하는 refresh 토큰값을 넣어줌
            User_RefreshToken user = new User_RefreshToken();
            user.setEmail(params.getEmail().toString());
            user.setRefresh_token(refresh_token.toString());
            redisRepository.save(user).toString();

//            System.out.println(redisRepository.findAll().toString());
            return token + "/" + refresh_token;
        } else {
            return null;
        }
    }


    //아이디 중복찾기
    @RequestMapping(value = "/idCheck", method = RequestMethod.POST)//ID중복체크
    @ApiOperation(value="아이디 중복체크", notes ="params값으로 아이디 입력을 해줘야함")
    @CrossOrigin(origins = "http://localhost:3000")
    public int idCheck(@RequestBody final User params) {

        String userId = params.getEmail();

        User joinUser = this.loginService.getUser(userId);

//        System.out.println(loginUser.getNickname());

        if (joinUser != null) { //읽어온 유저 정보가 있으면 패일

            return 0;
        } else {

            return 1;
        }
    }

    //회원가입
    @RequestMapping(value = "/join", method = RequestMethod.POST)//회원가입
    @ApiOperation(value="회원가입", notes ="{id},{passwd},{nickname},{birth}.{sex},{phone} 을 넘겨야함")
    @CrossOrigin(origins = "http://localhost:3000")
    public int join(@RequestBody final User params) {


        if (this.loginService.insertUser(params) != 0) {
            return 1;
        } else {
            return 2;
        }
    }

    // 마이페이지 정보출력
    @RequestMapping(value = "/mypage", method = RequestMethod.GET)
    @ApiOperation(value="즐겨찾기", notes ="Token 값을 authorization에 담아서 넘겨줘야함")
    @CrossOrigin(origins = "http://localhost:3000")
    public List getAuthInfo(HttpServletRequest req) throws ExpiredJwtException {

        String expireCheck = null;
        try {
            String authorization = req.getHeader("Authorization");
            String payload = securityService.getSubject(authorization);

            JSONObject json = new JSONObject(payload.replaceAll("=", ":"));

            String email = json.getString("Email");

            User user = loginService.getUser(email);

            List<User> userList = loginService.getUserList(email);

            List<HUNTUSER_BOOKMARK> huntuser_bookmark = loginService.get_HUNTUSER_BOOKMARK(user.getUno());

            List mine = new ArrayList<>();

            mine.addAll(userList);
            mine.addAll(huntuser_bookmark);

//            System.out.println(mine);

            return mine;
        }catch (JwtException e){
            System.out.println("마이페이지 토큰 만료");
            expireCheck = "false";

            e.printStackTrace();
        }

//      User_HUNTUSER_BOOKMARK user_huntuser_bookmark = new User_HUNTUSER_BOOKMARK(); //빈 dto
        return Collections.singletonList(expireCheck);


    }


    // 즐겨찾기에 회사정보저장
    @RequestMapping(value = "/company-save", method = RequestMethod.POST)
    @ApiOperation(value="즐겨찾기", notes ="Token 값을 authorization에 담아서 넘겨줘야함")
    @CrossOrigin(origins = "http://localhost:3000")
    public int company_save(@RequestBody final HUNTUSER_BOOKMARK params, HttpServletRequest req) {


        // 토큰값으로 해당하는 유저의 정보를 가져오는 코드
        String authorization = req.getHeader("Authorization");
//        System.out.println(authorization);
        String payload = securityService.getSubject(authorization);

        JSONObject json = new JSONObject(payload.replaceAll("=", ":"));

        String email = json.getString("Email");

        User user = loginService.getUser(email);

        params.setUno(user.getUno());

//        System.out.println(params.getCompanyname());
//        System.out.println(params.getCompany_start());
//        System.out.println(params.getCompany_end());
//        System.out.println(params.getCompanyimg());
//        System.out.println(params.getUno());


        if (this.loginService.get_HUNTUSER_BOOKMARK_Check(params.getCompanyname(), user.getUno()) == null) {
            if (this.loginService.Insert_User_BookMark(params) != 0) {
                return 1; // 즐겨찾기에 성공
            } else {
                return 3; // 로그인 후 이용해주세요
            }

        } else {
            return 2;  //이미 즐겨찾기가 되어있습니다.
        }

    }

    // 즐겨찾기에 회사정보삭제
    @RequestMapping(value = "/company-delete", method = RequestMethod.POST)
    @ApiOperation(value="즐겨찾기 삭제", notes ="Token 값을 authorization에 담아서 넘겨줘야함")
    @CrossOrigin(origins = "http://localhost:3000")
    public int company_delete(@RequestBody final HUNTUSER_BOOKMARK params, HttpServletRequest req) {

        // 토큰값으로 해당하는 유저의 정보를 가져오는 코드
        String authorization = req.getHeader("Authorization");
//        System.out.println(authorization);
        String payload = securityService.getSubject(authorization);

        JSONObject json = new JSONObject(payload.replaceAll("=", ":"));

        String email = json.getString("Email");

        User user = loginService.getUser(email);


        if (this.loginService.delete_User_BookMark(params.getCompanyname(), user.getUno()) != 0) {
            return 1;
        } else {
            return 2;
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ApiOperation(value="로그아웃", notes ="refresh를 제외한 모든 토큰 삭제")
    @CrossOrigin(origins = "http://localhost:3000")
    public int logout(HttpServletRequest req) {
        String refreshTokenId = req.getHeader("refreshTokenId");
        redisRepository.deleteById(refreshTokenId); //refresh token값 redis에서 삭제
        System.out.println("로그아웃");
        return 1;
    }


}