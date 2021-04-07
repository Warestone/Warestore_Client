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
        catch (ResourceAccessException | HttpClientErrorException.Conflict ignored){
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

    @PostMapping(value = "/editProfile")
    public String editUserProfile(@ModelAttribute @Valid User user, Model model, @CookieValue(name = "WarestoreToken") Cookie token){
        try {
            ResponseEntity<?> response = requestService.getOrPostData(
                    environment.getProperty("url.user.edit"),
                    token,
                    HttpMethod.POST,
                    new HttpEntity<>(user)
            );
            if (response.getStatusCode()==HttpStatus.OK)
                return responseController.response(model,
                        new ResponseHTML("Warestore - Изменение информации", "Ваш профиль был успешно изменён!"));
            else return  responseController.response(model,
                    new ResponseHTML("Warestore - Изменение информации", "Информация в профиле не была изменена.\nПопробуйте позже."));
        }
        catch (ResourceAccessException | HttpClientErrorException.Conflict ignored){
            return responseController.response(model,
                    new ResponseHTML("Warestore - Техническое обслуживание", "Ресурс временно недоступен :(\nСервер на обслуживании."));
        }
        catch (HttpClientErrorException.NotAcceptable | HttpClientErrorException.NotFound ignored){
            return responseController.response(model,
                    new ResponseHTML("Warestore - Изменение информации", "Информация в профиле не была изменена.\nПопробуйте позже."));
        }
        catch (HttpClientErrorException.Gone ignored){
            return responseController.response(model,
                    new ResponseHTML("Warestore - Изменение информации", "Информация в профиле не была изменена.\nДанный телефон уже зарегистрирован в системе!"));
        }
        catch (HttpClientErrorException.MethodNotAllowed ignored){
            return responseController.response(model,
                    new ResponseHTML("Warestore - Изменение информации", "Информация в профиле не была изменена.\nДанный email уже зарегистрирован в системе!"));
        }
        catch (HttpClientErrorException.UnsupportedMediaType ignored){
            return responseController.response(model,
                    new ResponseHTML("Warestore - Изменение информации", "Информация в профиле не была изменена.\nДанный email и телефон уже зарегистрированы в системе!"));
        }
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
        catch (ResourceAccessException | HttpClientErrorException.Conflict ignored){
            return responseController.response(model,
                    new ResponseHTML("Warestore - Техническое обслуживание", "Ресурс временно недоступен :(\nСервер на обслуживании."));
        }
        catch (HttpClientErrorException.NotAcceptable ignored){
            return responseController.response(model,
                    new ResponseHTML("Warestore - Регистрация", "Пользователь с данным логином уже существует!\nПридумайте другой логин."));
        }
        catch (HttpClientErrorException.Gone ignored){
            return responseController.response(model,
                    new ResponseHTML("Warestore - Регистрация", "Зарегистрировать нового пользователя не удалось.\nДанный телефон уже зарегистрирован в системе!"));
        }
        catch (HttpClientErrorException.MethodNotAllowed ignored){
            return responseController.response(model,
                    new ResponseHTML("Warestore - Регистрация", "Зарегистрировать нового пользователя не удалось.\nДанный email уже зарегистрирован в системе!"));
        }
        catch (HttpClientErrorException.UnsupportedMediaType ignored){
            return responseController.response(model,
                    new ResponseHTML("Warestore - Регистрация", "Зарегистрировать нового пользователя не удалось.\nДанный телефон и email уже зарегистрированы в системе!"));
        }
    }

    @PostMapping(value = "/editPassword")
    public String editPassword(@ModelAttribute @Valid EditPassword editPassword, Model model, @CookieValue(name = "WarestoreToken") Cookie token){
        try {
            ResponseEntity<?> response = requestService.getOrPostData(
                    environment.getProperty("url.user.password"),
                    token,
                    HttpMethod.POST,
                    new HttpEntity<>(editPassword)
            );
            if (response.getStatusCode()==HttpStatus.OK)
                return responseController.response(model,
                        new ResponseHTML("Warestore - Изменение информации", "Ваш пароль был успешно изменён!"));
            else return  responseController.response(model,
                    new ResponseHTML("Warestore - Изменение информации", "Ваш пароль не был изменён.\nПопробуйте позже."));
        }
        catch (ResourceAccessException | HttpClientErrorException.Conflict ignored){
            return responseController.response(model,
                    new ResponseHTML("Warestore - Техническое обслуживание", "Ресурс временно недоступен :(\nСервер на обслуживании."));
        }
        catch (HttpClientErrorException.NotAcceptable | HttpClientErrorException.NotFound ignored){
            return responseController.response(model,
                    new ResponseHTML("Warestore - Изменение информации", "Не верный текущий пароль!\nПопробуйте снова."));
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
            model.addAttribute("editUser", new User());
            model.addAttribute("editPassword", new EditPassword());
            return "profile";
        }
        catch (HttpClientErrorException.Forbidden ignored){
            return responseController.response(model,
                    new ResponseHTML("Warestore - Доступ ограничен", "Пожалуйста, авторизуйтесь для доступа в защищённую область."));
        }
        catch (ResourceAccessException | HttpClientErrorException.Conflict ignored){
            return responseController.response(model,
                    new ResponseHTML("Warestore - Техническое обслуживание", "Ресурс временно недоступен :(\nСервер на обслуживании."));
        }
    }
    @PostMapping("/getOrderPageButtonsUser")
    public String nextOrdersPage(@RequestParam("currentPage") String page, Model model, @CookieValue(value = "WarestoreToken", required = true) Cookie token){
        return getProfile(model, token, page);
    }

}
