package com.example.community1.controller;

import com.example.community1.Provider.GithubProvider;
import com.example.community1.dto.AccessTokenDTO;
import com.example.community1.dto.GithubUser;
import com.example.community1.mapper.UserMapper;
import com.example.community1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.url}")
    private String redirectUri;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
//        System.out.println(user.getName());
//        System.out.println(user.getId());
//        System.out.println(user.getBio());
        if (githubUser!=null){
            User users = new User();
            String token = UUID.randomUUID().toString();
            users.setToken(token);
            users.setName(githubUser.getName());
            users.setAccountId(String.valueOf(githubUser.getId()));
            users.setGmtCreate(System.currentTimeMillis());
            users.setGmtModified(users.getGmtCreate());
//            System.out.println(users.getName());
//            System.out.println(users.getGmtCreate());
            userMapper.insert(users);
            response.addCookie(new Cookie("token",token));
            request.getSession().setAttribute("user",githubUser);//成功登录
            return "redirect:/";
        }else{
            return "redirect:/";
            //登录失败
        }

    }
}