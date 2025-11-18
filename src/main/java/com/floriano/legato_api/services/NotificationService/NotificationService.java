package com.floriano.legato_api.services.NotificationService;

import com.floriano.legato_api.dto.NotificationDTO.NotificationRequestDTO;
import com.floriano.legato_api.dto.NotificationDTO.NotificationResponseDTO;
import com.floriano.legato_api.model.Notification.Notification;
import com.floriano.legato_api.model.User.User;
import com.floriano.legato_api.repositories.NotificationRepository;
import com.floriano.legato_api.services.ColaborationService.useCases.CreateColaborationService;
import com.floriano.legato_api.services.NotificationService.userCases.CreateNotificationService;
import com.floriano.legato_api.services.NotificationService.userCases.ListNotificationsByRecipientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public NotificationResponseDTO createNotification(Long idUser, NotificationRequestDTO dto) {
        return createNotificationService.execute(idUser, dto);
    }
}
