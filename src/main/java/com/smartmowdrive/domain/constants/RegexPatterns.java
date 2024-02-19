package com.smartmowdrive.domain.constants;

import com.smartmowdrive.domain.model.Instruction;
import com.smartmowdrive.domain.model.Position;
import java.util.regex.Pattern;

public class RegexPatterns {
  public static final Pattern TERRAIN_DIMENSIONS = Pattern.compile("\\d+ \\d+");
  public static final Pattern MOWER_POSITION =
      Pattern.compile("\\d+ \\d+ [" + Position.getValidChars() + "]");
  public static final Pattern INSTRUCTIONS =
      Pattern.compile("[" + Instruction.getValidChars() + "]+");
}
