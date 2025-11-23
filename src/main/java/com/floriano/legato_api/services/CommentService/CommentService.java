package com.floriano.legato_api.services.CommentService;

import com.floriano.legato_api.dto.CommentDTO.CommentRequestDTO;
import com.floriano.legato_api.dto.CommentDTO.CommentResponseDTO;
import com.floriano.legato_api.services.CommentService.useCases.CreateCommentService;
import com.floriano.legato_api.services.CommentService.useCases.ListAllCommentByPostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final CreateCommentService createCommentService;
    private final ListAllCommentByPostService listAllCommentByPostService;


    public CommentResponseDTO createComment(Long userId, CommentRequestDTO dto) {
        return createCommentService.execute(userId, dto);
    }

    public List<CommentResponseDTO> listUserComments(Long userId) {
        return listAllCommentByPostService.execute(userId);
    }


}
