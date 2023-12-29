package com.smartmowdrive.domain.service;

import com.smartmowdrive.domain.exception.*;
import com.smartmowdrive.domain.model.InstructionsCommands;
import com.smartmowdrive.domain.model.MowerFinalPosition;
import com.smartmowdrive.utils.TestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MowerServiceTest {
    private MowerService mowerService;

    @BeforeEach
    public void setup() {
        mowerService = new MowerService();
    }

    @Test
    public void executeInstructionsReturnsExpectedPositionsWhenValidInstructionsProvided() {
        InstructionsCommands instructionsCommands = TestBuilder.buildInstructionsCommands();
        List<MowerFinalPosition> expected = TestBuilder.buildMowerFinalPosition();

        List<MowerFinalPosition> actual = mowerService.executeInstructions(instructionsCommands);

        assertEquals(expected, actual);
    }

    @Test
    public void executeInstructionsReturnsExpectedPositionsWithErrorsWhenInvalidInstructionsProvided() {
        InstructionsCommands instructionsCommands = TestBuilder.buildInstructionsCommandsWithErrors();
        List<MowerFinalPosition> expected = TestBuilder.buildMowerFinalPositionWithErrors();

        List<MowerFinalPosition> actual = mowerService.executeInstructions(instructionsCommands);

        assertEquals(expected, actual);
    }

    @Test
    public void executeInstructionsThrowsDuplicateMowerPositionExceptionWhenDuplicateMowerPositionProvided() {
        InstructionsCommands instructionsCommands = TestBuilder.buildInstructionsCommandsDuplicateMowerPosition();
        assertThrows(DuplicateMowerPositionException.class, () -> mowerService.executeInstructions(instructionsCommands));
    }

    @Test
    public void executeInstructionsThrowsInvalidDimensionsExceptionWhenInvalidDimensionsProvided() {
        InstructionsCommands instructionsCommands = TestBuilder.buildInstructionsCommandsInvalidDimensions();
        assertThrows(InvalidDimensionsException.class, () -> mowerService.executeInstructions(instructionsCommands));
    }

    @Test
    public void executeInstructionsThrowsDuplicateMowerIdExceptionWhenDuplicateMowerIdProvided() {
        InstructionsCommands instructionsCommands = TestBuilder.buildInstructionsCommandsDuplicateMowerId();
        assertThrows(DuplicateMowerIdException.class, () -> mowerService.executeInstructions(instructionsCommands));
    }

    @Test
    public void executeInstructionsThrowsInvalidPositionExceptionWhenInvalidPositionProvided() {
        InstructionsCommands instructionsCommands = TestBuilder.buildInstructionsCommandsInvalidPosition();
        assertThrows(InvalidPositionException.class, () -> mowerService.executeInstructions(instructionsCommands));
    }

    @Test
    public void executeInstructionsThrowsInvalidInstructionExceptionWhenInvalidInstructionProvided() {
        InstructionsCommands instructionsCommands = TestBuilder.buildInstructionsCommandsInvalidInstruction();
        assertThrows(InvalidInstructionException.class, () -> mowerService.executeInstructions(instructionsCommands));
    }
}