package org.warestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.warestore.model.Order;
import org.warestore.service.RequestService;

import javax.servlet.http.Cookie;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    Environment environment;

    @Autowired
    private RequestService requestService;

    @GetMapping("/administration")
    public String getAllOrders(Model model, @CookieValue(name = "WarestoreToken", required = true) Cookie token, String page){
        ResponseEntity<?> response = requestService.
                getOrPostData(requestService.
                                getURL(environment.getProperty("url.admin.orders"), page), token,
                        HttpMethod.GET,null
                );
        List<Order> orders = (List<Order>) response.getBody();
        model.addAttribute("orders", orders);
        return "administration";
    }
    @PostMapping("/getOrderPageButtonsAdmin")
    public String nextRiflesPage(@RequestParam("currentPage") String page, Model model, @CookieValue(value = "WarestoreToken", required = true) Cookie token){
        return getAllOrders(model, token, page);
    }
}
