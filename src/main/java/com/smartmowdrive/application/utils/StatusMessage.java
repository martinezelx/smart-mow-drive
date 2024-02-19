package com.smartmowdrive.application.utils;

public class StatusMessage {
  public static final String SUCCESSFUL = "Instructions executed successfully";
  public static final String ERROR =
      "Error executing instructions, the mower has been stopped at the last valid position, see the log for more details";

  private StatusMessage() {
    throw new UnsupportedOperationException("Status is a utility class and cannot be instantiated");
  }
}
