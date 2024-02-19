package com.smartmowdrive.domain.constants;

public class ErrorMessages {
  public static final String GENERIC_ERROR = "An error occurred while processing the request.";
  public static final String DUPLICATE_MOWER_POSITION =
      "The starting position of this mower must not be the same as any final position of the previous ones:";
  public static final String DUPLICATE_MOWER_ID = "Duplicate mower ID found:";
  public static final String INVALID_TERRAIN_FORMAT = "Invalid terrain:";
  public static final String INVALID_POSITION_FORMAT = "Invalid starting position:";
  public static final String INVALID_INSTRUCTIONS_FORMAT = "Invalid instructions:";
  public static final String OUT_OF_BOUNDS =
      "The starting position of this mower is out of bounds:";
  public static final String NULL_POINTER = "Null value encountered";
}
