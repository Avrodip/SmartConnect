package com.scm.scm.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.scm.entities.User;
import com.scm.scm.helpers.AppConstants;
import com.scm.scm.helpers.ResourceNotFoundException;
import com.scm.scm.repositories.UserRepo;
import com.scm.scm.services.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        String userId=UUID.randomUUID().toString();
        user.setUserId(userId);
        // user.setProfilePic("user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //set the user role 
        user.setRolesList(List.of(AppConstants.ROLE_USER));
        logger.info(user.getProvider().toString());
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String userId) {
        return userRepo.findById(userId);
    }
    @Override
    public Optional<User> updateUser(User user) {
        //user old is the current user data fetched from the databse
      User user_old=userRepo.findById(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
      user_old.setName(user.getName());
      user_old.setEmail(user.getEmail());
      user_old.setPassword(user.getPassword());
      user_old.setAbout(user.getAbout());
      user_old.setPhoneNumber(user.getPhoneNumber());
      user_old.setProfilePic(user.getProfilePic());
      user_old.setEnabled(user.isEnabled());
      user_old.setEmailVerified(user.getEmailVerified());
      user_old.setPhoneVerified(user.getPhoneVerified());
      user_old.setProvider(user.getProvider());
      user_old.setProviderUserId(user.getProviderUserId());


      User user_res = userRepo.save(user_old);
      return Optional.ofNullable(user_res);
    }

    @Override
    public void deleteUser(String userId) {
        User user_old=userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepo.delete(user_old);
    }


    @Override
    public boolean userExists(String userId) {
        User user=userRepo.findById(userId).orElse(null);
        return user!=null?true:false;

    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user=userRepo.findByEmail(email).orElse(null);
        return user!=null?true:false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

}
