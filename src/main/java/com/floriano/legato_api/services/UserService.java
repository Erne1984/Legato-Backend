package com.floriano.legato_api.services;

import com.floriano.legato_api.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean userExists(String email){
        return userRepository.existsByEmail(email);
    }
}
