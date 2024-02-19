package com.smartmowdrive.domain;

import com.smartmowdrive.domain.commons.exceptions.InvalidInstructionException;
import com.smartmowdrive.domain.commons.utils.ErrorMessages;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum Instruction {
  TURN_LEFT('L'),
  TURN_RIGHT('R'),
  MOVE_FORWARD('M');

  private final char instructionCode;

  Instruction(char instructionCode) {
    this.instructionCode = instructionCode;
  }

  public char getInstructionCode() {
    return this.instructionCode;
  }

  public static Instruction fromChar(char instructionChar) {
    return Arrays.stream(Instruction.values())
        .filter(instruction -> instruction.getInstructionCode() == instructionChar)
        .findFirst()
        .orElseThrow(
            () ->
                new InvalidInstructionException(
                    String.format(
                        "%s %s", ErrorMessages.INVALID_INSTRUCTIONS_FORMAT, instructionChar)));
  }

  public static String getValidChars() {
    return Arrays.stream(Instruction.values())
        .map(instruction -> String.valueOf(instruction.getInstructionCode()))
        .collect(Collectors.joining());
  }
}
