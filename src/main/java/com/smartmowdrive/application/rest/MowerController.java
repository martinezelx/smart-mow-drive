package com.smartmowdrive.application.rest;

import com.smartmowdrive.application.dto.InstructionsRequestDTO;
import com.smartmowdrive.application.dto.PositionResponseDTO;
import com.smartmowdrive.application.service.MowerInstructionsProcessingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Profile("rest")
@RestController
@RequestMapping("/api/v1/mower")
public class MowerController {

    private final MowerInstructionsProcessingService mowerInstructionsProcessingService;

    @PostMapping("/process-instructions")
    @Operation(summary = "Returns the final position of the mowers after processing the instructions.")
    public ResponseEntity<List<PositionResponseDTO>> processInstructions(@Valid @RequestBody InstructionsRequestDTO request) {
        return ResponseEntity.ok(mowerInstructionsProcessingService.processInstructions(request));
    }
}
