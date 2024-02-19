package com.smartmowdrive.application.usecases;

import com.smartmowdrive.infrastructure.rest.dto.InstructionsRequestDTO;
import com.smartmowdrive.infrastructure.rest.dto.PositionResponseDTO;
import java.util.List;

public interface ProcessMowerInstructions {
  List<PositionResponseDTO> processInstructions(InstructionsRequestDTO request);
}
