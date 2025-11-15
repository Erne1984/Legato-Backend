package com.floriano.legato_api.controllers.ColaborationController;

import com.floriano.legato_api.dto.ColaborationDTO.ColaborationRequestDTO;
import com.floriano.legato_api.dto.ColaborationDTO.ColaborationResponseDTO;
import com.floriano.legato_api.dto.UserDTO.UserResponseDTO;
import com.floriano.legato_api.model.User.UserPrincipal;
import com.floriano.legato_api.payload.ApiResponse;
import com.floriano.legato_api.payload.ResponseFactory;
import com.floriano.legato_api.services.ColaborationService.ColaborationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("colab")
@Tag(name = "colab")
@RequiredArgsConstructor
public class ColaborationController {

    private final ColaborationService colaborationService;

    @Operation(
            summary = "Criar colaboração",
            description = "Permite que o usuário autenticado crie uma colaboração. "
                    + "O ID do criador é obtido automaticamente a partir do token JWT.", security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping(value = "/colaborations", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ColaborationResponseDTO>> createColaboration(@AuthenticationPrincipal UserPrincipal userPrincipal, @ModelAttribute ColaborationRequestDTO dto) {
        Long userId = userPrincipal.getUser().getId();
        ColaborationResponseDTO responseDTO = colaborationService.createColaboration(userId, dto);

        return ResponseFactory.ok("Colaboração criada com sucesso!", responseDTO);
    }
}
