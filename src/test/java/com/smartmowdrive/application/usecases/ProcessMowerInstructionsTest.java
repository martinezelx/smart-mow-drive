package com.smartmowdrive.application.usecases;

import static org.junit.jupiter.api.Assertions.*;

import com.smartmowdrive.application.exceptions.*;
import com.smartmowdrive.domain.commons.exceptions.InvalidInstructionException;
import com.smartmowdrive.domain.commons.exceptions.InvalidPositionException;
import com.smartmowdrive.infrastructure.rest.dto.InstructionsRequestDTO;
import com.smartmowdrive.infrastructure.rest.dto.PositionResponseDTO;
import com.smartmowdrive.utils.TestBuilder;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProcessMowerInstructionsTest {

  @Autowired private ProcessMowerInstructions service;

  @Test
  void executeInstructionsReturnsExpectedPositionsWhenValidInstructionsProvided() {
    InstructionsRequestDTO request = TestBuilder.buildInstructionsRequestDTO();
    List<PositionResponseDTO> expected = TestBuilder.buildPositionResponseDTO();

    List<PositionResponseDTO> response = service.processInstructions(request);

    assertEquals(expected, response);
  }

  @Test
  void executeInstructionsReturnsExpectedPositionsWithErrorsWhenInvalidInstructionsProvided() {
    InstructionsRequestDTO request = TestBuilder.buildInstructionsRequestDTOWithErrors();
    List<PositionResponseDTO> expected = TestBuilder.buildPositionResponseDTOWithErrors();

    List<PositionResponseDTO> response = service.processInstructions(request);

    assertEquals(expected, response);
  }

  @Test
  void
      executeInstructionsThrowsDuplicateMowerPositionExceptionWhenDuplicateMowerPositionProvided() {
    InstructionsRequestDTO request =
        TestBuilder.buildInstructionsRequestDTODuplicateMowerPosition();
    assertThrows(DuplicateMowerPositionException.class, () -> service.processInstructions(request));
  }

  @Test
  void executeInstructionsThrowsInvalidDimensionsExceptionWhenInvalidDimensionsProvided() {
    InstructionsRequestDTO request = TestBuilder.buildInstructionsRequestDTOInvalidDimensions();
    assertThrows(InvalidDimensionsException.class, () -> service.processInstructions(request));
  }

  @Test
  void executeInstructionsThrowsDuplicateMowerIdExceptionWhenDuplicateMowerIdProvided() {
    InstructionsRequestDTO request = TestBuilder.buildInstructionsRequestDTODuplicateMowerId();
    assertThrows(DuplicateMowerIdException.class, () -> service.processInstructions(request));
  }

  @Test
  void executeInstructionsThrowsInvalidPositionExceptionWhenInvalidPositionProvided() {
    InstructionsRequestDTO request = TestBuilder.buildInstructionsRequestDTOInvalidPosition();
    assertThrows(InvalidPositionException.class, () -> service.processInstructions(request));
  }

  @Test
  void executeInstructionsThrowsInvalidInstructionExceptionWhenInvalidInstructionProvided() {
    InstructionsRequestDTO request =
        TestBuilder.buildInstructionsRequestDTOCommandsInvalidInstruction();
    assertThrows(InvalidInstructionException.class, () -> service.processInstructions(request));
  }
}
