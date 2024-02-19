package com.smartmowdrive.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class InstructionsRequestDTO {

  @NotBlank(message = "Terrain dimensions must not be blank")
  @JsonProperty("terrainDimensions")
  private String terrainDimensions;

  @NotEmpty(message = "List of mowers instructions must not be blank")
  @JsonProperty("mowersInstructions")
  @Valid
  private List<MowerInstructionsDTO> mowersInstructions;
}
