package com.floriano.legato_api.repositories;

import com.floriano.legato_api.model.Post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
