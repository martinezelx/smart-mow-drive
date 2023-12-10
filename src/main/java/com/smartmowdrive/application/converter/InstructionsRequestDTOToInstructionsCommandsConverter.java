package com.smartmowdrive.application.converter;

import com.smartmowdrive.application.dto.InstructionsRequestDTO;
import com.smartmowdrive.domain.model.InstructionsCommands;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class InstructionsRequestDTOToInstructionsCommandsConverter implements Converter<InstructionsRequestDTO, InstructionsCommands> {

    private final MowerInstructionsDTOToMowerInstructionsConverter mowerInstructionsConverter;

    @Override
    public InstructionsCommands convert(InstructionsRequestDTO instructionsRequestDTO) {
        return new InstructionsCommands(
                instructionsRequestDTO.getTerrainDimensions(),
                instructionsRequestDTO.getMowersInstructions().stream()
                        .map(mowerInstructionsConverter::convert)
                        .collect(Collectors.toList())
        );
    }
}
