package com.floriano.legato_api.dto.NotificationDTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDTO {

    private Long id;

    private Long senderId;
    private Long recipientId;

    private String message;
    private boolean read;

    private LocalDateTime createdAt;
}
