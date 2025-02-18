package com.scm.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.scm.entities.Contact;
import com.scm.scm.forms.ContactForm;
import com.scm.scm.helpers.Message;
import com.scm.scm.helpers.MessageType;
import com.scm.scm.services.ContactService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping("/add")

    // add contact page handler
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
            Authentication authentication, HttpSession session) {

        // process the form data

        if (result.hasErrors()) {
            session.setAttribute("message",
                    Message.builder().content("Please correct the errors in the form!").type(MessageType.red).build());
            return "user/add_contact";
        }

        // form --> contact
        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());

        contactService.save(contact);

        System.out.println(contactForm);
        session.setAttribute("message",
        Message.builder().content("Contact added successfully").type(MessageType.red).build());

        return "redirect:/user/contacts/add";
    }

}