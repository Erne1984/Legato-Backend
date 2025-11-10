package com.floriano.legato_api.controllers.UserController;

import com.floriano.legato_api.dto.UserDTO.UserResponseDTO;
import com.floriano.legato_api.payload.ApiResponse;
import com.floriano.legato_api.payload.ResponseFactory;
import com.floriano.legato_api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
@Tag(name = "Users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users", description = "Returns the complete list of registered users", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getUsers() {
        List<UserResponseDTO> userResponseDTOList = userService.listAllUsers();
        return ResponseFactory.ok("Lista recuperada com sucesso!", userResponseDTOList);
    }
}
