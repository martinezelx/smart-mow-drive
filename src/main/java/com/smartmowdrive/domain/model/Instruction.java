package com.smartmowdrive.domain.model;

import com.smartmowdrive.domain.constants.ErrorMessages;
import com.smartmowdrive.domain.exception.InvalidInstructionException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Instruction {
    TURN_LEFT('L'),
    TURN_RIGHT('R'),
    MOVE_FORWARD('M');

    private final char instructionCode;

    public static Instruction fromChar(char instructionChar) {
        return Arrays.stream(Instruction.values())
                .filter(instruction -> instruction.getInstructionCode() == instructionChar)
                .findFirst()
                .orElseThrow(() -> new InvalidInstructionException(String.format("%s %s", ErrorMessages.INVALID_INSTRUCTIONS_FORMAT, instructionChar)));
    }

    public static String getValidChars() {
        return Arrays.stream(Instruction.values())
                .map(instruction -> String.valueOf(instruction.getInstructionCode()))
                .collect(Collectors.joining());
    }
}
