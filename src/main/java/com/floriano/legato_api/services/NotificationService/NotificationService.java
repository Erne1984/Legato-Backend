package com.floriano.legato_api.services.NotificationService;

import com.floriano.legato_api.dto.NotificationDTO.NotificationRequestDTO;
import com.floriano.legato_api.dto.NotificationDTO.NotificationResponseDTO;
import com.floriano.legato_api.repositories.NotificationRepository;
import com.floriano.legato_api.services.NotificationService.useCases.CreateNotificationService;
import com.floriano.legato_api.services.NotificationService.useCases.ListNotificationsByRecipientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final CreateNotificationService createNotificationService;
    private final ListNotificationsByRecipientService listNotificationsByRecipientService;

    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> findAllByRecipient(Long recipientId) {
        return listNotificationsByRecipientService.execute(recipientId);
    }

    @Transactional
    public NotificationResponseDTO createNotification(NotificationRequestDTO dto) {
        return createNotificationService.execute(dto);
    }
}
