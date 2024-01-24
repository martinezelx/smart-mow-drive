package com.smartmowdrive.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class MowerInstructionsDTO {

    @NotNull(message = "Id must not be blank")
    @JsonProperty("id")
    private UUID id;

    @NotBlank(message = "Starting position must not be blank")
    @JsonProperty("startingPosition")
    private String startingPosition;

    @NotBlank(message = "Instructions must not be blank")
    @JsonProperty("instructions")
    private String instructions;
}
