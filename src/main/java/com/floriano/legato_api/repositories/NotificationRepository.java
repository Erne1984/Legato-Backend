package com.floriano.legato_api.repositories;

import com.floriano.legato_api.model.Notification.Notification;
import com.floriano.legato_api.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByRecipientOrderByCreatedAtDesc(User recipient);
}
