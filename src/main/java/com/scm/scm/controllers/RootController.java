package com.scm.scm.controllers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.scm.entities.User;
import com.scm.scm.helpers.Helper;
import com.scm.scm.services.UserService;

@ControllerAdvice
public class RootController {
    private Logger logger = Logger.getLogger(RootController.class.getName());
    @Autowired
    private UserService userService;
    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication) {
        if(authentication==null){
            return;
        }
        System.out.println("Adding logged in user information");
        String username = Helper.getEmailOfLoggedInUser(authentication);
        // logger.info("User name: "+name);
        // System.out.println("User profile");
        User user = userService.getUserByEmail(username);
        System.out.println("user"+ user);
            System.out.println(user.getName());
            System.out.println(user.getEmail());
            model.addAttribute("loggedInUser", user);
    }
}
