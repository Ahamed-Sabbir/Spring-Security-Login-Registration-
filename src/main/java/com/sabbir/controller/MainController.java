package com.sabbir.controller;

import com.sabbir.model.User;
import com.sabbir.service.UserService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/login"})
    public String loginPage(@RequestParam(required = false) String error, Model model){
        if(error != null && error.equals("true")){
            model.addAttribute("error", "Invalid Credentials");
        }
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request){
        if(request.isUserInRole("ROLE_ADMIN")){
            return "redirect:/admin/dashboard";
        }
        return "redirect:/user/dashboard";
    }

    @GetMapping("/user/registration")
    public String userRegistrationPage(){
        return "registration-user";
    }

    @PostMapping("/user/registration")
    public String registerUser(User user, RedirectAttributes redirectAttributes){
        if(userService.findUserByUsername(user.getUsername()) != null){
            redirectAttributes.addFlashAttribute("error", "Username Already Exists");
            return "redirect:/user/registration";
        }
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("success", "Registration Successful");
        return "redirect:/user/registration";
    }

    @GetMapping("/admin/registration")
    public String adminRegistrationPage(){
        return "registration-admin";
    }

    @PostMapping("/admin/registration")
    public String registerAdmin(User user, RedirectAttributes redirectAttributes, Model model){
        if(userService.findUserByUsername(user.getUsername()) != null){
            redirectAttributes.addFlashAttribute("error", "Username Already Exists");
            return "redirect:/admin/registration";
        }
        userService.saveAdmin(user);
        redirectAttributes.addFlashAttribute("success", "Registration Successful");
        return "redirect:/admin/registration";
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
