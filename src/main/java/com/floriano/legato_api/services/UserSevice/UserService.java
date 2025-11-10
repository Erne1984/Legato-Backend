package com.floriano.legato_api.services.UserSevice;

import com.floriano.legato_api.dto.UserDTO.UserRequestDTO;
import com.floriano.legato_api.dto.UserDTO.UserResponseDTO;
import com.floriano.legato_api.dto.UserDTO.UserUpdateDTO;
import com.floriano.legato_api.exceptions.UserNotFoundException;
import com.floriano.legato_api.mapper.UserMapper;
import com.floriano.legato_api.model.User.User;
import com.floriano.legato_api.repositories.UserRepository;
import com.floriano.legato_api.services.UserSevice.utils.UserUpdateHelper;
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

    public UserResponseDTO updateUser(Long id, UserUpdateDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário com id " + id + " não encontrado"));

        UserUpdateHelper.updateBasicFields(user, dto);
        UserUpdateHelper.updateMusicPreferences(user, dto);
        UserUpdateHelper.updateExternalLinks(user, dto);
        UserUpdateHelper.updateLocation(user, dto);

        user.setUpdatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);

        return UserMapper.toDTO(user);
    }

    public boolean userExists(String email){
        return userRepository.existsByEmail(email);
    }
}
