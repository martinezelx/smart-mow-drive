package com.smartmowdrive.application.service;

import com.smartmowdrive.application.dto.InstructionsRequestDTO;
import com.smartmowdrive.application.dto.PositionResponseDTO;
import com.smartmowdrive.domain.model.InstructionsCommands;
import com.smartmowdrive.domain.model.MowerFinalPosition;
import com.smartmowdrive.domain.service.MowerService;
import com.smartmowdrive.utils.TestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.ConversionService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class MowerInstructionsProcessingServiceTest {

    @Mock
    private MowerService mowerService;

    @Mock
    private ConversionService conversionService;

    private MowerInstructionsProcessingService mowerInstructionsProcessingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mowerInstructionsProcessingService = new MowerInstructionsProcessingService(mowerService, conversionService);
    }

    @Test
    public void testProcessInstructionsService() {
        InstructionsRequestDTO request = TestBuilder.buildInstructionsRequestDTO();
        InstructionsCommands commands = TestBuilder.buildInstructionsCommands();
        List<MowerFinalPosition> finalPositions = TestBuilder.buildMowerFinalPosition();
        List<PositionResponseDTO> responseDTO = TestBuilder.buildPositionResponseDTO();

        when(conversionService.convert(request, InstructionsCommands.class)).thenReturn(commands);
        when(mowerService.executeInstructions(commands)).thenReturn(finalPositions);

        for (int i = 0; i < finalPositions.size(); i++) {
            when(conversionService.convert(finalPositions.get(i), PositionResponseDTO.class)).thenReturn(responseDTO.get(i));
        }

        List<PositionResponseDTO> result = mowerInstructionsProcessingService.processInstructions(request);

        assertEquals(responseDTO, result);
    }

    @Test
    public void testProcessInstructionsService_ConversionServiceThrowsException() {
        InstructionsRequestDTO request = TestBuilder.buildInstructionsRequestDTO();

        when(conversionService.convert(request, InstructionsCommands.class)).thenThrow(new ConversionFailedException(null, null, null, null));

        assertThrows(ConversionFailedException.class, () -> mowerInstructionsProcessingService.processInstructions(request));
    }

    @Test
    public void testProcessInstructions_MowerServiceThrowsException() {
        InstructionsRequestDTO request = TestBuilder.buildInstructionsRequestDTO();
        InstructionsCommands commands = TestBuilder.buildInstructionsCommands();

        when(conversionService.convert(request, InstructionsCommands.class)).thenReturn(commands);
        when(mowerService.executeInstructions(commands)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> mowerInstructionsProcessingService.processInstructions(request));
    }
}