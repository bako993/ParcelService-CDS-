package data;

import entities.Client;
import entities.DeliveryPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;

public class DataReader {


    /** Read, shuffle and save packages from Packages.csv, return ArrayList. **/
    public ArrayList<DeliveryPackage> readPackagesFromCsvReturnArrayList() {
        ArrayList<DeliveryPackage> arrayListDeliveryPackages = new ArrayList<>();

        try {
            Scanner fileReader = new Scanner(new File("src/main/resources/Packages.csv"));
            fileReader.nextLine();
            while (fileReader.hasNext()) {
                String lineInFile = fileReader.nextLine();
                String[] lineParts = lineInFile.split(";");

                int packageId = Integer.parseInt(lineParts[0]);
                int length = Integer.parseInt(lineParts[1]);
                int breadth = Integer.parseInt(lineParts[2]);
                int height = Integer.parseInt(lineParts[3]);
                double weight = Double.parseDouble(lineParts[4]);

                String[] parts = lineParts[5].split("-");
                LocalDate entryDate = LocalDate.of(Integer.parseInt(parts[2]), Integer.parseInt(parts[1]), Integer.parseInt(parts[0]));

                int clientId = Integer.parseInt(lineParts[6]);

                DeliveryPackage aDeliveryPackage = new DeliveryPackage(
                        packageId,
                        length,
                        breadth,
                        height,
                        weight,
                        entryDate,
                        clientId
                );

                arrayListDeliveryPackages.add(aDeliveryPackage);
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }

        Collections.sort(arrayListDeliveryPackages);
        return arrayListDeliveryPackages;
    }

    /** Read, shuffle and save clients from Clients.csv, return ArrayList. **/
    public ArrayList<Client> readClientFromCsvReturnArrayList() {
        ArrayList<Client> clients = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/Clients.csv"));
            scanner.nextLine();

            while (scanner.hasNext()) {
                String lineFromScanner = scanner.nextLine();
                String[] lineParts = lineFromScanner.split(";");

                int clientId = Integer.parseInt(lineParts[0]);
                String name = lineParts[1];
                String initial = lineParts[2];
                int addressX = Integer.parseInt(lineParts[3]);
                int addressY = Integer.parseInt(lineParts[4]);

                Client client = new Client(clientId, name, initial, addressX, addressY);
                clients.add(client);

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("cannot find csv file " + e);
        }

        Collections.sort(clients);
        return clients;
    }
}
