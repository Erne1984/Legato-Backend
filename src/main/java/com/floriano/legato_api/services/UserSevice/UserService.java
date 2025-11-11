package com.floriano.legato_api.services.UserSevice;

import com.floriano.legato_api.dto.UserDTO.UserRequestDTO;
import com.floriano.legato_api.dto.UserDTO.UserResponseDTO;
import com.floriano.legato_api.dto.UserDTO.UserUpdateDTO;
import com.floriano.legato_api.exceptions.UserNotFoundException;
import com.floriano.legato_api.mapper.UserMapper;
import com.floriano.legato_api.model.User.User;
import com.floriano.legato_api.repositories.UserRepository;
import com.floriano.legato_api.services.UserSevice.useCases.*;
import com.floriano.legato_api.services.UserSevice.utils.UserDeleteHelper;
import com.floriano.legato_api.services.UserSevice.utils.UserUpdateHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ListAllUsersService listAllUsersService;
    private final FindByEmailService findByEmailService;
    private final FindByIdService findByIdService;
    private final UpdateUserService updateUserService;
    private final DeleteUserService deleteUserService;
    private final FollowUserService followUserService;
    private final UnfollowUserService unfollowUserService;

    public List<UserResponseDTO> listAllUsers() {
        return listAllUsersService.execute();
    }

    public User findByEmail(String email) {
        return findByEmailService.execute(email);
    }

    public User findById(Long id) {
        return findByIdService.execute(id);
    }

    public UserResponseDTO updateUser(Long id, UserUpdateDTO dto) {
        return updateUserService.execute(id, dto);
    }

    public void deleteUser(Long id) {
        deleteUserService.execute(id);
    }

    public UserResponseDTO followUser(Long followerId, Long targetId) {
        return followUserService.execute(followerId, targetId);
    }

    public UserResponseDTO unfollowUser(Long followerId, Long targetId) {
        return unfollowUserService.execute(followerId, targetId);
    }

}
