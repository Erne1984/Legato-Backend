package com.floriano.legato_api.controllers.NotificationController;

import com.floriano.legato_api.dto.ColaborationDTO.ColaborationResponseDTO;
import com.floriano.legato_api.dto.NotificationDTO.NotificationResponseDTO;
import com.floriano.legato_api.dto.UserDTO.UserResponseDTO;
import com.floriano.legato_api.dto.UserDTO.UserUpdateDTO;
import com.floriano.legato_api.mapper.NotificationMapper.NotificationMapper;
import com.floriano.legato_api.model.User.UserPrincipal;
import com.floriano.legato_api.payload.ApiResponse;
import com.floriano.legato_api.payload.ResponseFactory;
import com.floriano.legato_api.services.NotificationService.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("notifications")
@Tag(name = "Notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "Get Notifications by userId",
            description = "get Notifications from an existing user by ID",
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<List<NotificationResponseDTO>>> listNotificationsByUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long receiverId = userPrincipal.getUser().getId();
        List<NotificationResponseDTO> notificationResponseDTOS = notificationService.findAllByRecipient(receiverId);
        return ResponseFactory.ok("Notifications retrievied successfully", notificationResponseDTOS);
    }

}
