package org.warestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.warestore.model.User;
import org.warestore.model.UserAuthentication;
import org.warestore.service.RequestService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    Environment environment;

    @Autowired
    private RequestService requestService;

    @PostMapping(value = "/auth")
    public String authorizeUser(@RequestBody UserAuthentication user, HttpServletResponse response){
        List<String> token = (List<String>) requestService.getOrPostData(
                environment.getProperty("url.auth"),
                null,
                HttpMethod.POST,
                new HttpEntity<UserAuthentication>(user)
        );
        if (token.size()!=0){
            Cookie cookie = new Cookie("WarestoreToken",token.get(0));
            cookie.setMaxAge(24*60*60); //one day
            response.addCookie(cookie);
        }
        return "redirect:/";
    }

    @PostMapping(value = "/register")
    public String registerUser(@RequestBody User user, HttpServletResponse response){
        //user service -> add user
        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setUsername(user.getUsername());
        userAuthentication.setPassword(user.getPassword());
        return authorizeUser(userAuthentication, response);
    }
}
