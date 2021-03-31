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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.warestore.model.*;
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

    @Autowired
    private ResponseController responseController;

    @PostMapping(value = "/authentication")
    public String authorizeUser(@ModelAttribute @Valid UserAuthentication user, HttpServletResponse httpResponse, Model model){
        try {
            ResponseEntity<?> response = requestService.getOrPostData(
                    environment.getProperty("url.auth"),
                    null,
                    HttpMethod.POST,
                    new HttpEntity<>(user)
            );
            tokenService.addCookieToken(httpResponse, tokenService.tokenMapper(response));
            return "redirect:/";
        }
        catch (ResourceAccessException ignored){
            return responseController.response(model,
                    new ResponseHTML("Warestore - Техническое обслуживание", "Ресурс временно недоступен :(\nСервер на обслуживании."));
        }
        catch (HttpClientErrorException.NotFound ignored){
            return responseController.response(model,
                    new ResponseHTML("Warestore - Вход", "Пользователь с таким логином не найден."));
        }
        catch (HttpClientErrorException.Forbidden ignored){
            return responseController.response(model,
                    new ResponseHTML("Warestore - Вход", "Не верный пароль."));
        }

    }

    @GetMapping(value = "/authentication")
    public String getAuthorizationUserPage(Model model){
        model.addAttribute("userAuthentication", new UserAuthentication());
        return "authentication";
    }


    @PostMapping(value = "/registration")
    public String registerUser(@ModelAttribute @Valid User user, HttpServletResponse httpResponse, Model model){
        try {
            ResponseEntity<?> response = requestService.getOrPostData(
                    environment.getProperty("url.register"),
                    null,
                    HttpMethod.POST,
                    new HttpEntity<>(user)
            );
            Token token = tokenService.tokenMapper(response);
            tokenService.addCookieToken(httpResponse, token);
            return "redirect:/";
        }
        catch (ResourceAccessException ignored){
            return responseController.response(model,
                    new ResponseHTML("Warestore - Техническое обслуживание", "Ресурс временно недоступен :(\nСервер на обслуживании."));
        }
        catch (HttpClientErrorException.NotAcceptable ignored){
            return responseController.response(model,
                    new ResponseHTML("Warestore - Регистрация", "Зарегистрировать нового пользователя не удалось, попробуйте позже."));
        }
    }

    @GetMapping(value = "/registration")
    public String getRegistrationUserPage(Model model){
        model.addAttribute("userRegistration", new User());
        return "registration";
    }

    @GetMapping(value = "/profile")
    public String getProfile(Model model, @CookieValue(name = "WarestoreToken") Cookie token, String page){
        try {
            ResponseEntity<?> responseUser = requestService.
                    getOrPostData(environment.getProperty("url.user"), token,
                            HttpMethod.GET, null
                    );
            User user = new ObjectMapper().convertValue(responseUser.getBody(), User.class);

            ResponseEntity<?> responseOrders = requestService.
                    getOrPostData(requestService.getURL(
                            environment.getProperty("url.user.orders"), page), token,
                            HttpMethod.GET, null
                    );
            List<Order> orderList = new ArrayList<>();
            if (responseUser.getStatusCode() == HttpStatus.OK)
                orderList = (List<Order>) responseOrders.getBody();
            model.addAttribute("orders", orderList);
            model.addAttribute("userCredentials", user);
            return "profile";
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
    @PostMapping("/getOrderPageButtonsUser")
    public String nextRiflesPage(@RequestParam("currentPage") String page, Model model, @CookieValue(value = "WarestoreToken", required = true) Cookie token){
        return getProfile(model, token, page);
    }

}
