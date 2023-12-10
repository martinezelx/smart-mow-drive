package com.smartmowdrive.application.converter;

import com.smartmowdrive.application.dto.MowerInstructionsDTO;
import com.smartmowdrive.domain.model.MowerInstructions;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MowerInstructionsDTOToMowerInstructionsConverter implements Converter<MowerInstructionsDTO, MowerInstructions> {

    @Override
    public MowerInstructions convert(MowerInstructionsDTO mowerInstructionsDTO) {
        return new MowerInstructions(
                mowerInstructionsDTO.getId(),
                mowerInstructionsDTO.getStartingPosition(),
                mowerInstructionsDTO.getInstructions());
    }
}