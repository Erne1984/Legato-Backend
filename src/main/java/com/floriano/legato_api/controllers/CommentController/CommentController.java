package com.floriano.legato_api.controllers.CommentController;

import com.floriano.legato_api.dto.ColaborationDTO.ColaborationResponseDTO;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comments")
@Tag(name = "Comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(
            summary = "Listar comentários do usuário em uma publicação",
            description = "Permite que o usuário autenticado visualize seus comentários em uma publicação. "
                    + "O ID do usuário é obtido automaticamente a partir do token JWT.", security = { @SecurityRequirement(name = "bearerAuth") })
    @GetMapping("/user")
    public ResponseEntity<ApiResponse<List<CommentResponseDTO>>> listColaborationUsers(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getUser().getId();
        List<CommentResponseDTO> responseDTO = commentService.listUserComments(userId);

        return ResponseFactory.ok("Colaborações recuperadas com sucesso!", responseDTO);
    }

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
