package com.floriano.legato_api.services.NotificationService;

import com.floriano.legato_api.model.Notification.Notification;
import com.floriano.legato_api.model.User.User;
import com.floriano.legato_api.repositories.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional(readOnly = true)
    public List<Notification> findAllByRecipient(User recipient) {
        return notificationRepository.findAllByRecipientOrderByCreatedAtDesc(recipient);
    }

    @Transactional
    public Notification createNotification(User sender, User recipient, String message, String type) {
        Notification notification = new Notification();
        notification.setSender(sender);
        notification.setRecipient(recipient);
        notification.setMessage(message);
        notification.setType(type);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }
}
