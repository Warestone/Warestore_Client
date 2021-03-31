package org.warestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.warestore.model.Order;
import org.warestore.model.ResponseHTML;
import org.warestore.service.RequestService;
import javax.servlet.http.Cookie;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    Environment environment;

    @Autowired
    private RequestService requestService;

    @Autowired
    private ResponseController responseController;

    @GetMapping("/administration")
    public String getAllOrders(Model model, @CookieValue(name = "WarestoreToken", required = true) Cookie token, String page){
        try{
            ResponseEntity<?> response = requestService.
                    getOrPostData(requestService.
                                    getURL(environment.getProperty("url.admin.orders"), page), token,
                            HttpMethod.GET,null
                    );
            List<Order> orders = (List<Order>) response.getBody();
            model.addAttribute("orders", orders);
            return "administration";
        }
        catch (HttpClientErrorException.Forbidden ignored){
            return responseController.response(model,
                    new ResponseHTML("Warestore - Доступ ограничен", "Пожалуйста, авторизуйтесь для доступа в защищённую область."));
        }
        catch (ResourceAccessException ignored){
            return responseController.response(model,
                    new ResponseHTML("Warestore - Техническое обслуживание", "Ресурс временно недоступен :(\nСервер на обслуживании."));
        }
    }
    @PostMapping("/getOrderPageButtonsAdmin")
    public String nextRiflesPage(@RequestParam("currentPage") String page, Model model, @CookieValue(value = "WarestoreToken", required = true) Cookie token){
        return getAllOrders(model, token, page);
    }
}
