package com.smartmowdrive.application.usecases;

import com.smartmowdrive.application.exceptions.*;
import com.smartmowdrive.application.utils.RegexPatterns;
import com.smartmowdrive.application.utils.StatusMessage;
import com.smartmowdrive.domain.*;
import com.smartmowdrive.domain.commons.exceptions.InvalidInstructionException;
import com.smartmowdrive.domain.commons.exceptions.InvalidPositionException;
import com.smartmowdrive.domain.commons.utils.ErrorMessages;
import com.smartmowdrive.infrastructure.rest.dto.InstructionsRequestDTO;
import com.smartmowdrive.infrastructure.rest.dto.PositionResponseDTO;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProcessMowerInstructionsUseCase implements ProcessMowerInstructions {

  private static final String ERROR_MESSAGE_FORMAT = "%s %s";

  private final ConversionService conversionService;

  public List<PositionResponseDTO> processInstructions(InstructionsRequestDTO request) {
    var formattedRequest = conversionService.convert(request, InstructionsCommands.class);
    log.info("Formatted request: {}", formattedRequest);
    return executeInstructions(Objects.requireNonNull(formattedRequest)).stream()
        .map(
            mowerFinalPosition ->
                conversionService.convert(mowerFinalPosition, PositionResponseDTO.class))
        .toList();
  }

  private List<MowerFinalPosition> executeInstructions(InstructionsCommands request) {
    log.info("Executing instructions");
    var mowersFinalPositions = new ArrayList<MowerFinalPosition>();

    validateTerrainDimensions(request.terrainDimensions());
    validateMowerInstructionsIdUniqueness(request.mowersInstructions());

    for (var mowerInstruction : request.mowersInstructions()) {

      validateMowerPosition(mowerInstruction.startingPosition().toUpperCase());
      validateInstructions(mowerInstruction.instructions().toUpperCase());

      var positionParts = mowerInstruction.startingPosition().split(" ");
      var xCoordinate = Integer.parseInt(positionParts[0]);
      var yCoordinate = Integer.parseInt(positionParts[1]);
      var heading = positionParts[2].charAt(0);

      // Check if the new position is the starting position of another mower, regardless of its
      // status
      if (mowersFinalPositions.stream()
          .anyMatch(
              mowerFinalPosition ->
                  mowerFinalPosition.x() == xCoordinate
                      && mowerFinalPosition.y() == yCoordinate
                      && mowerFinalPosition.position() == heading)) {
        throw new DuplicateMowerPositionException(
            String.format(
                ERROR_MESSAGE_FORMAT,
                ErrorMessages.DUPLICATE_MOWER_POSITION,
                mowerInstruction.id()));
      }

      var mower =
          executeInstruction(
              mowerInstruction.id(),
              request.terrainDimensions(),
              mowerInstruction.startingPosition().toUpperCase(),
              mowerInstruction.instructions().toUpperCase(),
              mowersFinalPositions);
      mowersFinalPositions.add(mower);
    }

    return mowersFinalPositions;
  }

  private MowerFinalPosition executeInstruction(
      UUID id,
      String terrainDimensions,
      String mowerPosition,
      String instructions,
      List<MowerFinalPosition> mowersFinalPositions) {
    log.info("Executing instruction for mower with ID: {}", id);
    var dimensionsParts = terrainDimensions.split(" ");
    var width = Integer.parseInt(dimensionsParts[0]);
    var height = Integer.parseInt(dimensionsParts[1]);
    var terrain = new Terrain(width, height);

    var positionParts = mowerPosition.split(" ");
    var x = Integer.parseInt(positionParts[0]);
    var y = Integer.parseInt(positionParts[1]);
    var position = Position.fromChar(positionParts[2].charAt(0));

    var mower = new Mower(id, x, y, position, terrain, false);

    instructions
        .chars()
        .mapToObj(c -> (char) c)
        .map(Instruction::fromChar)
        .forEach(
            instruction -> {
              if (mower.isInErrorState()) {
                return;
              }
              switch (instruction) {
                case TURN_LEFT -> mower.turnLeft();
                case TURN_RIGHT -> mower.turnRight();
                case MOVE_FORWARD -> mower.moveForward(mowersFinalPositions);
              }
            });

    log.info("Finished executing instruction for mower with ID: {}", id);
    return new MowerFinalPosition(
        mower.getX(),
        mower.getY(),
        mower.getPosition().getCoordinateCode(),
        mower.isInErrorState() ? StatusMessage.ERROR : StatusMessage.SUCCESSFUL);
  }

  private void validateTerrainDimensions(String terrainDimensions) {
    log.info("Validating terrain dimensions: {}", terrainDimensions);
    if (!RegexPatterns.TERRAIN_DIMENSIONS.matcher(terrainDimensions).matches()) {
      throw new InvalidDimensionsException(
          String.format(
              ERROR_MESSAGE_FORMAT, ErrorMessages.INVALID_TERRAIN_FORMAT, terrainDimensions));
    }
  }

  private void validateMowerInstructionsIdUniqueness(List<MowerInstructions> mowerInstructions) {
    log.info("Validating mower instructions ID uniqueness");
    Set<UUID> uniqueIds = new HashSet<>();
    for (MowerInstructions instruction : mowerInstructions) {
      if (!uniqueIds.add(instruction.id())) {
        throw new DuplicateMowerIdException(
            String.format(
                ERROR_MESSAGE_FORMAT, ErrorMessages.DUPLICATE_MOWER_ID, instruction.id()));
      }
    }
  }

  private void validateMowerPosition(String mowerPosition) {
    log.info("Validating mower position: {}", mowerPosition);
    if (!RegexPatterns.MOWER_POSITION.matcher(mowerPosition).matches()) {
      throw new InvalidPositionException(
          String.format(
              ERROR_MESSAGE_FORMAT, ErrorMessages.INVALID_POSITION_FORMAT, mowerPosition));
    }
  }

  private void validateInstructions(String instructions) {
    log.info("Validating instructions: {}", instructions);
    if (!RegexPatterns.INSTRUCTIONS.matcher(instructions).matches()) {
      throw new InvalidInstructionException(
          String.format(
              ERROR_MESSAGE_FORMAT, ErrorMessages.INVALID_INSTRUCTIONS_FORMAT, instructions));
    }
  }
}
