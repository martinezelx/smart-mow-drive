package com.smartmowdrive.infrastructure.config;

import com.smartmowdrive.infrastructure.rest.converter.InstructionsRequestDTOToInstructionsCommandsConverter;
import com.smartmowdrive.infrastructure.rest.converter.MowerFinalPositionToPositionResponseDTOConverter;
import com.smartmowdrive.infrastructure.rest.converter.MowerInstructionsDTOToMowerInstructionsConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final MowerFinalPositionToPositionResponseDTOConverter
      mowerFinalPositionToPositionResponseDTOConverter;
  private final InstructionsRequestDTOToInstructionsCommandsConverter
      instructionsRequestDTOToInstructionsCommandsConverter;
  private final MowerInstructionsDTOToMowerInstructionsConverter
      mowerInstructionsDTOToMowerInstructionsConverter;

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(mowerFinalPositionToPositionResponseDTOConverter);
    registry.addConverter(instructionsRequestDTOToInstructionsCommandsConverter);
    registry.addConverter(mowerInstructionsDTOToMowerInstructionsConverter);
  }
}
