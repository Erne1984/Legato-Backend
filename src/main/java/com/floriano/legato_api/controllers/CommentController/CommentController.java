package com.floriano.legato_api.controllers.CommentController;

import com.floriano.legato_api.dto.CommentDTO.CommentRequestDTO;
import com.floriano.legato_api.dto.CommentDTO.CommentResponseDTO;
import com.floriano.legato_api.model.User.UserPrincipal;
import com.floriano.legato_api.payload.ApiResponse;
import com.floriano.legato_api.payload.ResponseFactory;
import com.floriano.legato_api.services.CommentService.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comments")
@Tag(name = "Comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(
            summary = "Criar comentário",
            description = "Permite que o usuário autenticado comente em uma publicação. "
                    + "O ID do criador é obtido automaticamente a partir do token JWT.", security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping()
    public ResponseEntity<ApiResponse<CommentResponseDTO>> createColaboration(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                                                   @RequestBody CommentRequestDTO dto) {
        Long userId = userPrincipal.getUser().getId();
        CommentResponseDTO responseDTO = commentService.createComment(userId, dto);

        return ResponseFactory.ok("Comentário publicado com sucesso!", responseDTO);
    }
}
