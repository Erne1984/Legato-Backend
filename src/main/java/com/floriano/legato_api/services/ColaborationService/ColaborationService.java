package com.floriano.legato_api.services.ColaborationService;

import com.floriano.legato_api.dto.ColaborationDTO.ColaborationRequestDTO;
import com.floriano.legato_api.dto.ColaborationDTO.ColaborationResponseDTO;
import com.floriano.legato_api.services.ColaborationService.useCases.CreateColaborationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColaborationService {

    private final CreateColaborationService createColaborationService;

    public ColaborationResponseDTO createColaboration(Long id, ColaborationRequestDTO dto) {
       return createColaborationService.execute(id, dto);
    }

}
