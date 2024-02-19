package com.smartmowdrive.infrastructure.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartmowdrive.application.exceptions.*;
import com.smartmowdrive.application.usecases.ProcessMowerInstructions;
import com.smartmowdrive.domain.commons.exceptions.InvalidInstructionException;
import com.smartmowdrive.domain.commons.exceptions.InvalidPositionException;
import com.smartmowdrive.domain.commons.exceptions.OutOfBoundsException;
import com.smartmowdrive.domain.commons.utils.ErrorMessages;
import com.smartmowdrive.infrastructure.rest.dto.InstructionsRequestDTO;
import com.smartmowdrive.utils.TestBuilder;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MowerControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private ProcessMowerInstructions processMowerInstructions;

  @Test
  void shouldReturnOkWhenProcessInstructions() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/mower/process-instructions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(TestBuilder.buildInstructionsRequestDTO())))
        .andExpect(status().isOk());
  }

  @Test
  void shouldReturnBadRequestWhenDuplicateMowerPosition() throws Exception {
    String message = ErrorMessages.DUPLICATE_MOWER_POSITION;
    when(processMowerInstructions.processInstructions(TestBuilder.buildInstructionsRequestDTO()))
        .thenThrow(new DuplicateMowerPositionException(message));

    String expectedResponse =
        "{\"exception\":\"DuplicateMowerPositionException\",\"status\":\"BAD_REQUEST\",\"message\":\""
            + message
            + "\"}";

    mockMvc
        .perform(
            post("/api/v1/mower/process-instructions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(TestBuilder.buildInstructionsRequestDTO())))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(expectedResponse));
  }

  @Test
  void shouldReturnBadRequestWhenDuplicateMowerId() throws Exception {
    String message = ErrorMessages.DUPLICATE_MOWER_ID;
    when(processMowerInstructions.processInstructions(TestBuilder.buildInstructionsRequestDTO()))
        .thenThrow(new DuplicateMowerIdException(message));

    String expectedResponse =
        "{\"exception\":\"DuplicateMowerIdException\",\"status\":\"BAD_REQUEST\",\"message\":\""
            + message
            + "\"}";

    mockMvc
        .perform(
            post("/api/v1/mower/process-instructions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(TestBuilder.buildInstructionsRequestDTO())))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(expectedResponse));
  }

  @Test
  void shouldReturnBadRequestWhenOutOfBounds() throws Exception {
    String message = ErrorMessages.OUT_OF_BOUNDS;
    when(processMowerInstructions.processInstructions(TestBuilder.buildInstructionsRequestDTO()))
        .thenThrow(new OutOfBoundsException(message));

    String expectedResponse =
        "{\"exception\":\"OutOfBoundsException\",\"status\":\"BAD_REQUEST\",\"message\":\""
            + message
            + "\"}";

    mockMvc
        .perform(
            post("/api/v1/mower/process-instructions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(TestBuilder.buildInstructionsRequestDTO())))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(expectedResponse));
  }

  @Test
  void shouldReturnBadRequestWhenInvalidDimensions() throws Exception {
    String message = ErrorMessages.INVALID_TERRAIN_FORMAT;
    when(processMowerInstructions.processInstructions(TestBuilder.buildInstructionsRequestDTO()))
        .thenThrow(new InvalidDimensionsException(message));

    String expectedResponse =
        "{\"exception\":\"InvalidDimensionsException\",\"status\":\"BAD_REQUEST\",\"message\":\""
            + message
            + "\"}";

    mockMvc
        .perform(
            post("/api/v1/mower/process-instructions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(TestBuilder.buildInstructionsRequestDTO())))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(expectedResponse));
  }

  @Test
  void shouldReturnBadRequestWhenInvalidInstructions() throws Exception {
    String message = ErrorMessages.INVALID_INSTRUCTIONS_FORMAT;
    when(processMowerInstructions.processInstructions(TestBuilder.buildInstructionsRequestDTO()))
        .thenThrow(new InvalidInstructionException(message));

    String expectedResponse =
        "{\"exception\":\"InvalidInstructionException\",\"status\":\"BAD_REQUEST\",\"message\":\""
            + message
            + "\"}";

    mockMvc
        .perform(
            post("/api/v1/mower/process-instructions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(TestBuilder.buildInstructionsRequestDTO())))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(expectedResponse));
  }

  @Test
  void shouldReturnBadRequestWhenInvalidPosition() throws Exception {
    String message = ErrorMessages.INVALID_POSITION_FORMAT;
    when(processMowerInstructions.processInstructions(TestBuilder.buildInstructionsRequestDTO()))
        .thenThrow(new InvalidPositionException(message));

    String expectedResponse =
        "{\"exception\":\"InvalidPositionException\",\"status\":\"BAD_REQUEST\",\"message\":\""
            + message
            + "\"}";

    mockMvc
        .perform(
            post("/api/v1/mower/process-instructions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(TestBuilder.buildInstructionsRequestDTO())))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(expectedResponse));
  }

  @Test
  void shouldReturnBadRequestWhenTerrainDimensionsNull() throws Exception {
    InstructionsRequestDTO invalidRequest = TestBuilder.buildInstructionsRequestDTO();
    invalidRequest.setTerrainDimensions(null);

    mockMvc
        .perform(
            post("/api/v1/mower/process-instructions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(
            content()
                .string(
                    "{\"exception\":\"MethodArgumentNotValidException\",\"status\":\"BAD_REQUEST\",\"message\":\"{terrainDimensions=Terrain dimensions must not be blank}\"}"));
  }

  @Test
  void shouldReturnBadRequestWhenMowersInstructionsEmpty() throws Exception {
    InstructionsRequestDTO invalidRequest = TestBuilder.buildInstructionsRequestDTO();
    invalidRequest.setMowersInstructions(List.of());

    mockMvc
        .perform(
            post("/api/v1/mower/process-instructions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(
            content()
                .string(
                    "{\"exception\":\"MethodArgumentNotValidException\",\"status\":\"BAD_REQUEST\",\"message\":\"{mowersInstructions=List of mowers instructions must not be blank}\"}"));
  }

  @Test
  void shouldReturnBadRequestWhenInvalidJson() throws Exception {
    String invalidJson = "{invalidJson}";

    mockMvc
        .perform(
            post("/api/v1/mower/process-instructions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
        .andExpect(status().isBadRequest());
  }
}
