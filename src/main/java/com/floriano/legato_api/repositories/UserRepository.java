package com.floriano.legato_api.repositories;

import com.floriano.legato_api.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long > {

    UserDetails findByEmail(String email);
    boolean existsByEmail(String email);
}
