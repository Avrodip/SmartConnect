package com.scm.scm.controllers;

import java.security.Principal;

// import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.scm.config.OAuthAuthenticationSuccessHandler;
import com.scm.scm.entities.User;
import com.scm.scm.helpers.Helper;
import com.scm.scm.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    // user dashboard page
    @RequestMapping(value = "/dashboard")
    public String userDashboard() {
        System.out.println("User dashboard");
        return "user/dashboard";
    }

    // user profile page
    @RequestMapping(value = "/profile")
    public String userProfile(Model model, Authentication authentication) {

        return "user/profile";
    }

    // user add contacts page

    // user view contacts

    // user edit contact

    // user delete contact

    // user search contact
}