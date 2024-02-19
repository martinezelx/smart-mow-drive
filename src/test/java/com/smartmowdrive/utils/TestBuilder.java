package com.smartmowdrive.utils;

import com.smartmowdrive.domain.model.InstructionsCommands;
import com.smartmowdrive.domain.model.MowerFinalPosition;
import com.smartmowdrive.domain.model.MowerInstructions;
import com.smartmowdrive.infrastructure.rest.dto.InstructionsRequestDTO;
import com.smartmowdrive.infrastructure.rest.dto.MowerInstructionsDTO;
import com.smartmowdrive.infrastructure.rest.dto.PositionResponseDTO;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TestBuilder {
  public static InstructionsRequestDTO buildInstructionsRequestDTO() {
    MowerInstructionsDTO mowerInstructionsDTO1 =
        MowerInstructionsDTO.builder()
            .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa1"))
            .startingPosition("1 2 N")
            .instructions("LMLMLMLMM")
            .build();

    MowerInstructionsDTO mowerInstructionsDTO2 =
        MowerInstructionsDTO.builder()
            .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa2"))
            .startingPosition("3 3 E")
            .instructions("MMRMMRMRRM")
            .build();

    List<MowerInstructionsDTO> mowersInstructions =
        Arrays.asList(mowerInstructionsDTO1, mowerInstructionsDTO2);

    return InstructionsRequestDTO.builder()
        .terrainDimensions("5 5")
        .mowersInstructions(mowersInstructions)
        .build();
  }

  public static InstructionsCommands buildInstructionsCommands() {
    MowerInstructions mowerInstructions1 =
        new MowerInstructions(
            UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa1"), "1 2 N", "LMLMLMLMM");
    MowerInstructions mowerInstructions2 =
        new MowerInstructions(
            UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa2"), "3 3 E", "MMRMMRMRRM");
    List<MowerInstructions> mowersInstructions =
        Arrays.asList(mowerInstructions1, mowerInstructions2);
    return new InstructionsCommands("5 5", mowersInstructions);
  }

  public static InstructionsCommands buildInstructionsCommandsWithErrors() {
    MowerInstructions mowerInstructions1 =
        new MowerInstructions(
            UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa1"), "1 2 N", "LMLMLMLMM");
    MowerInstructions mowerInstructions2 =
        new MowerInstructions(
            UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa2"), "3 3 E", "MMRMMRMRRM");
    MowerInstructions mowerInstructions3 =
        new MowerInstructions(
            UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa3"), "1 4 N", "LMLMLMLMM");
    List<MowerInstructions> mowersInstructions =
        Arrays.asList(mowerInstructions1, mowerInstructions2, mowerInstructions3);
    return new InstructionsCommands("5 5", mowersInstructions);
  }

  public static List<MowerFinalPosition> buildMowerFinalPosition() {
    MowerFinalPosition mowerFinalPosition1 =
        new MowerFinalPosition(1, 3, 'N', "Instructions executed successfully");
    MowerFinalPosition mowerFinalPosition2 =
        new MowerFinalPosition(5, 1, 'E', "Instructions executed successfully");
    return Arrays.asList(mowerFinalPosition1, mowerFinalPosition2);
  }

  public static List<MowerFinalPosition> buildMowerFinalPositionWithErrors() {
    MowerFinalPosition mowerFinalPosition1 =
        new MowerFinalPosition(1, 3, 'N', "Instructions executed successfully");
    MowerFinalPosition mowerFinalPosition2 =
        new MowerFinalPosition(5, 1, 'E', "Instructions executed successfully");
    MowerFinalPosition mowerFinalPosition3 =
        new MowerFinalPosition(
            0,
            3,
            'E',
            "Error executing instructions, the mower has been stopped at the last valid position, see the log for more details");
    return Arrays.asList(mowerFinalPosition1, mowerFinalPosition2, mowerFinalPosition3);
  }

  public static List<PositionResponseDTO> buildPositionResponseDTO() {
    PositionResponseDTO positionResponseDTO1 =
        PositionResponseDTO.builder()
            .x(1)
            .y(3)
            .position('N')
            .status("Instructions executed successfully")
            .build();

    PositionResponseDTO positionResponseDTO2 =
        PositionResponseDTO.builder()
            .x(5)
            .y(1)
            .position('E')
            .status("Instructions executed successfully")
            .build();

    return Arrays.asList(positionResponseDTO1, positionResponseDTO2);
  }

  public static InstructionsCommands buildInstructionsCommandsDuplicateMowerPosition() {
    MowerInstructions mowerInstructions1 =
        new MowerInstructions(
            UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa1"), "1 2 N", "LMLMLMLMM");
    MowerInstructions mowerInstructions2 =
        new MowerInstructions(
            UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa2"), "1 3 N", "LMLMLMLMM");
    return new InstructionsCommands("5 5", Arrays.asList(mowerInstructions1, mowerInstructions2));
  }

  public static InstructionsCommands buildInstructionsCommandsInvalidDimensions() {
    MowerInstructions mowerInstructions =
        new MowerInstructions(
            UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa1"), "1 2 N", "LMLMLMLMM");
    return new InstructionsCommands("5 5 5", Collections.singletonList(mowerInstructions));
  }

  public static InstructionsCommands buildInstructionsCommandsDuplicateMowerId() {
    MowerInstructions mowerInstructions1 =
        new MowerInstructions(
            UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa1"), "1 2 N", "LMLMLMLMM");
    MowerInstructions mowerInstructions2 =
        new MowerInstructions(
            UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa1"), "1 3 N", "LMLMLMLMM");
    return new InstructionsCommands("5 5", Arrays.asList(mowerInstructions1, mowerInstructions2));
  }

  public static InstructionsCommands buildInstructionsCommandsInvalidPosition() {
    MowerInstructions mowerInstructions =
        new MowerInstructions(
            UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa1"), "1 2 N N", "LMLMLMLMM");
    return new InstructionsCommands("5 5", Collections.singletonList(mowerInstructions));
  }

  public static InstructionsCommands buildInstructionsCommandsInvalidInstruction() {
    MowerInstructions mowerInstructions =
        new MowerInstructions(
            UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa1"), "1 2 N", "LMLMLMLMMX");
    return new InstructionsCommands("5 5", Collections.singletonList(mowerInstructions));
  }
}
