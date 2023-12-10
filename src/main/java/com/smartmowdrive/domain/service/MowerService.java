package com.smartmowdrive.domain.service;

import com.smartmowdrive.domain.constants.ErrorMessages;
import com.smartmowdrive.domain.constants.RegexPatterns;
import com.smartmowdrive.domain.constants.Status;
import com.smartmowdrive.domain.exception.*;
import com.smartmowdrive.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class MowerService {

    public List<MowerFinalPosition> executeInstructions(InstructionsCommands request) {

        var mowersFinalPositions = new ArrayList<MowerFinalPosition>();

        validateTerrainDimensions(request.terrainDimensions());
        validateMowerInstructionsUniqueness(request.mowersInstructions());

        for (var mowerInstruction : request.mowersInstructions()) {

            validateMowerPosition(mowerInstruction.startingPosition().toUpperCase());
            validateInstructions(mowerInstruction.instructions().toUpperCase());

            var positionParts = mowerInstruction.startingPosition().split(" ");
            var xCoordinate = Integer.parseInt(positionParts[0]);
            var yCoordinate = Integer.parseInt(positionParts[1]);
            var heading = positionParts[2].charAt(0);

            // Check if the new position is the starting position of another mower, regardless of its status
            if (mowersFinalPositions.stream().anyMatch(mowerFinalPosition ->
                    mowerFinalPosition.x() == xCoordinate &&
                            mowerFinalPosition.y() == yCoordinate &&
                            mowerFinalPosition.position() == heading)) {
                throw new DuplicateMowerPositionException(String.format("%s %s", ErrorMessages.DUPLICATE_MOWER_POSITION, mowerInstruction.id()));
            }

            var mower = executeInstruction(
                    mowerInstruction.id(),
                    request.terrainDimensions(),
                    mowerInstruction.startingPosition().toUpperCase(),
                    mowerInstruction.instructions().toUpperCase(),
                    mowersFinalPositions
            );
            mowersFinalPositions.add(mower);
        }

        return mowersFinalPositions;
    }

    public MowerFinalPosition executeInstruction(UUID id, String terrainDimensions, String mowerPosition, String instructions, List<MowerFinalPosition> mowersFinalPositions) {

        var dimensionsParts = terrainDimensions.split(" ");
        var width = Integer.parseInt(dimensionsParts[0]);
        var height = Integer.parseInt(dimensionsParts[1]);
        var terrain = new Terrain(width, height);

        var positionParts = mowerPosition.split(" ");
        var x = Integer.parseInt(positionParts[0]);
        var y = Integer.parseInt(positionParts[1]);
        var position = Position.fromChar(positionParts[2].charAt(0));

        var mower = new Mower(id, x, y, position, terrain, false);

        instructions.chars()
                .mapToObj(c -> (char) c)
                .map(Instruction::fromChar)
                .forEach(instruction -> {
                    if (mower.isInErrorState()) {
                        return;
                    }
                    switch (instruction) {
                        case TURN_LEFT -> mower.turnLeft();
                        case TURN_RIGHT -> mower.turnRight();
                        case MOVE_FORWARD -> mower.moveForward(mowersFinalPositions);
                    }
                });

        return new MowerFinalPosition(mower.getX(), mower.getY(), mower.getPosition().getCoordinateCode(),
                mower.isInErrorState() ? Status.ERROR : Status.SUCCESSFUL);
    }

    private void validateTerrainDimensions(String terrainDimensions) {
        if (!RegexPatterns.TERRAIN_DIMENSIONS.matcher(terrainDimensions).matches()) {
            throw new InvalidDimensionsException(String.format("%s %s", ErrorMessages.INVALID_TERRAIN_FORMAT, terrainDimensions));
        }
    }

    private void validateMowerInstructionsUniqueness(List<MowerInstructions> mowerInstructions) {
        Set<UUID> uniqueIds = new HashSet<>();
        for (MowerInstructions instruction : mowerInstructions) {
            if (!uniqueIds.add(instruction.id())) {
                throw new DuplicateMowerIdException(String.format("%s %s", ErrorMessages.DUPLICATE_MOWER_ID, instruction.id()));
            }
        }
    }

    private void validateMowerPosition(String mowerPosition) {
        if (!RegexPatterns.MOWER_POSITION.matcher(mowerPosition).matches()) {
            throw new InvalidPositionException(String.format("%s %s", ErrorMessages.INVALID_POSITION_FORMAT, mowerPosition));
        }
    }

    private void validateInstructions(String instructions) {
        if (!RegexPatterns.INSTRUCTIONS.matcher(instructions).matches()) {
            throw new InvalidInstructionException(String.format("%s %s", ErrorMessages.INVALID_INSTRUCTIONS_FORMAT, instructions));
        }
    }
}