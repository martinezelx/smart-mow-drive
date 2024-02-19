package com.smartmowdrive.infrastructure.cli;

import com.smartmowdrive.application.usecases.ProcessMowerInstructions;
import com.smartmowdrive.domain.Position;
import com.smartmowdrive.infrastructure.rest.dto.InstructionsRequestDTO;
import com.smartmowdrive.infrastructure.rest.dto.MowerInstructionsDTO;
import com.smartmowdrive.infrastructure.rest.dto.PositionResponseDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("cli")
@RequiredArgsConstructor
@Component
@SuppressWarnings(
    "squid:S106") // Suppress "Standard outputs should not be used directly to log anything"
public class MowerCLI implements CommandLineRunner {

  private static final String ENTER_MOWER_PROMPT = "| Enter mower ";
  private static final String SEPARATOR_LINE =
      "---------------------------------------------------";

  private final ProcessMowerInstructions processMowerInstructions;

  @Override
  public void run(String... args) {
    Scanner scanner = new Scanner(System.in);
    boolean exit = false;

    while (!exit) {
      try {
        printWelcomeMessage();
        String terrainDimensions = getTerrainDimensions(scanner);
        int numberOfMowers = getNumberOfMowers(scanner);
        List<MowerInstructionsDTO> mowersInstructions =
            getMowersInstructions(scanner, numberOfMowers);

        InstructionsRequestDTO request =
            new InstructionsRequestDTO(terrainDimensions, mowersInstructions);
        var mowersFinalPositions = processMowerInstructions.processInstructions(request);

        printMowersData(mowersFinalPositions);
        drawInstructions(terrainDimensions, mowersFinalPositions);

        exit = getExitResponse(scanner);

      } catch (IllegalArgumentException e) {
        System.out.println("An error occurred: " + e.getMessage());
        exit = !getContinueResponse(scanner);
      }
    }
  }

  private void printWelcomeMessage() {
    System.out.println("===========================================");
    System.out.println("|       SMART MOW DRIVE 1.5.0 - CLI       |");
    System.out.println("|      SEAT:CODE Technical Challenge      |");
    System.out.println("|        by Luis M.M. @martinezelx        |");
    System.out.println("===========================================");
  }

  private String getTerrainDimensions(Scanner scanner) {
    System.out.println(SEPARATOR_LINE);
    System.out.println("| Enter terrain dimensions:               |");
    System.out.println("| Example: '5 5'                          |");
    System.out.println(SEPARATOR_LINE);
    return scanner.nextLine();
  }

  private int getNumberOfMowers(Scanner scanner) {
    System.out.println(SEPARATOR_LINE);
    System.out.println("| How many mowers do you want to operate? |");
    System.out.println(SEPARATOR_LINE);
    int numberOfMowers = scanner.nextInt();
    scanner.nextLine();
    return numberOfMowers;
  }

  private List<MowerInstructionsDTO> getMowersInstructions(Scanner scanner, int numberOfMowers) {
    List<MowerInstructionsDTO> mowersInstructions = new ArrayList<>();

    for (int i = 0; i < numberOfMowers; i++) {
      System.out.println(SEPARATOR_LINE);
      System.out.println(ENTER_MOWER_PROMPT + (i + 1) + " ID:");
      System.out.println("| Example: '3fa85f64-5717-4562-b3fc-2c963f66afa6'");
      System.out.println(SEPARATOR_LINE);
      String id = scanner.nextLine();

      System.out.println(SEPARATOR_LINE);
      System.out.println(ENTER_MOWER_PROMPT + (i + 1) + " initial position:");
      System.out.println("| Example: '1 2 N'");
      System.out.println(SEPARATOR_LINE);
      String mowerPosition = scanner.nextLine();

      System.out.println(SEPARATOR_LINE);
      System.out.println(ENTER_MOWER_PROMPT + (i + 1) + " instructions:");
      System.out.println("| Example: 'LMLMLMLMM'");
      System.out.println(SEPARATOR_LINE);
      String instructions = scanner.nextLine();

      mowersInstructions.add(
          new MowerInstructionsDTO(UUID.fromString(id), mowerPosition, instructions));
    }

    return mowersInstructions;
  }

