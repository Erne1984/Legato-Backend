package com.floriano.legato_api.services.UserSevice;

import com.floriano.legato_api.dto.ConnectionDTO.ConnectionRequestResponseDTO;
import com.floriano.legato_api.dto.UserDTO.UserResponseDTO;
import com.floriano.legato_api.dto.UserDTO.UserUpdateDTO;
import com.floriano.legato_api.mapper.connection.ConnectionRequestMapper;
import com.floriano.legato_api.model.Connection.ConnectionRequest;
import com.floriano.legato_api.model.User.User;
import com.floriano.legato_api.services.UserSevice.useCases.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final SendConnectionRequestService sendConnectionRequestService;
    private final AcceptConnectionRequestService acceptConnectionRequestService;
    private final RejectConnectionRequestService rejectConnectionRequestService;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // CRUD

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

    // FOLLOW

    public UserResponseDTO followUser(Long followerId, Long targetId) {
        return followUserService.execute(followerId, targetId);
    }

    public UserResponseDTO unfollowUser(Long followerId, Long targetId) {
        return unfollowUserService.execute(followerId, targetId);
    }

    // CONEXÕES

    public UserResponseDTO sendConnectionRequest(Long senderId, Long receiverId, String message) {
        return sendConnectionRequestService.execute(senderId, receiverId, message);
    }

    public void acceptConnectionRequest(Long receiverId, Long requestId) {
        acceptConnectionRequestService.execute(receiverId, requestId);
    }

    public void rejectConnectionRequest(Long receiverId, Long requestId) {
        rejectConnectionRequestService.execute(receiverId, requestId);
    }

    public List<ConnectionRequestResponseDTO> listSentRequests(Long userId) {
        User user = findById(userId);

        List<ConnectionRequest> connectionRequests =  user.getSentRequests();

        return connectionRequests.stream()
                .map(ConnectionRequestMapper::toDTO)
                .toList();
    }

    public List<ConnectionRequestResponseDTO> listReceivedRequests(Long userId) {
        User user = findById(userId);
        List<ConnectionRequest> connectionRequests = user.getReceivedRequests();

        return connectionRequests.stream()
                .map(ConnectionRequestMapper::toDTO)
                .toList();
    }
}
