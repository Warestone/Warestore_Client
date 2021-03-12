package org.warestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.warestore.model.Ammo;
import org.warestore.model.Category;
import org.warestore.model.Target;
import org.warestore.model.Weapon;
import org.warestore.service.RequestService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class CatalogController {

    @Autowired
    Environment environment;

    @Autowired
    private RequestService requestService;

    @GetMapping("/")
    public String getIndexPage(Model model, @CookieValue(name = "WarestoreToken",  required = false) Cookie token){
        List<Category> categories = (List<Category>) requestService.
                getOrPostData(environment.getProperty("url.categories"), token,
                        HttpMethod.GET,null
                );
        model.addAttribute("categoriesList", categories);
        return "index";
    }

    @GetMapping("/rifles")
    public String getRiflesPage(Model model, String page, @CookieValue(name = "WarestoreToken", required = false) Cookie token){
        List<Weapon> rifles = (List<Weapon>) requestService.getOrPostData(requestService.
                getURL(environment.getProperty("url.rifles"), page), token,
                HttpMethod.GET,null
        );
        model.addAttribute("riflesList", rifles);
        return "rifles";
    }
    @PostMapping("/getRiflesPageButtons")
    public String nextRiflesPage(@RequestParam("currentPage") String page, Model model, @CookieValue(value = "WarestoreToken", required = false) Cookie token){
        return getRiflesPage(model, page, token);
    }


    @GetMapping("/shotguns")
    public String getShotgunsPage(Model model, String page, @CookieValue(value = "WarestoreToken", required = false) Cookie token){
        List<Weapon> shotguns = (List<Weapon>) requestService.getOrPostData(requestService.
                getURL(environment.getProperty("url.shotguns"), page), token,
                HttpMethod.GET,null
        );
        model.addAttribute("shotgunsList", shotguns);
        return "shotguns";
    }
    @PostMapping("/getShotgunsPageButtons")
    public String nextShotgunPage(@RequestParam("currentPage") String page, Model model, @CookieValue(value = "WarestoreToken", required = false) Cookie token){
        return getShotgunsPage(model, page, token);
    }

    @GetMapping("/airguns")
    public String getAirgunsPage(Model model, String page, @CookieValue(value = "WarestoreToken", required = false) Cookie token){ ;
        List<Weapon> airguns = (List<Weapon>) requestService.getOrPostData(requestService.
                        getURL(environment.getProperty("url.airguns"), page), token,
                HttpMethod.GET,null
        );
        model.addAttribute("airgunsList", airguns);
        return "airguns";
    }
    @PostMapping("/getAirgunsPageButtons")
    public String nextAirgunsPage(@RequestParam("currentPage") String page, Model model, @CookieValue(value = "WarestoreToken", required = false) Cookie token){
        return getAirgunsPage(model, page, token);
    }

    @GetMapping("/ammo")
    public String getAmmoPage(Model model, String page, @CookieValue(value = "WarestoreToken", required = false) Cookie token){
        List<Ammo> ammo = (List<Ammo>) requestService.getOrPostData(requestService.
                getURL(environment.getProperty("url.ammo"), page), token,
                HttpMethod.GET,null
        );
        model.addAttribute("ammoList", ammo);
        return "ammo";
    }
    @PostMapping("/getAmmoPageButtons")
    public String nextAmmoPage(@RequestParam("currentPage") String page, Model model, @CookieValue(value = "WarestoreToken", required = false) Cookie token){
        return getAmmoPage(model, page, token);
    }

    @GetMapping("/targets")
    public String getTargetPage(Model model, String page, @CookieValue(value = "WarestoreToken", required = false) Cookie token){
        List<Target> targets = (List<Target>) requestService.getOrPostData(requestService.
                getURL(environment.getProperty("url.targets"), page), token,
                HttpMethod.GET,null
        );
        model.addAttribute("targetList", targets);
        return "targets";
    }
    @PostMapping("/getTargetsPageButtons")
    public String nextTargetPage(@RequestParam("currentPage") String page, Model model, @CookieValue(value = "WarestoreToken", required = false) Cookie token){
        return getTargetPage(model, page, token);
    }

}
