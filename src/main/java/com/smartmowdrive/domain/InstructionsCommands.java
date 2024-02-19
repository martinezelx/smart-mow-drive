package com.smartmowdrive.domain;

import java.util.List;

public record InstructionsCommands(
    String terrainDimensions, List<MowerInstructions> mowersInstructions) {}
