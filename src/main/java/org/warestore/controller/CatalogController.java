package org.warestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.warestore.model.Ammo;
import org.warestore.model.Category;
import org.warestore.model.Target;
import org.warestore.model.Weapon;
import org.warestore.service.RequestService;
import java.util.List;

@Controller
public class CatalogController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/")
    public String getIndexPage(Model model){
        List<Category> categories = (List<Category>) requestService.
                getData("http://localhost:2033/server/catalog/get/category");
        model.addAttribute("categoriesList", categories);
        return "index";
    }

    @GetMapping("/rifles")
    public String getRiflesPage(Model model, String page){
        List<Weapon> rifles = (List<Weapon>) requestService.getData(requestService.
                getURL("http://localhost:2033/server/catalog/get/rifle_page/", page)
        );
        model.addAttribute("riflesList", rifles);
        return "rifles";
    }
    @PostMapping("/getRiflesPageButtons")
    public String nextRiflesPage(@RequestParam("currentPage") String page, Model model){ return getRiflesPage(model, page); }


    @GetMapping("/shotguns")
    public String getShotgunsPage(Model model, String page){
        List<Weapon> shotguns = (List<Weapon>) requestService.getData(requestService.
                getURL("http://localhost:2033/server/catalog/get/shotgun_page/", page)
        );
        model.addAttribute("shotgunsList", shotguns);
        return "shotguns";
    }
    @PostMapping("/getShotgunsPageButtons")
    public String nextShotgunPage(@RequestParam("currentPage") String page, Model model){ return getShotgunsPage(model, page); }

    @GetMapping("/airguns")
    public String getAirgunsPage(Model model, String page){ ;
        List<Weapon> airguns = (List<Weapon>) requestService.getData(requestService.
                        getURL("http://localhost:2033/server/catalog/get/airgun_page/", page));
        model.addAttribute("airgunsList", airguns);
        return "airguns";
    }
    @PostMapping("/getAirgunsPageButtons")
    public String nextAirgunsPage(@RequestParam("currentPage") String page, Model model){ return getAirgunsPage(model, page); }

    @GetMapping("/ammo")
    public String getAmmoPage(Model model, String page){
        List<Ammo> ammo = (List<Ammo>) requestService.getData(requestService.
                getURL("http://localhost:2033/server/catalog/get/ammo_page/", page));
        model.addAttribute("ammoList", ammo);
        return "ammo";
    }
    @PostMapping("/getAmmoPageButtons")
    public String nextAmmoPage(@RequestParam("currentPage") String page, Model model){ return getAmmoPage(model, page); }

    @GetMapping("/targets")
    public String getTargetPage(Model model, String page){
        List<Target> targets = (List<Target>) requestService.getData(requestService.
                getURL("http://localhost:2033/server/catalog/get/target_page/", page));
        model.addAttribute("targetList", targets);
        return "targets";
    }
    @PostMapping("/getTargetsPageButtons")
    public String nextTargetPage(@RequestParam("currentPage") String page, Model model){ return getTargetPage(model, page); }

}
