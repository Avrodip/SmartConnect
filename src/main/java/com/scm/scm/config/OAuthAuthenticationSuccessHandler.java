package com.scm.scm.config;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.util.List;

import com.scm.scm.entities.Providers;
import com.scm.scm.entities.User;
import com.scm.scm.helpers.AppConstants;
import com.scm.scm.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        logger.info("OAuthAuthenticationSuccessHandler");

        // Identify the provider
        var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

        logger.info("Provider: " + authorizedClientRegistrationId);

        var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

        oauthUser.getAttributes().forEach((key, value) -> logger.info(key + ": " + value));

        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setPassword("dummy");

        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
            // Google attributes (ensure null checks)
            user.setEmail(oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
                    : "no-email@google.com");
            user.setProfilePic(
                    oauthUser.getAttribute("picture") != null ? oauthUser.getAttribute("picture").toString() : "");
            user.setName(
                    oauthUser.getAttribute("name") != null ? oauthUser.getAttribute("name").toString() : "Google User");
            user.setProviderUserId(oauthUser.getName());
            user.setProvider(Providers.GOOGLE);
            user.setAbout("This account is created using Google.");

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
            // GitHub attributes (ensure null checks)
            String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
                    : oauthUser.getAttribute("login").toString() + "@github.com";
            String picture = oauthUser.getAttribute("avatar_url") != null
                    ? oauthUser.getAttribute("avatar_url").toString()
                    : "";
            String name = oauthUser.getAttribute("login") != null ? oauthUser.getAttribute("login").toString()
                    : "GitHub User";
            String providerUserId = oauthUser.getName();

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderUserId(providerUserId);
            user.setProvider(Providers.GITHUB);
            user.setAbout("This account is created using GitHub.");

        } else {
            logger.info("OAuthAuthenticationSuccessHandler: Unknown Provider");
        }

        // Save the user in the database if not already present
        User existingUser = userRepo.findByEmail(user.getEmail()).orElse(null);
        if (existingUser == null) {
            userRepo.save(user);
            logger.info("User saved: " + user.getEmail());
        }

        // Redirect to profile page
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }

}