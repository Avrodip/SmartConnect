package com.scm.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("/home")
    public String home(Model model) {
        model.addAttribute("name","Substring Technologies");
        System.out.println("Home Page");
        return "home"; 
    }


    @RequestMapping("/about")
    public String about() {
        return "about"; 
    }

    @RequestMapping("/services")
    public String services() {
        return "services"; 
    }

    @RequestMapping("/contact")
    public String contact() {
        return "contact"; 
    }

    @RequestMapping("/login")
    public String login() {
        return "login"; 
    }

    @RequestMapping("/register")
    public String register() {
        return "register"; 
    }
}
