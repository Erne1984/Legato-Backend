package com.floriano.legato_api.services.ColaborationService.useCases;

import com.floriano.legato_api.dto.ColaborationDTO.ColaborationRequestDTO;
import com.floriano.legato_api.dto.ColaborationDTO.ColaborationResponseDTO;
import com.floriano.legato_api.mapper.ColaborationMapper.ColaborationMapper;
import com.floriano.legato_api.model.Colaboration.Colaboration;
import com.floriano.legato_api.model.User.User;
import com.floriano.legato_api.repositories.ColaborationRepository;
import com.floriano.legato_api.repositories.UserRepository;
import com.floriano.legato_api.services.CloudinaryService.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateColaborationService {

    private final ColaborationRepository colaborationRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

    public ColaborationResponseDTO execute(Long id, ColaborationRequestDTO dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Colaboration colab = ColaborationMapper.toEntity(dto);
        colab.setUser(user);

        colab.setAuthor(user.getDisplayName());

        if (dto.imageFile() != null && !dto.imageFile().isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(dto.imageFile(), "colaborations");
            colab.setImageUrl(imageUrl);
        }

        user.getColaborations().add(colab);

        colaborationRepository.save(colab);

        return ColaborationMapper.toResponse(colab);
    }
}

