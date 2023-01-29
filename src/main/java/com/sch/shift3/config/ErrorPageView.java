package com.sch.shift3.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPageView {
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/503";
    }

    @GetMapping("/404")
    public String notFound() {
        return "error/404";
    }
}
