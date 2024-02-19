package com.smartmowdrive.utils;

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

  public static InstructionsRequestDTO buildInstructionsRequestDTOWithErrors() {
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

    MowerInstructionsDTO mowerInstructionsDTO3 =
        MowerInstructionsDTO.builder()
            .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa3"))
            .startingPosition("1 4 N")
            .instructions("LMLMLMLMM")
            .build();

    List<MowerInstructionsDTO> mowersInstructions =
        Arrays.asList(mowerInstructionsDTO1, mowerInstructionsDTO2, mowerInstructionsDTO3);

    return InstructionsRequestDTO.builder()
        .terrainDimensions("5 5")
        .mowersInstructions(mowersInstructions)
        .build();
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

  public static List<PositionResponseDTO> buildPositionResponseDTOWithErrors() {
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

    PositionResponseDTO positionResponseDTO3 =
        PositionResponseDTO.builder()
            .x(0)
            .y(3)
            .position('E')
            .status(
                "Error executing instructions, the mower has been stopped at the last valid position, see the log for more details")
            .build();

    return Arrays.asList(positionResponseDTO1, positionResponseDTO2, positionResponseDTO3);
  }

  public static InstructionsRequestDTO buildInstructionsRequestDTODuplicateMowerPosition() {
    MowerInstructionsDTO mowerInstructionsDTO1 =
        MowerInstructionsDTO.builder()
            .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa1"))
            .startingPosition("1 2 N")
            .instructions("LMLMLMLMM")
            .build();

    MowerInstructionsDTO mowerInstructionsDTO2 =
        MowerInstructionsDTO.builder()
            .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa2"))
            .startingPosition("1 3 N")
            .instructions("LMLMLMLMM")
            .build();

    List<MowerInstructionsDTO> mowersInstructions =
        Arrays.asList(mowerInstructionsDTO1, mowerInstructionsDTO2);

    return InstructionsRequestDTO.builder()
        .terrainDimensions("5 5")
        .mowersInstructions(mowersInstructions)
        .build();
  }

  public static InstructionsRequestDTO buildInstructionsRequestDTOInvalidDimensions() {
    MowerInstructionsDTO mowerInstructionsDTO1 =
        MowerInstructionsDTO.builder()
            .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa1"))
            .startingPosition("1 2 N")
            .instructions("LMLMLMLMM")
            .build();

    List<MowerInstructionsDTO> mowersInstructions =
        Collections.singletonList(mowerInstructionsDTO1);

    return InstructionsRequestDTO.builder()
        .terrainDimensions("5 5 5")
        .mowersInstructions(mowersInstructions)
        .build();
  }

  public static InstructionsRequestDTO buildInstructionsRequestDTODuplicateMowerId() {
    MowerInstructionsDTO mowerInstructionsDTO1 =
        MowerInstructionsDTO.builder()
            .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa1"))
            .startingPosition("1 2 N")
            .instructions("LMLMLMLMM")
            .build();

    MowerInstructionsDTO mowerInstructionsDTO2 =
        MowerInstructionsDTO.builder()
            .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa1"))
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

  public static InstructionsRequestDTO buildInstructionsRequestDTOInvalidPosition() {
    MowerInstructionsDTO mowerInstructionsDTO1 =
        MowerInstructionsDTO.builder()
            .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa1"))
            .startingPosition("1 2 N N")
            .instructions("LMLMLMLMM")
            .build();

    List<MowerInstructionsDTO> mowersInstructions =
        Collections.singletonList(mowerInstructionsDTO1);

    return InstructionsRequestDTO.builder()
        .terrainDimensions("5 5")
        .mowersInstructions(mowersInstructions)
        .build();
  }

  public static InstructionsRequestDTO buildInstructionsRequestDTOCommandsInvalidInstruction() {
    MowerInstructionsDTO mowerInstructionsDTO1 =
        MowerInstructionsDTO.builder()
            .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa1"))
            .startingPosition("1 2 N")
            .instructions("LMLMLMLMMX")
            .build();

    List<MowerInstructionsDTO> mowersInstructions =
        Collections.singletonList(mowerInstructionsDTO1);

    return InstructionsRequestDTO.builder()
        .terrainDimensions("5 5")
        .mowersInstructions(mowersInstructions)
        .build();
  }
}
