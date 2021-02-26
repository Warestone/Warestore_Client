package org.warestore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.warestore.model.Ammo;
import org.warestore.model.Category;
import org.warestore.model.Target;
import org.warestore.model.Weapon;
import org.warestore.service.ResponseService;
import java.util.List;

@Controller
public class CatalogController {
    private final ResponseService responseService = new ResponseService();

    @GetMapping("/")
    public String getIndexPage(Model model){
        List<Category> categories = (List<Category>) responseService.
                getData("http://localhost:2033/server/catalog/get/category");
        model.addAttribute("categoriesList", categories);
        return "index";
    }

    @GetMapping("/rifles")
    public String getRiflesPage(Model model){
        List<Weapon> rifles = (List<Weapon>) responseService.
                getData("http://localhost:2033/server/catalog/get/rifle");
        model.addAttribute("riflesList", rifles);
        return "rifles";
    }

    @GetMapping("/shotguns")
    public String getShotgunsPage(Model model){
        List<Weapon> shotguns = (List<Weapon>) responseService.
                getData("http://localhost:2033/server/catalog/get/shotgun");
        model.addAttribute("shotgunsList", shotguns);
        return "shotguns";
    }

    @GetMapping("/airguns")
    public String getAirgunPage(Model model){ ;
        List<Weapon> airguns = (List<Weapon>) responseService.
                getData("http://localhost:2033/server/catalog/get/airgun");
        model.addAttribute("airgunsList", airguns);
        return "airguns";
    }

    @GetMapping("/ammo")
    public String getAmmoPage(Model model){
        List<Ammo> ammo = (List<Ammo>) responseService.
                getData("http://localhost:2033/server/catalog/get/ammo");
        model.addAttribute("ammoList", ammo);
        return "ammo";
    }

    @GetMapping("/targets")
    public String getTargetPage(Model model){
        List<Target> targets = (List<Target>) responseService.
                getData("http://localhost:2033/server/catalog/get/target");
        model.addAttribute("targetList", targets);
        return "targets";
    }
}
