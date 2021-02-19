package org.warestore.controller;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.warestore.model.Category;
import java.util.List;

@Controller
public class ClientController {


    @GetMapping("/")
    public String getSearchUserPage(Model model){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Category>> response = restTemplate.exchange(
                "http://localhost:2033/server/get/category",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Category>>(){});
        List<Category> categories = response.getBody();
        model.addAttribute("categoriesList", categories);
        return "index";
    }
}
