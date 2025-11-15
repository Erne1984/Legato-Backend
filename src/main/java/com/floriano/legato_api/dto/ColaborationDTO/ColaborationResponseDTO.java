package com.floriano.legato_api.dto.ColaborationDTO;


import java.time.LocalDateTime;
import java.util.List;

public record ColaborationResponseDTO(
        Long id,
        String title,
        String author,
        String royalties,
        List<String> genres,
        boolean remote,
        LocalDateTime deadline,
        String imageUrl,
        LocalDateTime createdAt,
        String timeAgo,
        Long userId
) {}
