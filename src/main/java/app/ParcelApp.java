package app;

import parcelservice.ParcelService;
import java.util.Scanner;

public class ParcelApp {
    public static void main(String[] arg) {
        ParcelService parcelService = new ParcelService();

        System.out.println("------------------------------------------------");
        System.out.println("Welcome to our Parcel Service Application (PSA).");
        System.out.println("------------------------------------------------\n");

        Scanner inputReader = new Scanner(System.in);

        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("1. Find delivery package by id");
            System.out.println("2. Top 10 recipients in a recent period");
            System.out.println("3. Efficient route");
            System.out.println("4. Calculate vans needed for day\n");

            System.out.print("Choose a process (1-4): ");
            int processChoice = inputReader.nextInt();

            System.out.println("  ");

            if (processChoice == 1) {
                parcelService.findPackageById();
            } else if (processChoice == 2) {
                parcelService.showTopRecipients();
            } else if (processChoice == 3) {
                parcelService.findShortestPath();
            } else if (processChoice == 4) {
                parcelService.loadPackagesInTheVan();
            }

            System.out.println("  ");

            System.out.print("Press 0 to exit, press 1 to continue: ");
            int exitValue = inputReader.nextInt();

            if (exitValue == 0) {
                System.out.println("\n\nThank you for using PSA.");
                keepRunning = false;
            } else {
                System.out.println("\n-----------------------------\n");
            }
        }

        inputReader.close();
    }
}
