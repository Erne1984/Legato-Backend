package com.floriano.legato_api.services.UserSevice;

import com.floriano.legato_api.dto.UserDTO.UserRequestDTO;
import com.floriano.legato_api.dto.UserDTO.UserResponseDTO;
import com.floriano.legato_api.dto.UserDTO.UserUpdateDTO;
import com.floriano.legato_api.exceptions.UserNotFoundException;
import com.floriano.legato_api.mapper.UserMapper;
import com.floriano.legato_api.model.User.User;
import com.floriano.legato_api.repositories.UserRepository;
import com.floriano.legato_api.services.UserSevice.utils.UserDeleteHelper;
import com.floriano.legato_api.services.UserSevice.utils.UserUpdateHelper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDeleteHelper userDeleteHelper;

    public UserService(UserRepository userRepository, UserDeleteHelper userDeleteHelper) {
        this.userRepository = userRepository;
        this.userDeleteHelper = userDeleteHelper;
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

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário com id " + id + " não encontrado"));

        userDeleteHelper.deleteUserAndCleanup(user);
    }

    @Transactional
    public UserResponseDTO followUser(Long followerId, Long targetId) {
        if (followerId.equals(targetId)) {
            throw new IllegalArgumentException("Um usuário não pode seguir a si mesmo.");
        }

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new UserNotFoundException("Usuário seguidor com id " + followerId + " não encontrado"));

        User target = userRepository.findById(targetId)
                .orElseThrow(() -> new UserNotFoundException("Usuário alvo com id " + targetId + " não encontrado"));

        if (follower.getBlockedUsers().contains(target) || target.getBlockedUsers().contains(follower)) {
            throw new IllegalStateException("Ação não permitida: um dos usuários bloqueou o outro.");
        }

        follower.follow(target);
        userRepository.save(follower);
        userRepository.save(target);

        return UserMapper.toDTO(target);
    }

    @Transactional
    public UserResponseDTO unfollowUser(Long followerId, Long targetId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new UserNotFoundException("Usuário seguidor com id " + followerId + " não encontrado"));

        User target = userRepository.findById(targetId)
                .orElseThrow(() -> new UserNotFoundException("Usuário alvo com id " + targetId + " não encontrado"));

        follower.unfollow(target);
        userRepository.save(follower);
        userRepository.save(target);

        return UserMapper.toDTO(target);
    }

    public boolean userExists(String email){
        return userRepository.existsByEmail(email);
    }
}
