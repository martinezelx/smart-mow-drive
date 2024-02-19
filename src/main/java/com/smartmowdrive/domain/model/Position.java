package com.smartmowdrive.domain.model;

import com.smartmowdrive.domain.constants.ErrorMessages;
import com.smartmowdrive.domain.exception.InvalidPositionException;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Position {
  NORTH('N'),
  WEST('W'),
  SOUTH('S'),
  EAST('E');

  private final char coordinateCode;

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
