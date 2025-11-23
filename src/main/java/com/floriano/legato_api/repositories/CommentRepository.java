package com.floriano.legato_api.repositories;

import com.floriano.legato_api.model.Comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
