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
    public void executeInstructions_HappyPathWithoutErrors() {
        InstructionsCommands instructionsCommands = TestBuilder.buildInstructionsCommands();
        List<MowerFinalPosition> expected = TestBuilder.buildMowerFinalPosition();

        List<MowerFinalPosition> actual = mowerService.executeInstructions(instructionsCommands);

        assertEquals(expected, actual);
    }

    @Test
    public void executeInstructions_HappyPathWithErrors() {
        InstructionsCommands instructionsCommands = TestBuilder.buildInstructionsCommandsErrors();
        List<MowerFinalPosition> expected = TestBuilder.buildMowerFinalPositionErrors();

        List<MowerFinalPosition> actual = mowerService.executeInstructions(instructionsCommands);

        assertEquals(expected, actual);
    }

    @Test
    public void executeInstructions_ThrowsDuplicateMowerPositionException() {
        InstructionsCommands instructionsCommands = TestBuilder.buildInstructionsCommandsDuplicateMowerPosition();
        assertThrows(DuplicateMowerPositionException.class, () -> mowerService.executeInstructions(instructionsCommands));
    }

    @Test
    public void executeInstructions_ThrowsInvalidDimensionsException() {
        InstructionsCommands instructionsCommands = TestBuilder.buildInstructionsCommandsInvalidDimensions();
        assertThrows(InvalidDimensionsException.class, () -> mowerService.executeInstructions(instructionsCommands));
    }

    @Test
    public void executeInstructions_ThrowsDuplicateMowerIdException() {
        InstructionsCommands instructionsCommands = TestBuilder.buildInstructionsCommandsDuplicateMowerId();
        assertThrows(DuplicateMowerIdException.class, () -> mowerService.executeInstructions(instructionsCommands));
    }

    @Test
    public void executeInstructions_ThrowsInvalidPositionException() {
        InstructionsCommands instructionsCommands = TestBuilder.buildInstructionsCommandsInvalidPosition();
        assertThrows(InvalidPositionException.class, () -> mowerService.executeInstructions(instructionsCommands));
    }

    @Test
    public void executeInstructions_ThrowsInvalidInstructionException() {
        InstructionsCommands instructionsCommands = TestBuilder.buildInstructionsCommandsInvalidInstruction();
        assertThrows(InvalidInstructionException.class, () -> mowerService.executeInstructions(instructionsCommands));
    }
}