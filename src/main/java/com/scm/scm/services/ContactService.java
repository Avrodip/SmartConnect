package com.scm.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scm.scm.entities.Contact;
import com.scm.scm.entities.User;

public interface ContactService {

    //save contacts
   Contact save(Contact contact);

   //update contact
   Contact update(Contact contact);

   //get contacts
   List<Contact> getAll();

   //get contact by id
   Contact getById(String id);

   //delete contact
   void delete(String id);

   //search contact
   List<Contact> search(String name,String email,String phoneNumber);

   //get contacts by userId
   List<Contact> getByUserId(String userId);

   //get contacts by user
   Page<Contact> getByUser(User user,int page,int size,String sortField,String sortDirection);
   

   

}