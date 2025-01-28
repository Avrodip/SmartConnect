package com.scm.scm.repositories;
import java.util.Optional;

// repositories are used to interact with the db
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scm.scm.entities.User; // Make sure this path matches your project structure

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    Optional <User> findByEmail(String email);
    Optional <User> findByEmailAndPassword(String email, String password);
    
}
