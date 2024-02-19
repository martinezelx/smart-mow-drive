package com.smartmowdrive.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.smartmowdrive.domain.model.InstructionsCommands;
import com.smartmowdrive.domain.model.MowerFinalPosition;
import com.smartmowdrive.domain.service.MowerService;
import com.smartmowdrive.infrastructure.rest.dto.InstructionsRequestDTO;
import com.smartmowdrive.infrastructure.rest.dto.PositionResponseDTO;
import com.smartmowdrive.utils.TestBuilder;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.ConversionService;

public class MowerInstructionsProcessingServiceImplTest {

  @Mock private MowerService mowerService;

  @Mock private ConversionService conversionService;

  private MowerInstructionsProcessingServiceImpl mowerInstructionsProcessingServiceImpl;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mowerInstructionsProcessingServiceImpl =
        new MowerInstructionsProcessingServiceImpl(mowerService, conversionService);
  }

  @Test
  public void processInstructionsReturnsExpectedPositionsWhenValidInstructionsProvided() {
    InstructionsRequestDTO request = TestBuilder.buildInstructionsRequestDTO();
    InstructionsCommands commands = TestBuilder.buildInstructionsCommands();
    List<MowerFinalPosition> finalPositions = TestBuilder.buildMowerFinalPosition();
    List<PositionResponseDTO> responseDTO = TestBuilder.buildPositionResponseDTO();

    when(conversionService.convert(request, InstructionsCommands.class)).thenReturn(commands);
    when(mowerService.executeInstructions(commands)).thenReturn(finalPositions);

    IntStream.range(0, finalPositions.size())
        .forEach(
            i ->
                when(conversionService.convert(finalPositions.get(i), PositionResponseDTO.class))
                    .thenReturn(responseDTO.get(i)));

    List<PositionResponseDTO> result =
        mowerInstructionsProcessingServiceImpl.processInstructions(request);

    assertEquals(responseDTO, result);
  }

  @Test
  public void processInstructionsThrowsConversionFailedExceptionWhenConversionFails() {
    InstructionsRequestDTO request = TestBuilder.buildInstructionsRequestDTO();

    when(conversionService.convert(request, InstructionsCommands.class))
        .thenThrow(new ConversionFailedException(null, null, null, null));

    assertThrows(
        ConversionFailedException.class,
        () -> mowerInstructionsProcessingServiceImpl.processInstructions(request));
  }

  @Test
  public void processInstructionsThrowsRuntimeExceptionWhenMowerServiceFails() {
    InstructionsRequestDTO request = TestBuilder.buildInstructionsRequestDTO();
    InstructionsCommands commands = TestBuilder.buildInstructionsCommands();

    when(conversionService.convert(request, InstructionsCommands.class)).thenReturn(commands);
    when(mowerService.executeInstructions(commands)).thenThrow(new RuntimeException());

    assertThrows(
        RuntimeException.class,
        () -> mowerInstructionsProcessingServiceImpl.processInstructions(request));
  }
}
