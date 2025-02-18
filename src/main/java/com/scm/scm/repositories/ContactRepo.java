package com.scm.scm.repositories;

import java.util.List;

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
    List<Contact> findByUser(User user);

    //custom query method
    @Query("SELECT c from Contact c WHERE c.user.id = :userid")
    List<Contact> findByUserId(@Param("userId")String userId);

}