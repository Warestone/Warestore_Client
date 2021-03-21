package org.warestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.warestore.model.*;
import org.warestore.service.RequestService;
import javax.servlet.http.Cookie;
import java.util.List;
import java.util.Map;

@Controller
public class CatalogController {

    @Autowired
    Environment environment;

    @Autowired
    private RequestService requestService;

    @GetMapping("/")
    public String getIndexPage(Model model, @CookieValue(name = "WarestoreToken",  required = false) Cookie token){
        ResponseEntity<?> response = requestService.
                getOrPostData(environment.getProperty("url.categories"), token,
                        HttpMethod.GET,null
                );
        if (!requestService.checkStatusCode(response)){

        }
        List<Category> categories = (List<Category>) response.getBody();
        model.addAttribute("categoriesList", categories);
        if (token==null)
            model.addAttribute("linkButton", new LinkButton("Вход/Регистрация","/authentication"));
        else if (token.getValue()!=null && !token.getValue().equals(""))
            model.addAttribute("linkButton", new LinkButton("Выйти из аккаунта","/"));
        else model.addAttribute("linkButton", new LinkButton("Вход/Регистрация","/authentication"));
        return "index";
    }

    @GetMapping("/rifles")
    public String getRiflesPage(Model model, String page, @CookieValue(name = "WarestoreToken", required = false) Cookie token){
        ResponseEntity<?> response = requestService.getOrPostData(requestService.
                        getURL(environment.getProperty("url.rifles"), page), token,
                HttpMethod.GET,null
        );
        if (!requestService.checkStatusCode(response)) {
            // call error-page
        }
        List<Weapon> rifles = (List<Weapon>) response.getBody();
        model.addAttribute("riflesList", rifles);
        return "rifles";
    }
    @PostMapping("/getRiflesPageButtons")
    public String nextRiflesPage(@RequestParam("currentPage") String page, Model model, @CookieValue(value = "WarestoreToken", required = false) Cookie token){
        return getRiflesPage(model, page, token);
    }


    @GetMapping("/shotguns")
    public String getShotgunsPage(Model model, String page, @CookieValue(value = "WarestoreToken", required = false) Cookie token){
        ResponseEntity<?> response =  requestService.getOrPostData(requestService.
                        getURL(environment.getProperty("url.shotguns"), page), token,
                HttpMethod.GET,null
        );
        if (!requestService.checkStatusCode(response)){
            // call error-page
        }
        List<Weapon> shotguns = (List<Weapon>) response.getBody();
        model.addAttribute("shotgunsList", shotguns);
        return "shotguns";
    }
    @PostMapping("/getShotgunsPageButtons")
    public String nextShotgunPage(@RequestParam("currentPage") String page, Model model, @CookieValue(value = "WarestoreToken", required = false) Cookie token){
        return getShotgunsPage(model, page, token);
    }

    @GetMapping("/airguns")
    public String getAirgunsPage(Model model, String page, @CookieValue(value = "WarestoreToken", required = false) Cookie token){
        ResponseEntity<?> response = requestService.getOrPostData(requestService.
                        getURL(environment.getProperty("url.airguns"), page), token,
                HttpMethod.GET,null
        );
        if (!requestService.checkStatusCode(response)){
            // call error-page
        }
        List<Weapon> airguns = (List<Weapon>) response.getBody();
        model.addAttribute("airgunsList", airguns);
        return "airguns";
    }
    @PostMapping("/getAirgunsPageButtons")
    public String nextAirgunsPage(@RequestParam("currentPage") String page, Model model, @CookieValue(value = "WarestoreToken", required = false) Cookie token){
        return getAirgunsPage(model, page, token);
    }

    @GetMapping("/ammo")
    public String getAmmoPage(Model model, String page, @CookieValue(value = "WarestoreToken", required = false) Cookie token){
        ResponseEntity<?> response = requestService.getOrPostData(requestService.
                        getURL(environment.getProperty("url.ammo"), page), token,
                HttpMethod.GET,null
        );
        if (!requestService.checkStatusCode(response)){
            // call error-page
        }
        List<Ammo> ammo = (List<Ammo>) response.getBody();
        model.addAttribute("ammoList", ammo);
        return "ammo";
    }
    @PostMapping("/getAmmoPageButtons")
    public String nextAmmoPage(@RequestParam("currentPage") String page, Model model, @CookieValue(value = "WarestoreToken", required = false) Cookie token){
        return getAmmoPage(model, page, token);
    }

    @GetMapping("/targets")
    public String getTargetPage(Model model, String page, @CookieValue(value = "WarestoreToken", required = false) Cookie token){
        ResponseEntity<?> response = requestService.getOrPostData(requestService.
                        getURL(environment.getProperty("url.targets"), page), token,
                HttpMethod.GET,null
        );
        if (!requestService.checkStatusCode(response)){
            // call error-page
        }
        List<Target> targets = (List<Target>) response.getBody();
        model.addAttribute("targetList", targets);
        return "targets";
    }
    @PostMapping("/getTargetsPageButtons")
    public String nextTargetPage(@RequestParam("currentPage") String page, Model model, @CookieValue(value = "WarestoreToken", required = false) Cookie token){
        return getTargetPage(model, page, token);
    }

    @PostMapping("/order")
    public void addToOrder(@RequestBody Map<String, Item> cart, @CookieValue(value = "WarestoreToken", required = true) Cookie token){
        int vasr =0 ;
    }
}
