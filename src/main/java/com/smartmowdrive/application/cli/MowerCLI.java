package com.smartmowdrive.application.cli;

import com.smartmowdrive.application.dto.InstructionsRequestDTO;
import com.smartmowdrive.application.dto.MowerInstructionsDTO;
import com.smartmowdrive.application.dto.PositionResponseDTO;
import com.smartmowdrive.application.service.MowerInstructionsProcessingService;
import com.smartmowdrive.domain.model.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Profile("cli")
@RequiredArgsConstructor
@Component
public class MowerCLI implements CommandLineRunner {

    private final MowerInstructionsProcessingService mowerInstructionsProcessingService;

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            try {

                System.out.println("===========================================");
                System.out.println("|        SMART MOW DRIVE 1.0 - CLI        |");
                System.out.println("|      SEAT:CODE Technical Challenge      |");
                System.out.println("|        by Luis M.M. @martinezelx        |");
                System.out.println("===========================================");

                System.out.println("-------------------------------------------");
                System.out.println("| Enter terrain dimensions:               |");
                System.out.println("| Example: '5 5'                          |");
                System.out.println("-------------------------------------------");
                String terrainDimensions = scanner.nextLine();

                System.out.println("-------------------------------------------");
                System.out.println("| How many mowers do you want to operate? |");
                System.out.println("-------------------------------------------");
                int numberOfMowers = scanner.nextInt();
                scanner.nextLine();

                List<MowerInstructionsDTO> mowersInstructions = new ArrayList<>();

                for (int i = 0; i < numberOfMowers; i++) {
                    System.out.println("---------------------------------------------------");
                    System.out.println("| Enter mower " + (i + 1) + " ID:");
                    System.out.println("| Example: '3fa85f64-5717-4562-b3fc-2c963f66afa6'");
                    System.out.println("---------------------------------------------------");
                    String id = scanner.nextLine();

                    System.out.println("-------------------------------------------------");
                    System.out.println("| Enter mower " + (i + 1) + " initial position:");
                    System.out.println("| Example: '1 2 N'");
                    System.out.println("-------------------------------------------------");
                    String mowerPosition = scanner.nextLine();

                    System.out.println("---------------------------------------------");
                    System.out.println("| Enter mower " + (i + 1) + " instructions:");
                    System.out.println("| Example: 'LMLMLMLMM'");
                    System.out.println("---------------------------------------------");
                    String instructions = scanner.nextLine();

                    mowersInstructions.add(new MowerInstructionsDTO(UUID.fromString(id), mowerPosition, instructions));
                }

                InstructionsRequestDTO request = new InstructionsRequestDTO(terrainDimensions, mowersInstructions);
                var mowersFinalPositions = mowerInstructionsProcessingService.processInstructions(request);

                System.out.println("================================================================================");
                System.out.println("| Mowers data:");
                mowersFinalPositions.forEach(mower -> System.out.println("| " + mower + " |"));
                System.out.println("================================================================================");

                System.out.println("--------------------------------------------------------------------------------");

                drawInstructions(terrainDimensions, mowersFinalPositions);

                System.out.println("Do you want to exit? (yes/no)");
                String exitResponse = scanner.nextLine();
                exit = "yes".equalsIgnoreCase(exitResponse);

            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                System.out.println("Do you want to continue? (yes/no)");
                String continueResponse = scanner.nextLine();
                exit = !"yes".equalsIgnoreCase(continueResponse);
            }
        }
    }

    private void drawInstructions(String terrainDimensions, List<PositionResponseDTO> mowersFinalPositions) {
        // Calculate the number of spaces to print before the "N" and "S" labels
        int numSpaces = 2 * Integer.parseInt(terrainDimensions.split(" ")[0]);

        // Print the North label at the center
        for (int i = 0; i < numSpaces; i++) {
            System.out.print(" ");
        }
        System.out.println("N");

        // Draw the terrain
        for (int y = Integer.parseInt(terrainDimensions.split(" ")[1]) - 1; y >= 0; y--) {
            if (y == (Integer.parseInt(terrainDimensions.split(" ")[1]) - 1) / 2) {
                System.out.print("W ");
            } else {
                System.out.print("  ");
            }

            for (int x = 0; x < Integer.parseInt(terrainDimensions.split(" ")[0]); x++) {
                int finalX = x;
                int finalY = y;
                var mowerAtPosition = mowersFinalPositions.stream()
                        .filter(mower -> mower.getX() == finalX && mower.getY() == finalY)
                        .findFirst();

                if (mowerAtPosition.isPresent()) {
                    // Print the mower and its direction
                    System.out.print("| " + getDirectionSymbol(Position.fromChar(mowerAtPosition.get().getPosition())) + " ");
                } else {
                    // Print an empty cell
                    System.out.print("|   ");
                }
            }

            if (y == (Integer.parseInt(terrainDimensions.split(" ")[1]) - 1) / 2) {
                System.out.println("| E");
            } else {
                System.out.println("|");
            }
        }

        // Print the South label at the center
        for (int i = 0; i < numSpaces; i++) {
            System.out.print(" ");
        }
        System.out.println("S");
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