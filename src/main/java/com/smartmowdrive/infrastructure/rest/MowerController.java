package com.smartmowdrive.infrastructure.rest;

import com.smartmowdrive.application.usecases.ProcessMowerInstructions;
import com.smartmowdrive.infrastructure.rest.dto.InstructionsRequestDTO;
import com.smartmowdrive.infrastructure.rest.dto.PositionResponseDTO;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@Profile("rest")
@RestController
@RequestMapping("/api/v1/mower")
public class MowerController {

  private final ProcessMowerInstructions processMowerInstructions;

  @PostMapping("/process-instructions")
  @Operation(
      summary = "Returns the final position of the mowers after processing the instructions.")
  @Timed(
      value = "mower.process.instructions",
      description = "Time taken to process mower instructions")
  public ResponseEntity<List<PositionResponseDTO>> processInstructions(
      @Valid @RequestBody InstructionsRequestDTO request) {
    log.info("Processing instructions for request: {}", request);
    var result = ResponseEntity.ok(processMowerInstructions.processInstructions(request));
    log.info("Finished processing instructions: {}", result);
    return result;
  }
}
