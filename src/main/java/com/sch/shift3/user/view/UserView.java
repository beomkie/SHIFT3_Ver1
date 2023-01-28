package com.sch.shift3.user.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserView {

    @GetMapping("/")
    public String mainPage(){
        return "user/content/main";
    }

    @GetMapping("/introduce")
    public String introducePage() {
        return "user/content/pages/introduce";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("disableLoading", true);
        return "user/content/login";
    }

    @GetMapping("/forgot")
    public String forgotPage(){
        return "user/content/forgot";
    }

}