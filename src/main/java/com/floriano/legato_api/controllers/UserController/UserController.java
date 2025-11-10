package com.floriano.legato_api.controllers.UserController;

import com.floriano.legato_api.dto.UserDTO.UserRequestDTO;
import com.floriano.legato_api.dto.UserDTO.UserResponseDTO;
import com.floriano.legato_api.dto.UserDTO.UserUpdateDTO;
import com.floriano.legato_api.model.User.User;
import com.floriano.legato_api.payload.ApiResponse;
import com.floriano.legato_api.payload.ResponseFactory;
import com.floriano.legato_api.services.UserSevice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Update user", description = "Updates an existing user by ID", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(@PathVariable Long id,  @RequestBody UserUpdateDTO dto ) {
        UserResponseDTO responseDTO = userService.updateUser(id, dto);
        return ResponseFactory.ok("User updated successfully", responseDTO);
    }

    @Operation(summary = "Delete user", description = "Deletes a user by ID and cleans up related entities", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseFactory.ok("Usuário deletado com sucesso!");
    }

    @Operation(
            summary = "Seguir um usuário",
            description = "Permite que o usuário autenticado siga outro usuário. "
                    + "Requer autenticação via Bearer Token. ", security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @PostMapping("/follow/{targetId}")
    public ResponseEntity<UserResponseDTO> followUser(@AuthenticationPrincipal User authenticatedUser, @PathVariable Long targetId) {
        Long followerId = authenticatedUser.getId();
        UserResponseDTO followed = userService.followUser(followerId, targetId);
        return ResponseEntity.ok(followed);
    }

    @Operation(
            summary = "Deixar de seguir um usuário",
            description = "Permite que o usuário autenticado deixe de seguir outro usuário. "
                    + "O ID do seguidor é obtido automaticamente a partir do token JWT.", security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping("/unfollow/{targetId}")
    public ResponseEntity<UserResponseDTO> unfollowUser(@AuthenticationPrincipal User authenticatedUser, @PathVariable Long targetId) {
        Long followerId = authenticatedUser.getId();
        UserResponseDTO unfollowed = userService.unfollowUser(followerId, targetId);
        return ResponseEntity.ok(unfollowed);
    }
}
