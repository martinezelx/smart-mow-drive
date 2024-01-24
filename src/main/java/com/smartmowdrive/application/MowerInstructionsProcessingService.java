package com.smartmowdrive.application;

import com.smartmowdrive.infrastructure.rest.dto.InstructionsRequestDTO;
import com.smartmowdrive.infrastructure.rest.dto.PositionResponseDTO;

import java.util.List;

public interface MowerInstructionsProcessingService {
    List<PositionResponseDTO> processInstructions(InstructionsRequestDTO request);
}