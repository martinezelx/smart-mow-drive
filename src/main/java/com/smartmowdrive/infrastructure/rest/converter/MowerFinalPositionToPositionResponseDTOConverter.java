package com.smartmowdrive.infrastructure.rest.converter;

import com.smartmowdrive.infrastructure.rest.dto.PositionResponseDTO;
import com.smartmowdrive.domain.model.MowerFinalPosition;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MowerFinalPositionToPositionResponseDTOConverter implements Converter<MowerFinalPosition, PositionResponseDTO> {

    @Override
    public PositionResponseDTO convert(MowerFinalPosition mowerFinalPosition) {
        return new PositionResponseDTO(
                mowerFinalPosition.x(),
                mowerFinalPosition.y(),
                mowerFinalPosition.position(),
                mowerFinalPosition.status()
        );
    }
}
