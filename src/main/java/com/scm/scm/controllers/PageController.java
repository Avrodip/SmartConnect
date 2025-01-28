package com.scm.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scm.scm.entities.User;
import com.scm.scm.forms.UserForm;
import com.scm.scm.helpers.Message;
import com.scm.scm.helpers.MessageType;
import com.scm.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {
    @Autowired
    private UserService userService;

    @RequestMapping("/home")
    public String home(Model model) {
        model.addAttribute("name", "Substring Technologies");
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
    public String register(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult, HttpSession session) {
        System.out.println("Registration Done" + userForm);
        if(rBindingResult.hasErrors()){
            return "register";
        }
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setAbout(userForm.getAbout());
        user.setProfilePic(
                "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAKIAAACUCAMAAAAnDwKZAAAAM1BMVEXk5ueutLeqsbTP09Xn6eq6v8Lh4+Sxt7rY29zc3+DM0NLHy822u77U19mnrrHq7O3AxceMdfF6AAADw0lEQVR4nO2b247cIAxAAzF3QvL/X1uS2bm12RmME5NKnEqr7T4dAXbAmGHodDqdTqfT6XQ6nU6n09kFALTWg95+ay2zA4CbfFAryxK81RezhEGHKISQUt5/mjjq60iCHlVa3d6RSU3DNSxnH+W/gpukiHZurZeZzL7fjRRt44GEYfkkuC1M39bQxS+Gq2RsaTiJ74bZ0TSbbBgL/G40cgRfMoQ3UhNHmMoN13FsYYgRzGh2RY00FOxxDQo1zRkZmA09dhCz48QaMg5vmOFUnNHTvA2j53MEm6pG0Tg+RVVlKPh2FOiU+IQrOUKoWYkraWRajeis/cTwbMJhqguWDZ5P9VwZLBtMeYdgKBWHIFiCIk9qrI/nbRg5FiN8O/N9VuTI3poSLUIuDPGiI0VRKAZFZ0iKHDsyoqJkULSUaMmf6a7YFY+i7mTFq3j9pENM3ZFD8fofwK+l48+KHNsI4mZs4lC8/paWeDBgOUnPhJCWgeV4hawgv8NU89YERaZqRP3pha189x+UnYahtnjHV+6ureoYxsuXueB2cm8QOYvdVZtGjpPVk5qgllz1zzvoqZYLr+AA6KmO7JeAMOKGUTr+617MdXSG93bt7hjKs2NqYojaf3MH89OxdK4bjeHmOBa0mEjTIFJeHIevd6rcV+U7fB7IZBpO8h2A5deuMSn8NdoYQQez03snpfLXaWEEbZV8t5Qy2It0L96ZZ+cXpeKKUsvo5mv53VjbfV3+p/X12n1/jPQL+X9X6UteNZwdfVgn2eSoWTFmnevgx8m19swTO4WH2d/xvP7JxJiXZRvNddmNS0w7ncj/5J6Usw97nzeADWovF/7qKeLC2EGdF18QCL2HpVwmzaEJziu8349lMsGePeGgF8z87o2lOnNfAYPda9THksxprw9mq4p6pL8j43jKmnTVS3APc/jNQd5vHSmYScuxNWWwH986VCFFOC64cxgTusQ+SB52aDhjCH8cD6p9IwsjSEl1RP45Z5IfjvRDtq7qPcZAXJCgD0rWnyB1rxY9CjqAekegtkGUUl0FB31WsjnM8fxIeaHqa4h/LkIhVeQeWgsEmoo+YNK9eJUj+lpGMwXziyMyhc+kZpxKUIq1D1pIIBuhSE1X1SCuq7GXZweBGUZgj5Ub5cmR9FqEglSlz2FaDaIoXo20vjUahd0TzJ++Nwrv1WltnjQK35q4doNY2CcDvlE8bxQ9cWvyeX5Q1PY9tzTM2btkolvOc9kpxrVVLIiX/PWTDUklD5+cH1tSVL2FppQYdjqdzon8AfWmM9Xf67QYAAAAAElFTkSuQmCC");
        User savedUser = userService.saveUser(user);
        System.out.println("user saved" + savedUser);

        Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();
        session.setAttribute("message", message);
        return "redirect:/register";
    }
}
