package com.scm.scm.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Contact {
    @Id
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String picture;
    @Column(columnDefinition = "TEXT")
    private String description;
    private boolean favourite = false;
    private String facebookLink;
    private String instagramLink;


    @ManyToOne
    private User user;

    
    @OneToMany(mappedBy = "contact",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    private List<SocialLink>links=new ArrayList<>();

    // private List<SocialLink> socialLinks=new ArrayList<>();
}
