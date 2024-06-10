package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PackageGenerator {
    int numberOfCustomers = 500;
    int numberOfPackages = 10000;
    List<String> surnames = getSetOfNames();
    Random random = new Random();
    List<Integer> userIds = new ArrayList<>();

    int day = 1;
    int month = 1;
    int year = 2021;

    public void setNumberOfPackages(int numberOfPackages) {
        this.numberOfPackages = numberOfPackages;
    }

    public void run() {
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/Clients.csv");
            myWriter.write("ClientID;Name;Initials;AddressX;AddressY\n");
            int currentId = random.nextInt(300000);
            for(int i = 0; i < numberOfCustomers; i++) {
                myWriter.write(currentId + ";" + getRandomName() + ";" + getRandomInitials() + ";" + random.nextInt(500) + ";" + random.nextInt(500));
                userIds.add(currentId);
                currentId += random.nextInt(10)+1;
                if(i < numberOfCustomers-1) {
                    myWriter.write("\n");
                }
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/Packages.csv");
            myWriter.write("PackageID;Length;Width;Height;Weight;EntryDate;ClientID\n");
            int currentId = random.nextInt(300000);
            for(int i = 0; i < numberOfPackages; i++) {
                myWriter.write(currentId + ";" + getRandomSize() + ";" + getRandomSize() + ";" + getRandomSize() + ";" + getRandomWeight() + ";" + getNextDate() + ";" + getRandomClient());
                currentId += random.nextInt(10)+1;
                if(i < numberOfPackages-1) {
                    myWriter.write("\n");
                }
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public int getRandomClient() {
        return userIds.get(random.nextInt(userIds.size()-1));
    }

    public String getNextDate() {
        if(random.nextInt(100) % 2 == 0) {
            day++;
            if((day > 31 && (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month ==12 ))
                    ||(day > 30 && (month == 4 || month == 6 || month == 9 || month == 11))
                    ||(day > 28 && month == 2)) {
                day = 1;
                month++;
            }
            if(month > 12) {
                day = 1;
                month = 1;
                year++;
            }
        }
        return day + "-" + month + "-" + year;
    }

    public int getRandomSize() {
        int randomSize = (random.nextInt(9) +1) * 10;
        if(random.nextInt(2) == 1) {
            randomSize += 5;
        }
        return randomSize;
    }

    public String getRandomWeight() {
        return String.format(Locale.US, "%.3f", random.nextDouble(10.0));
    }

    public String getRandomName() {
        return surnames.get(random.nextInt(surnames.size()-1));
    }

    public String getRandomInitials() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String initials = "";
        for(int i = 0; i < random.nextInt(4)+1; i++) {
            initials += alphabet.charAt(random.nextInt(alphabet.length()-1)) + ".";
        }
        return initials;
    }

    public List<String> getSetOfNames() {
        List<String> names = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/names.txt"));
            while (scanner.hasNextLine()) {
                names.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return names;
    }
}

