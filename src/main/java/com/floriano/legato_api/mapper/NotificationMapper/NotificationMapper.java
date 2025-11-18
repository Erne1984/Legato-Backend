package com.floriano.legato_api.mapper.NotificationMapper;

import com.floriano.legato_api.dto.NotificationDTO.NotificationRequestDTO;
import com.floriano.legato_api.dto.NotificationDTO.NotificationResponseDTO;
import com.floriano.legato_api.model.Notification.Notification;
import com.floriano.legato_api.model.User.User;

public class NotificationMapper {

    public static Notification toEntity(NotificationRequestDTO dto, User sender, User recipient) {
        Notification n = new Notification();

        n.setId(dto.getId());
        n.setSender(sender);
        n.setRecipient(recipient);

        n.setMessage(dto.getMessage());
        n.setRead(dto.isRead());
        n.setCreatedAt(dto.getCreatedAt());

        return n;
    }

    public static NotificationResponseDTO toResponseDTO(Notification notification) {

        NotificationResponseDTO dto = new NotificationResponseDTO();

        dto.setId(notification.getId());

        dto.setSenderName(notification.getSender() != null ? notification.getSender().getUsername() : null);
        dto.setRecipientName(notification.getRecipient() != null ? notification.getRecipient().getUsername() : null);

        dto.setMessage(notification.getMessage());
        dto.setRead(notification.isRead());

        dto.setTimeAgo(notification.getTimeAgo());

        return dto;
    }
}
