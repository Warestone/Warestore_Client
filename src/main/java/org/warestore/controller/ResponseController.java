package org.warestore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.warestore.model.ResponseHTML;

@Controller
public class ResponseController {
    @GetMapping("/response")
    public String response(Model model, ResponseHTML responseHTML){
        model.addAttribute("response", responseHTML);
        return "response";
    }
}
