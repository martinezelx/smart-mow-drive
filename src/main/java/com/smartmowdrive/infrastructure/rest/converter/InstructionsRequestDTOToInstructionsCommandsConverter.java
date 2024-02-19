package com.smartmowdrive.infrastructure.rest.converter;

import com.smartmowdrive.domain.model.InstructionsCommands;
import com.smartmowdrive.infrastructure.rest.dto.InstructionsRequestDTO;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InstructionsRequestDTOToInstructionsCommandsConverter
    implements Converter<InstructionsRequestDTO, InstructionsCommands> {

  private final MowerInstructionsDTOToMowerInstructionsConverter mowerInstructionsConverter;

  @Override
  public InstructionsCommands convert(InstructionsRequestDTO instructionsRequestDTO) {
    return new InstructionsCommands(
        instructionsRequestDTO.getTerrainDimensions(),
        instructionsRequestDTO.getMowersInstructions().stream()
            .map(mowerInstructionsConverter::convert)
            .collect(Collectors.toList()));
  }
}
