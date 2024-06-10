package search;

import entities.Client;
import entities.DeliveryPackage;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class LinearSearch {

    /**
     * Linear search for package id in the ArrayList package data.
     **/
    public void linearSearchInPackagesForPackageId(int searchedPackageId, ArrayList<DeliveryPackage> receivedDeliveryPackages) {
        boolean foundPackage = false;

        for (int i = 0; i < receivedDeliveryPackages.size(); i++) {
            if (receivedDeliveryPackages.get(i).getPackageId() == searchedPackageId) {
                System.out.println("The searched value (" + receivedDeliveryPackages.get(i).getPackageId() + ") has been found at position " + (i+1));
                foundPackage = true;
                break;
            }
        }

        if (!foundPackage) {
            System.out.println("The searched value has not been found!");
        }
    }

    /** Linear search for package id in the Array package data. **/
    public void linearSearchInPackagesForPackageId(int searchedPackageId, DeliveryPackage[] receivedDeliveryPackages) {
        boolean foundPackage = false;

        for (int i = 0; i < receivedDeliveryPackages.length; i++) {
            if (receivedDeliveryPackages[i].getPackageId() == searchedPackageId) {
                System.out.println("The searched value (" + searchedPackageId + ") has been found at position " + (i+1));
                foundPackage = true;
            }
        }

        if (!foundPackage) {
            System.out.println("The searched value (" + searchedPackageId + ") has not been found!");
        }
    }

    /** Linear search for package id in the PriorityQueue package data. **/
    public void linearSearchInPackagesForPackageId(int searchedPackageId, PriorityQueue<DeliveryPackage> receivedDeliveryPackages) {
        int searchIndex = 1;

        boolean foundPackage = false;

        while (!receivedDeliveryPackages.isEmpty()) {
            if (receivedDeliveryPackages.poll().getPackageId() == searchedPackageId) {
                System.out.println("The searched value (" + searchedPackageId + ") has been found at position " + searchIndex);
                foundPackage = true;
            }
            searchIndex++;
        }

        if (!foundPackage) {
            System.out.println("The searched value (" + searchedPackageId + ") has not been found!");
        }
    }

    /** Linear search for client id in the ArrayList package data. **/
    public Client linearSearchForClientId(int clientId,ArrayList<Client> clients) {
        Client foundClient = null;
        
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getClientId() == clientId) {
                System.out.println("the client with id : " + clientId + " has been found at position " + (i+1));
                foundClient = clients.get(i);
            }
        }
        
        if (foundClient == null) {
            System.out.println("there is no client with that id.");
        }
        
        return foundClient;
    }
}
