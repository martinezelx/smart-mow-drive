package com.smartmowdrive.domain;

public record Terrain(int width, int height) {
  public boolean isWithinBounds(int x, int y) {
    return x >= 0 && x <= width && y >= 0 && y <= height;
  }
}
