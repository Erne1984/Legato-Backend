package com.floriano.legato_api.model.Post;

import com.floriano.legato_api.model.User.User;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "posts")
@Entity(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String content;

    private TypeMedia type;

    private String urlMedia;
}
