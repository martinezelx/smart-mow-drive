package com.smartmowdrive.domain.model;

import java.util.UUID;

public record MowerInstructions(UUID id, String startingPosition, String instructions) {}
