package com.smartmowdrive.application.rest;

import com.smartmowdrive.application.dto.InstructionsRequestDTO;
import com.smartmowdrive.application.dto.PositionResponseDTO;
import com.smartmowdrive.application.service.MowerInstructionsProcessingService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Profile("rest")
@RestController
@RequestMapping("/api/v1/mower")
public class MowerController {

    private final MowerInstructionsProcessingService mowerInstructionsProcessingService;

    @PostMapping("/process-instructions")
    @Operation(summary = "Returns the final position of the mowers after processing the instructions.")
    @Timed(value = "mower.process.instructions", description = "Time taken to process mower instructions")
    public ResponseEntity<List<PositionResponseDTO>> processInstructions(@Valid @RequestBody InstructionsRequestDTO request) {
        log.info("Processing instructions for request: {}", request);
        var result = ResponseEntity.ok(mowerInstructionsProcessingService.processInstructions(request));
        log.info("Finished processing instructions: {}", result);
        return result;
    }
}
