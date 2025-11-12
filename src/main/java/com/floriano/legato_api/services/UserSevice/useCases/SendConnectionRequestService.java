package com.floriano.legato_api.services.UserSevice.useCases;

import com.floriano.legato_api.dto.UserDTO.UserResponseDTO;
import com.floriano.legato_api.exceptions.UserNotFoundException;
import com.floriano.legato_api.mapper.user.UserMapper;
import com.floriano.legato_api.model.Connection.ConnectionRequest;
import com.floriano.legato_api.model.User.User;
import com.floriano.legato_api.repositories.ConnectionRequestRepository;
import com.floriano.legato_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendConnectionRequestService {

    private final UserRepository userRepository;
    private final ConnectionRequestRepository connectionRequestRepository;

    @Transactional
    public UserResponseDTO execute(Long senderId, Long receiverId, String message) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new UserNotFoundException("Usuário remetente não encontrado"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new UserNotFoundException("Usuário destinatário não encontrado"));

        boolean alreadyPending = connectionRequestRepository.existsBySenderAndReceiverAndStatusPending(sender, receiver);
        if (alreadyPending)
            throw new IllegalArgumentException("Já existe um pedido pendente entre esses usuários.");

        ConnectionRequest request = sender.sendConnectionRequest(receiver, message);
        connectionRequestRepository.save(request);

        return UserMapper.toDTO(receiver);
    }
}