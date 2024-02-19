package com.smartmowdrive.domain;

import com.smartmowdrive.domain.commons.exceptions.OutOfBoundsException;
import com.smartmowdrive.domain.commons.utils.ErrorMessages;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mower {
  private static final Logger logger = LoggerFactory.getLogger(Mower.class);
  private UUID id;
  private int x;
  private int y;
  private Position position;
  private final Terrain terrain;
  private boolean inErrorState;

  public Mower(UUID id, int x, int y, Position position, Terrain terrain, boolean inErrorState) {
    this.id = id;
    this.x = x;
    this.y = y;
    this.position = position;
    this.terrain = terrain;
    this.inErrorState = inErrorState;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public Terrain getTerrain() {
    return terrain;
  }

  public boolean isInErrorState() {
    return inErrorState;
  }

  public void setInErrorState(boolean inErrorState) {
    this.inErrorState = inErrorState;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Mower mower = (Mower) o;
    return x == mower.x
        && y == mower.y
        && inErrorState == mower.inErrorState
        && Objects.equals(id, mower.id)
        && position == mower.position
        && Objects.equals(terrain, mower.terrain);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, x, y, position, terrain, inErrorState);
  }

  @Override
  public String toString() {
    return "Mower{"
        + "id="
        + id
        + ", x="
        + x
        + ", y="
        + y
        + ", position="
        + position
        + ", terrain="
        + terrain
        + ", inErrorState="
        + inErrorState
        + '}';
  }

  public void turnLeft() {
    this.position =
        switch (this.position) {
          case NORTH -> Position.WEST;
          case WEST -> Position.SOUTH;
          case SOUTH -> Position.EAST;
          case EAST -> Position.NORTH;
        };
    logger.info("Mower {} turned left and this heading is pointing to {}", this.id, this.position);
  }

  public void turnRight() {
    this.position =
        switch (this.position) {
          case NORTH -> Position.EAST;
          case EAST -> Position.SOUTH;
          case SOUTH -> Position.WEST;
          case WEST -> Position.NORTH;
        };
    logger.info("Mower {} turned right and this heading is pointing to {}", this.id, this.position);
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
        logger.error(
            "Mower {} try to move forward to X:{} Y:{} but the position is occupied",
            this.id,
            newX,
            newY);
        return;
      }
    }

    if (!terrain.isWithinBounds(newX, newY)) {
      throw new OutOfBoundsException(String.format("%s %s", ErrorMessages.OUT_OF_BOUNDS, this.id));
    }

    this.x = newX;
    this.y = newY;
    logger.info(
        "Mower {} move forward and this new position is X:{} Y:{}", this.id, this.x, this.y);
  }
}
