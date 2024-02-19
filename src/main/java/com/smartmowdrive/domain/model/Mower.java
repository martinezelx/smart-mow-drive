package com.smartmowdrive.domain.model;

import com.smartmowdrive.domain.constants.ErrorMessages;
import com.smartmowdrive.domain.exception.OutOfBoundsException;
import java.util.List;
import java.util.UUID;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Data
public class Mower {
  private UUID id;
  private int x;
  private int y;
  private Position position;
  private final Terrain terrain;
  private boolean inErrorState;

  public void turnLeft() {
    this.position =
        switch (this.position) {
          case NORTH -> Position.WEST;
          case WEST -> Position.SOUTH;
          case SOUTH -> Position.EAST;
          case EAST -> Position.NORTH;
        };
    log.info(
        String.format(
            "Mower %s turned left and this heading is pointing to %s", this.id, this.position));
  }

  public void turnRight() {
    this.position =
        switch (this.position) {
          case NORTH -> Position.EAST;
          case EAST -> Position.SOUTH;
          case SOUTH -> Position.WEST;
          case WEST -> Position.NORTH;
        };
    log.info(
        String.format(
            "Mower %s turned right and this heading is pointing to %s", this.id, this.position));
  }

  public void moveForward(List<MowerFinalPosition> mowersFinalPositions) {
    int newX = this.x;
    int newY = this.y;

    switch (this.position) {
      case NORTH -> newY++;
      case EAST -> newX++;
      case SOUTH -> newY--;
      case WEST -> newX--;
    }

    // Check if the new position is occupied by another mower, regardless of its status
    for (MowerFinalPosition mowerFinalPosition : mowersFinalPositions) {
      if (mowerFinalPosition.x() == newX && mowerFinalPosition.y() == newY) {
        this.inErrorState = true;
        log.error(
            String.format(
                "Mower %s try to move forward to X:%s Y:%s but the position is occupied",
                this.id, newX, newY));
        return;
      }
    }

    if (!terrain.isWithinBounds(newX, newY)) {
      throw new OutOfBoundsException(String.format("%s %s", ErrorMessages.OUT_OF_BOUNDS, this.id));
    }

    this.x = newX;
    this.y = newY;
    log.info(
        String.format(
            "Mower %s move forward and this new position is X:%s Y:%s", this.id, this.x, this.y));
  }
}
