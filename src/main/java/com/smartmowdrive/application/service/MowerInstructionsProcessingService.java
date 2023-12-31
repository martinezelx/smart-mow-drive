package com.smartmowdrive.application.service;

import com.smartmowdrive.application.dto.InstructionsRequestDTO;
import com.smartmowdrive.application.dto.PositionResponseDTO;
import com.smartmowdrive.domain.model.InstructionsCommands;
import com.smartmowdrive.domain.service.MowerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class MowerInstructionsProcessingService {

    private final MowerService mowerService;
    private final ConversionService conversionService;

    public List<PositionResponseDTO> processInstructions(InstructionsRequestDTO request) {
        var formattedRequest = conversionService.convert(request, InstructionsCommands.class);
        log.info("Formatted request: {}", formattedRequest);
        return mowerService.executeInstructions(Objects.requireNonNull(formattedRequest)).stream()
                .map(mowerFinalPosition -> conversionService.convert(mowerFinalPosition, PositionResponseDTO.class))
                .collect(Collectors.toList());
    }
}
