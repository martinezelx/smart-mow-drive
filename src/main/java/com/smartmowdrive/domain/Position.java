package com.smartmowdrive.domain;

import com.smartmowdrive.domain.commons.exceptions.InvalidPositionException;
import com.smartmowdrive.domain.commons.utils.ErrorMessages;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum Position {
  NORTH('N'),
  WEST('W'),
  SOUTH('S'),
  EAST('E');

  private final char coordinateCode;

  Position(char coordinateCode) {
    this.coordinateCode = coordinateCode;
  }

  public char getCoordinateCode() {
    return this.coordinateCode;
  }

  public static Position fromChar(char coordinateChar) {
    return Arrays.stream(Position.values())
        .filter(position -> position.getCoordinateCode() == coordinateChar)
        .findFirst()
        .orElseThrow(
            () ->
                new InvalidPositionException(
                    String.format("%s %s", ErrorMessages.INVALID_POSITION_FORMAT, coordinateChar)));
  }

  public static String getValidChars() {
    return Arrays.stream(Position.values())
        .map(position -> String.valueOf(position.getCoordinateCode()))
        .collect(Collectors.joining());
  }
}
