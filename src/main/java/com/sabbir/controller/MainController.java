package com.sabbir.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping({"/", "/login"})
    public String loginPage(){
        return "login";
    }

    @GetMapping("/user/registration")
    public String userRegistrationPage(){
        return "registration-user";
    }

    @GetMapping("/admin/registration")
    public String adminRegistrationPage(){
        return "registration-admin";
    }

    @GetMapping("/user/dashboard")
    public String userDashboardPage(){
        return "dashboard-user";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboardPage(){
        return "dashboard-admin";
    }

}
