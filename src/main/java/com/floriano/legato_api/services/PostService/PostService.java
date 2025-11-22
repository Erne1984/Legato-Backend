package com.floriano.legato_api.services.PostService;

import com.floriano.legato_api.dto.PostDTO.PostResponseDTO;
import com.floriano.legato_api.services.PostService.UseCases.CreatePostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class PostService {

    private final CreatePostService createPostService;

    public PostResponseDTO createPost(Long userId, String dto, MultipartFile mediaFile) {
        return  createPostService.execute(userId, dto, mediaFile);
    }


}
