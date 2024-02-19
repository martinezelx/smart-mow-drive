package com.smartmowdrive.domain.model;

import java.util.List;

public record InstructionsCommands(
    String terrainDimensions, List<MowerInstructions> mowersInstructions) {}
