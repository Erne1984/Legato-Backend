package com.floriano.legato_api.domain.user;

public record RegisterDto(String email, String password, UserRole role) {
}
