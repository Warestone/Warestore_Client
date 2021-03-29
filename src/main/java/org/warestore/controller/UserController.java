package org.warestore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.warestore.model.Order;
import org.warestore.model.Token;
import org.warestore.model.User;
import org.warestore.model.UserAuthentication;
import org.warestore.service.RequestService;
import org.warestore.service.TokenService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
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
    public String authorizeUser(@ModelAttribute @Valid UserAuthentication user, HttpServletResponse httpResponse){
        ResponseEntity<?> response = requestService.getOrPostData(
                environment.getProperty("url.auth"),
                null,
                HttpMethod.POST,
                new HttpEntity<>(user)
        );
        if (response.getStatusCode()!= HttpStatus.FORBIDDEN){
            // do when wrong password
        }
        if (response.getStatusCode()!= HttpStatus.NOT_FOUND){
            // do when wrong username
        }
        if (response.getStatusCode()==HttpStatus.OK)
            tokenService.addCookieToken(httpResponse,tokenService.tokenMapper(response));
        return "redirect:/";
    }

    @GetMapping(value = "/authentication")
    public String getAuthorizationUserPage(Model model){
        model.addAttribute("userAuthentication", new UserAuthentication());
        return "authentication";
    }


    @PostMapping(value = "/registration")
    public String registerUser(@ModelAttribute @Valid User user, HttpServletResponse httpResponse){
        ResponseEntity<?> response = requestService.getOrPostData(
                environment.getProperty("url.register"),
                null,
                HttpMethod.POST,
                new HttpEntity<>(user)
        );
        if (response.getStatusCode()!=HttpStatus.OK){
            // do when dont register user
        }
        Token token = tokenService.tokenMapper(response);
        tokenService.addCookieToken(httpResponse,token);
        return "redirect:/";
    }

    @GetMapping(value = "/registration")
    public String getRegistrationUserPage(Model model){
        model.addAttribute("userRegistration", new User());
        return "registration";
    }

    @GetMapping(value = "/profile")
    public String getProfile(Model model, @CookieValue(name = "WarestoreToken") Cookie token, String page){
        ResponseEntity<?> responseUser = requestService.
                getOrPostData(environment.getProperty("url.user"), token,
                        HttpMethod.GET,null
                );
        User user = new ObjectMapper().convertValue(responseUser.getBody(),User.class);

        ResponseEntity<?> responseOrders = requestService.
                getOrPostData(requestService.getURL(
                        environment.getProperty("url.user.orders"), page), token,
                        HttpMethod.GET,null
                );
        List<Order> orderList = new ArrayList<>();
        if (responseUser.getStatusCode()==HttpStatus.OK)
            orderList = (List<Order>) responseOrders.getBody();
        model.addAttribute("orders",orderList);
        model.addAttribute("userCredentials", user);
        return "profile";
    }
    @PostMapping("/getOrderPageButtonsUser")
    public String nextRiflesPage(@RequestParam("currentPage") String page, Model model, @CookieValue(value = "WarestoreToken", required = true) Cookie token){
        return getProfile(model, token, page);
    }

}
