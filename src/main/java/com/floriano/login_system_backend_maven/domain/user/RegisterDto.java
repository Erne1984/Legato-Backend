package com.floriano.login_system_backend_maven.domain.user;

public record RegisterDto(String email, String password, UserRole role) {
}
