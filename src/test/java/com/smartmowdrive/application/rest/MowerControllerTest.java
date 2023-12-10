package com.smartmowdrive.application.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartmowdrive.application.dto.InstructionsRequestDTO;
import com.smartmowdrive.application.service.MowerInstructionsProcessingService;
import com.smartmowdrive.domain.constants.ErrorMessages;
import com.smartmowdrive.domain.exception.*;
import com.smartmowdrive.utils.TestBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MowerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MowerInstructionsProcessingService mowerInstructionsProcessingService;

    @Test
    public void testProcessInstructionsController() throws Exception {
        mockMvc.perform(post("/api/v1/mower/process-instructions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestBuilder.buildInstructionsRequestDTO())))
                .andExpect(status().isOk());
    }

    @Test
    public void testProcessInstructionsController_DuplicateMowerPositionException() throws Exception {
        String message = ErrorMessages.DUPLICATE_MOWER_POSITION;
        when(mowerInstructionsProcessingService.processInstructions(TestBuilder.buildInstructionsRequestDTO()))
                .thenThrow(new DuplicateMowerPositionException(message));

        String expectedResponse = "{\"exception\":\"DuplicateMowerPositionException\",\"status\":\"BAD_REQUEST\",\"message\":\"" + message + "\"}";

        mockMvc.perform(post("/api/v1/mower/process-instructions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestBuilder.buildInstructionsRequestDTO())))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    public void testProcessInstructionsController_DuplicateMowerIdException() throws Exception {
        String message = ErrorMessages.DUPLICATE_MOWER_ID;
        when(mowerInstructionsProcessingService.processInstructions(TestBuilder.buildInstructionsRequestDTO()))
                .thenThrow(new DuplicateMowerIdException(message));

        String expectedResponse = "{\"exception\":\"DuplicateMowerIdException\",\"status\":\"BAD_REQUEST\",\"message\":\"" + message + "\"}";

        mockMvc.perform(post("/api/v1/mower/process-instructions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestBuilder.buildInstructionsRequestDTO())))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    public void testProcessInstructionsController_OutOfBoundsException() throws Exception {
        String message = ErrorMessages.OUT_OF_BOUNDS;
        when(mowerInstructionsProcessingService.processInstructions(TestBuilder.buildInstructionsRequestDTO()))
                .thenThrow(new OutOfBoundsException(message));

        String expectedResponse = "{\"exception\":\"OutOfBoundsException\",\"status\":\"BAD_REQUEST\",\"message\":\"" + message + "\"}";

        mockMvc.perform(post("/api/v1/mower/process-instructions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestBuilder.buildInstructionsRequestDTO())))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    public void testProcessInstructionsController_InvalidDimensionsException() throws Exception {
        String message = ErrorMessages.INVALID_TERRAIN_FORMAT;
        when(mowerInstructionsProcessingService.processInstructions(TestBuilder.buildInstructionsRequestDTO()))
                .thenThrow(new InvalidDimensionsException(message));

        String expectedResponse = "{\"exception\":\"InvalidDimensionsException\",\"status\":\"BAD_REQUEST\",\"message\":\"" + message + "\"}";

        mockMvc.perform(post("/api/v1/mower/process-instructions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestBuilder.buildInstructionsRequestDTO())))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    public void testProcessInstructionsController_InvalidInstructionsException() throws Exception {
        String message = ErrorMessages.INVALID_INSTRUCTIONS_FORMAT;
        when(mowerInstructionsProcessingService.processInstructions(TestBuilder.buildInstructionsRequestDTO()))
                .thenThrow(new InvalidInstructionException(message));

        String expectedResponse = "{\"exception\":\"InvalidInstructionException\",\"status\":\"BAD_REQUEST\",\"message\":\"" + message + "\"}";

        mockMvc.perform(post("/api/v1/mower/process-instructions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestBuilder.buildInstructionsRequestDTO())))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    public void testProcessInstructionsController_InvalidPositionException() throws Exception {
        String message = ErrorMessages.INVALID_POSITION_FORMAT;
        when(mowerInstructionsProcessingService.processInstructions(TestBuilder.buildInstructionsRequestDTO()))
                .thenThrow(new InvalidPositionException(message));

        String expectedResponse = "{\"exception\":\"InvalidPositionException\",\"status\":\"BAD_REQUEST\",\"message\":\"" + message + "\"}";

        mockMvc.perform(post("/api/v1/mower/process-instructions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestBuilder.buildInstructionsRequestDTO())))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    public void testProcessInstructionsController_MethodArgumentNotValidException() throws Exception {
        InstructionsRequestDTO invalidRequest = TestBuilder.buildInstructionsRequestDTO();
        invalidRequest.setTerrainDimensions(null);

        mockMvc.perform(post("/api/v1/mower/process-instructions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"exception\":\"MethodArgumentNotValidException\",\"status\":\"BAD_REQUEST\",\"message\":\"{terrainDimensions=Terrain dimensions must not be blank}\"}"));
    }

    @Test
    public void testProcessInstructionsController_HttpMessageNotReadableException() throws Exception {
        String invalidJson = "{invalidJson}";

        mockMvc.perform(post("/api/v1/mower/process-instructions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}