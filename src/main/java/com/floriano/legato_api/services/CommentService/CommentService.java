package com.floriano.legato_api.services.CommentService;

import com.floriano.legato_api.dto.CommentDTO.CommentRequestDTO;
import com.floriano.legato_api.dto.CommentDTO.CommentResponseDTO;
import com.floriano.legato_api.services.CommentService.useCases.CreateCommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

    private final CreateCommentService createCommentService;



    public CommentResponseDTO createComment(Long userId, CommentRequestDTO dto) {
        return createCommentService.execute(userId, dto);
    }


}
