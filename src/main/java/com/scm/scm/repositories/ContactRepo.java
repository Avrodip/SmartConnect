package com.scm.scm.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.scm.entities.Contact;
import com.scm.scm.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact,String> {

    //find the contact by user
    //custom finder method
    Page<Contact> findByUser(User user,Pageable pageable);

    //custom query method
    @Query("SELECT c from Contact c WHERE c.user.id = :userid")
    List<Contact> findByUserId(@Param("userId")String userId);

    Page<Contact> findByUserAndNameContainingIgnoreCase(User user, String nameKeyword, PageRequest pageable);

    Page<Contact> findByUserAndEmailContainingIgnoreCase(User user, String emailKeyword, PageRequest pageable);

    Page<Contact> findByUserAndPhoneNumberContainingIgnoreCase(User user, String phoneNumberKeyword, PageRequest pageable);

}