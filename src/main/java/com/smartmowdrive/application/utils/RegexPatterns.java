package com.smartmowdrive.application.utils;

import com.smartmowdrive.domain.Instruction;
import com.smartmowdrive.domain.Position;
import java.util.regex.Pattern;

public class RegexPatterns {
  public static final Pattern TERRAIN_DIMENSIONS = Pattern.compile("\\d+ \\d+");
  public static final Pattern MOWER_POSITION =
      Pattern.compile("\\d+ \\d+ [" + Position.getValidChars() + "]");
  public static final Pattern INSTRUCTIONS =
      Pattern.compile("[" + Instruction.getValidChars() + "]+");

  private RegexPatterns() {
    throw new UnsupportedOperationException(
        "RegexPatterns is a utility class and cannot be instantiated");
  }
}
