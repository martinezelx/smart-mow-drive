package com.smartmowdrive.domain;

import java.util.UUID;

public record MowerInstructions(UUID id, String startingPosition, String instructions) {}
