package com.scm.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ContactForm {
    @NotBlank(message = "Name is required")
    private String name;
    @Email(message = "Email is not valid [example@gmail.com]")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Phone number is not valid")
    private String phoneNumber;
    @NotBlank(message = "Address is required")
    private String address;
    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedInLink;
    private MultipartFile profileImage;

}