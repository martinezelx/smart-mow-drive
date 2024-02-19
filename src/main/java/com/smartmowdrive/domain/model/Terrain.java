package com.smartmowdrive.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class Terrain {
  private final int width;
  private final int height;

  public boolean isWithinBounds(int x, int y) {
    return x >= 0 && x <= width && y >= 0 && y <= height;
  }
}
