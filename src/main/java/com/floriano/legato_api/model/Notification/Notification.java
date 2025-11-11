package com.floriano.legato_api.model.Notification;

import com.floriano.legato_api.model.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "notifications")
@Entity(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

    private String message; // texto resumido
    private String type;    // FOLLOW, LIKE, COMMENT, CONNECTION
    private boolean read;
    private LocalDateTime createdAt;
}
