package parcelservice;

import data.ClientParcelLDS;
import data.DataReader;
import data.DataTransformer;
import entities.Client;
import entities.DeliveryPackage;
import graphs.Graph;
import graphs.Node;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParcelService {
    public DataReader dataReader = new DataReader();
    public DataTransformer dataTransformer = new DataTransformer();
    public static ArrayList<DeliveryPackage> deliveryPackages;
    public static ArrayList<Client> clients;

    public ParcelService() {
        deliveryPackages = dataReader.readPackagesFromCsvReturnArrayList();
        clients = dataReader.readClientFromCsvReturnArrayList();
    }

    public void findPackageById() {
        HashMap<Integer, DeliveryPackage> deliveryPackageHashMap = dataTransformer.convertPackageArrayListToHashMap(deliveryPackages);

        System.out.print("Which package id are you trying to find (Id from " + deliveryPackages.get(0).getPackageId() + " to "
                + deliveryPackages.get(deliveryPackages.size() - 1).getPackageId() + "): ");

        Scanner inputReader = new Scanner(System.in);
        int packageId = inputReader.nextInt();

        System.out.println();

        Pattern pattern = Pattern.compile("[0-9]{5}", Pattern.UNICODE_CASE);
        Matcher matcher = pattern.matcher(Integer.toString(packageId));
        boolean matchFound = matcher.find();

        if (matchFound) {
            Instant start = Instant.now();
            DeliveryPackage deliveryPackage = deliveryPackageHashMap.get(packageId);
            Instant end = Instant.now();

            if (deliveryPackage != null) {
                System.out.println("We have found your package!");
                System.out.println("Finding a parcel takes " + Duration.between(start, end).toMillis() + " ms.");
                System.out.println(deliveryPackage);
            } else {
                System.out.println("Delivery package does not exists in our database.");
            }
        } else {
            System.out.println("Invalid input! Please try again.");
        }
    }

    public void showTopRecipients() {
        System.out.print("Fill start date (Format: dd-MM-yyyy): ");

        Scanner inputReader = new Scanner(System.in);
        String firstDateString = inputReader.nextLine();

        System.out.print("Fill end date (Format: dd-MM-yyyy): ");
        String endDateString = inputReader.nextLine();

        Instant start = Instant.now();

        if (Pattern.matches("([1-9]|[1-3][0-9])-([0-9]|[0-9][0-9])-[0-9]{4}", firstDateString) && Pattern.matches("([1-9]|[1-3][0-9])-([0-9]|[0-9][0-9])-[0-9]{4}", endDateString)) {

                String[] startDateParts = firstDateString.split("-");
                LocalDate startDate = LocalDate.of(Integer.parseInt(startDateParts[2]), Integer.parseInt(startDateParts[1]), Integer.parseInt(startDateParts[0]));

                String[] endDateParts = endDateString.split("-");
                LocalDate endDate = LocalDate.of(Integer.parseInt(endDateParts[2]), Integer.parseInt(endDateParts[1]), Integer.parseInt(endDateParts[0]));


                if (startDate.isAfter(endDate) || endDate.isBefore(startDate)) {
                    System.out.print("Incorrect date values.");
                    System.out.println(" Please try again.");
                } else {
                    ClientParcelLDS clientParcelLDS = new ClientParcelLDS();

                    for (DeliveryPackage deliveryPackage : deliveryPackages) {
                        if (deliveryPackage.getEntryDate().isAfter(startDate) && deliveryPackage.getEntryDate().isBefore(endDate)) {
                            clientParcelLDS.add(deliveryPackage);
                        }
                    }

                    System.out.println(" ");

                    int limit = 0;
                    for (Map.Entry<Integer, ArrayList<DeliveryPackage>> entry : clientParcelLDS.getClientParcelLDS().entrySet()) {
                        if (limit == 0) System.out.println("*** Client with id " + entry.getKey() + " ordered " + entry.getValue().size() + " packages. ***");
                        if (limit == 1) System.out.println("** Client with id " + entry.getKey() + " ordered " + entry.getValue().size() + " packages. **");
                        if (limit == 2) System.out.println("* Client with id " + entry.getKey() + " ordered " + entry.getValue().size() + " packages. *");
                        if (limit > 2) System.out.println("Client with id " + entry.getKey() + " ordered " + entry.getValue().size() + " packages.");

                        limit++;

                        if (limit == 10) break;

                    }

                    Instant end = Instant.now();

                    System.out.println("\nExporting Top 10 takes " + Duration.between(start,end).toMillis() + " ms.\n");
                }
        } else {
            System.out.println("\nThe input date(s) are out of bounds or the input is invalid. Please try again.\n");
        }
    }

    public void loadPackagesInTheVan() {
        System.out.print("Which date you want to load packages (Format: dd-MM-yyyy): ");
        Scanner inputReader = new Scanner(System.in);
        String date = inputReader.nextLine();

        if (Pattern.matches("([1-9]|[1-3][0-9])-([0-9]|[0-9][0-9])-[0-9]{4}", date)) {
            String[] startDateParts = date.split("-");
            LocalDate startDate = LocalDate.of(Integer.parseInt(startDateParts[2]), Integer.parseInt(startDateParts[1]), Integer.parseInt(startDateParts[0]));

            ArrayList<DeliveryPackage> packagesForTheDate = new ArrayList<>();
            for (DeliveryPackage deliveryPackage : deliveryPackages) {
                if (startDate.equals(deliveryPackage.getEntryDate())) {
                    packagesForTheDate.add(deliveryPackage);
                }
            }

            if (packagesForTheDate.size() == 0) System.out.println("There are no packages for that day.");

            ArrayList<Van> vansForDay = new ArrayList<>();
            Stack<DeliveryPackage> packagesInVan = new Stack<>();

            Instant start = Instant.now();

            Van van = new Van();
            int vanArea = van.vanArea();

            for (DeliveryPackage deliveryPackage : packagesForTheDate) {
                if (vanArea == van.vanArea()) {
                    van = new Van();
                    packagesInVan = new Stack<>();
                }

                if (vanArea - deliveryPackage.deliveryPackageArea() <= 0) {
                    van.setLoadedPackages(packagesInVan);
                    vansForDay.add(van);

                    van = new Van();
                    packagesInVan = new Stack<>();
                    vanArea = van.vanArea();
                }


                vanArea = vanArea - deliveryPackage.deliveryPackageArea();
                packagesInVan.push(deliveryPackage);
            }

            Van lastVan = new Van();
            lastVan.setLoadedPackages(packagesInVan);
            vansForDay.add(lastVan);


            System.out.println(" ");

            Instant end = Instant.now();

            int vanNum = 1;
            int totalLoad = 0;
            int totalNum = 0;
            for (Van vanForDay: vansForDay) {
                int packageLoad = 0;

                for (DeliveryPackage deliveryPackage: vanForDay.getLoadedPackages()) {
                    packageLoad += deliveryPackage.deliveryPackageArea();
                }

                System.out.print("#" + vanNum + " Van (" + packageLoad + "/" + vanForDay.vanArea() + ") ");
                System.out.print("contains " + vanForDay.getLoadedPackages().size() + " packages.\n");

                totalNum += vanForDay.getLoadedPackages().size();

                vanNum++;
                totalLoad += packageLoad;
            }

            System.out.println("\nThe total num of packages loaded: " + totalNum);
            System.out.println("The sum area of these packages: " + totalLoad + "\n");

            System.out.println("Loading a van for the day takes " + Duration.between(start, end).toMillis() + " ms.\n");
        } else {
            System.out.println("\nThere are no parcels for the specified date!\n");
        }
    }

    public void findShortestPath() {
//        Version 3: implementation of the graph with using addresses as a weight.
//
//        int nrOfNode = clients.size() + 1;
//        Graph graph = new Graph(nrOfNode);
//        ArrayList<Client> clientsForGraph = new ArrayList<>();
//        Client distributionCenter = new Client(0,"Distribution Center","DS",375,375);
//        clientsForGraph.add(distributionCenter);
//
//        clientsForGraph.addAll(clients);
//
//        Collections.sort(clientsForGraph);
//
//        for (int i = 0; i < clientsForGraph.size(); i++) {
//            graph.addNode(new Node(clientsForGraph.get(i).getClientId()));
//
//            for (int j = 0; j < clientsForGraph.size(); j++) {
//                if (j != i) {
//                    int x = clientsForGraph.get(i).getAddressX() - clientsForGraph.get(j).getAddressX();
//                    int y = clientsForGraph.get(i).getAddressY() - clientsForGraph.get(j).getAddressY();
//
//                    graph.addEdge(i, j, -(x + y));
//                }
//            }
//        }



//        Version 2: implementation of the graph with random weight.
//
//        for (int i = 0; i < clients.size(); i++) {
//            graph.addNode(new Node(clients.get(i).getClientId()));
//
//            for (int j = 0; j < clients.size(); j++) {
//                if (j != i) {
//                    Random random = new Random();
//                    graph.addEdge(i, j, random.nextInt(1,10));
//                }
//            }
//        }






        //Version 1: using dummy data to ease the understanding of the graph.
        // We chose to keep our dummy data because it is easier to understand the implementation and display of the graph.
        int nrOfNode = 10;
        Graph graph = new Graph(nrOfNode);

        graph.addNode(new Node(clients.get(0).getClientId()));
        graph.addNode(new Node(clients.get(1).getClientId()));
        graph.addNode(new Node(clients.get(2).getClientId()));
        graph.addNode(new Node(clients.get(3).getClientId()));
        graph.addNode(new Node(clients.get(4).getClientId()));
        graph.addNode(new Node(clients.get(5).getClientId()));
        graph.addNode(new Node(clients.get(6).getClientId()));
        graph.addNode(new Node(clients.get(7).getClientId()));
        graph.addNode(new Node(clients.get(8).getClientId()));
        graph.addNode(new Node(clients.get(9).getClientId()));

        graph.addEdge(0,1,4);
        graph.addEdge(0,7,8);
        graph.addEdge(1,2,8);
        graph.addEdge(7,8,7);
        graph.addEdge(1,7,9);
        graph.addEdge(7,6,1);
        graph.addEdge(6,8,6);
        graph.addEdge(2,8,2);
        graph.addEdge(2,5,4);
        graph.addEdge(2,3,7);
        graph.addEdge(6,5,2);
        graph.addEdge(3,5,3);
        graph.addEdge(3,4,9);
        graph.addEdge(4,5,5);
        graph.addEdge(7,9,2);

        // print the graph
        graph.print();
        System.out.println();

        // breadth first search method
        graph.bfs(0);
        System.out.println();

        // depth first search method
        graph.dfs(0);
        System.out.println();

        // prim algorithm
        graph.primAlgorithm(graph.matrix);
        System.out.println();

        // check if the nodes are connected
        System.out.println(graph.checkEdge(0, 1));
        System.out.println(graph.checkEdge(1, 0));
        System.out.println(graph.checkEdge(0, 5));
        System.out.println(graph.checkEdge(8, 6));
        System.out.println();

        // dijkstra algorithm
        graph.dijkstraAlgo(graph.matrix, 0);

        System.out.println();
    }
}
