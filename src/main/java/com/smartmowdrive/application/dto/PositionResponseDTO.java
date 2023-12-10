package com.smartmowdrive.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PositionResponseDTO {
    private int x;
    private int y;
    private char position;
    private String status;
}