  private void printMowersData(List<PositionResponseDTO> mowersFinalPositions) {
    System.out.println(
        "================================================================================");
    System.out.println("| Mowers data:");
    mowersFinalPositions.forEach(mower -> System.out.println("| " + mower + " |"));
    System.out.println(
        "================================================================================");
  }

  private boolean getExitResponse(Scanner scanner) {
    System.out.println("Do you want to exit? (yes/no)");
    String exitResponse = scanner.nextLine();
    return "yes".equalsIgnoreCase(exitResponse);
  }

  private boolean getContinueResponse(Scanner scanner) {
    System.out.println("Do you want to continue? (yes/no)");
    String continueResponse = scanner.nextLine();
    return "yes".equalsIgnoreCase(continueResponse);
  }

  private void drawInstructions(
      String terrainDimensions, List<PositionResponseDTO> mowersFinalPositions) {
    int terrainWidth = getTerrainWidth(terrainDimensions);
    int terrainHeight = getTerrainHeight(terrainDimensions);

    printNorthLabel(terrainWidth);
    drawTerrain(terrainWidth, terrainHeight, mowersFinalPositions);
    printSouthLabel(terrainWidth);
  }

  private int getTerrainWidth(String terrainDimensions) {
    return Integer.parseInt(terrainDimensions.split(" ")[0]);
  }

  private int getTerrainHeight(String terrainDimensions) {
    return Integer.parseInt(terrainDimensions.split(" ")[1]);
  }

  private void printNorthLabel(int terrainWidth) {
    printLabel("N", terrainWidth);
  }

  private void printSouthLabel(int terrainWidth) {
    printLabel("S", terrainWidth);
  }

  private void printLabel(String label, int terrainWidth) {
    for (int i = 0; i < 2 * terrainWidth; i++) {
      System.out.print(" ");
    }
    System.out.println(label);
  }

  private void drawTerrain(
      int terrainWidth, int terrainHeight, List<PositionResponseDTO> mowersFinalPositions) {
    for (int y = terrainHeight - 1; y >= 0; y--) {
      printRow(y, terrainWidth, terrainHeight, mowersFinalPositions);
    }
  }

  private void printRow(
      int y, int terrainWidth, int terrainHeight, List<PositionResponseDTO> mowersFinalPositions) {
    if (y == (terrainHeight - 1) / 2) {
      System.out.print("W ");
    } else {
      System.out.print("  ");
    }

    for (int x = 0; x < terrainWidth; x++) {
      printCell(x, y, mowersFinalPositions);
    }

    if (y == (terrainHeight - 1) / 2) {
      System.out.println("| E");
    } else {
      System.out.println("|");
    }
  }

  private void printCell(int x, int y, List<PositionResponseDTO> mowersFinalPositions) {
    var mowersAtPosition = getMowersAtPosition(x, y, mowersFinalPositions);

    if (!mowersAtPosition.isEmpty()) {
      for (var mower : mowersAtPosition) {
        System.out.print("| " + getDirectionSymbol(Position.fromChar(mower.getPosition())) + " ");
      }
    } else {
      System.out.print("|   ");
    }
  }

  private List<PositionResponseDTO> getMowersAtPosition(
      int x, int y, List<PositionResponseDTO> mowersFinalPositions) {
    return mowersFinalPositions.stream()
        .filter(mower -> mower.getX() == x + 1 && mower.getY() == y + 1)
        .toList();
  }

  private String getDirectionSymbol(Position position) {
    return switch (position) {
      case NORTH -> "^";
      case EAST -> ">";
      case SOUTH -> "v";
      case WEST -> "<";
    };
  }
}
