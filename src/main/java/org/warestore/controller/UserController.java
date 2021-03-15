package org.warestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.warestore.model.User;
import org.warestore.model.UserAuthentication;
import org.warestore.service.RequestService;
import org.warestore.service.TokenService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    Environment environment;

    @Autowired
    private RequestService requestService;

    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "/authentication")
    public String authorizeUser(@ModelAttribute UserAuthentication user, HttpServletResponse response){
        List<String> token = (List<String>) requestService.getOrPostData(
                environment.getProperty("url.auth"),
                null,
                HttpMethod.POST,
                new HttpEntity<>(user)
        );
        tokenService.addCookieToken(response,token);
        // something do when no token == incorrect credentials
        return "redirect:/";
    }

    @GetMapping(value = "/authentication")
    public String getAuthorizationUserPage(Model model){
        model.addAttribute("userAuthentication", new UserAuthentication());
        return "authentication";
    }


    @PostMapping(value = "/registration")
    public String registerUser(@ModelAttribute User user, HttpServletResponse response){
        List<String> token = (List<String>) requestService.getOrPostData(
                environment.getProperty("url.auth"),
                null,
                HttpMethod.POST,
                new HttpEntity<>(user)
        );
        tokenService.addCookieToken(response,token);
        // something do when no token == incorrect credentials
        return "redirect:/";
    }

    @GetMapping(value = "/registration")
    public String getRegistrationUserPage(Model model){
        model.addAttribute("userRegistration", new User());
        return "registration";
    }
}
