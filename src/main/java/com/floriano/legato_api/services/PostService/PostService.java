package com.floriano.legato_api.services.PostService;

import com.floriano.legato_api.dto.PostDTO.PostResponseDTO;
import com.floriano.legato_api.services.PostService.UseCases.CreatePostService;
import com.floriano.legato_api.services.PostService.UseCases.ListAllPostsByUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private final CreatePostService createPostService;
    private final ListAllPostsByUserService listAllPostsByUserService;

    public PostResponseDTO createPost(Long userId, String dto, MultipartFile mediaFile) {
        return  createPostService.execute(userId, dto, mediaFile);
    }

    public List<PostResponseDTO> listPosts(long userId) {
        return listAllPostsByUserService.execute(userId);
    }
}
