package com.floriano.legato_api.dto.ColaborationDTO;

import com.floriano.legato_api.model.User.enums.Genre;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public record ColaborationRequestDTO(
        String title,
        String royalties,
        List<Genre> genres,
        boolean remote,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime deadline,
        MultipartFile imageFile
) {}