package com.floriano.legato_api.model.Post;

import com.floriano.legato_api.model.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "posts")
@Entity(name = "Post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private TypeMedia typeMedia;

    // Link Cloudinary
    private String urlMedia;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // METODOS AUXILIARES

    public void assignUser(User newUser) {
        if (newUser == null) return;
        if (this.user != null && this.user.getPosts() != null) {
            this.user.getPosts().remove(this);
        }

        this.user = newUser;

        if (!newUser.getPosts().contains(this)) {
            newUser.getPosts().add(this);
        }
    }

    public void detachUser() {
        if (this.user == null) return;

        this.user.getPosts().remove(this);
        this.user = null;
    }
}
