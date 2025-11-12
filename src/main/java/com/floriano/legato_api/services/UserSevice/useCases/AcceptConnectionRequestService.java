package com.floriano.legato_api.services.UserSevice.useCases;

import com.floriano.legato_api.exceptions.UserNotFoundException;
import com.floriano.legato_api.model.Connection.ConnectionRequest;
import com.floriano.legato_api.model.User.User;
import com.floriano.legato_api.repositories.ConnectionRequestRepository;
import com.floriano.legato_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcceptConnectionRequestService {

    private final UserRepository userRepository;
    private final ConnectionRequestRepository connectionRequestRepository;

    @Transactional
    public void execute(Long receiverId, Long requestId) {
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));

        ConnectionRequest request = connectionRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));

        receiver.acceptConnectionRequest(request);
        connectionRequestRepository.save(request);
    }
}
