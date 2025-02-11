package com.scm.scm.helpers;

// import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {
    public static String getEmailOfLoggedInUser(Authentication authentication) {

        if (authentication instanceof OAuth2AuthenticationToken) {
            var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            var cliendId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User=(OAuth2User)authentication.getPrincipal();
            String username="";
            if (cliendId.equalsIgnoreCase("google")) {
                // email if logged in through google
                // return oauth2AuthenticationToken.getPrincipal().getAttribute("email");
                username=oauth2User.getAttribute("email").toString();
            } else if (cliendId.equalsIgnoreCase("github")) {
                // email if logged in through github
                username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString()
                : oauth2User.getAttribute("login").toString() + "@github.com";
            }
            return username;
        } else {
            System.out.println("Email: " + authentication.getName());
            return authentication.getName();
        }
    }
}
