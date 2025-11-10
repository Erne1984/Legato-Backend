package com.floriano.legato_api.services;

import com.floriano.legato_api.dto.UserDTO.UserResponseDTO;
import com.floriano.legato_api.mapper.UserMapper;
import com.floriano.legato_api.model.User.User;
import com.floriano.legato_api.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDTO> listAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserResponseDTO> userResponseDTOS = users.stream().map(UserMapper :: toDTO).toList();

        return userResponseDTOS;
    }

    public boolean userExists(String email){
        return userRepository.existsByEmail(email);
    }
}
