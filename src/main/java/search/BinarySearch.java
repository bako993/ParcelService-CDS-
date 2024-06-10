package search;

import entities.Client;
import entities.DeliveryPackage;
import java.util.ArrayList;

public class BinarySearch {

    /** Binary search for package id in the ArrayList package data. **/
    public void binarySearchInPackagesForPackageId(int searchedPackageId, ArrayList<DeliveryPackage> receivedDeliveryPackages) {

        int left = 0;
        int seqLength = receivedDeliveryPackages.size();
        int right = seqLength - 1;
        int middle = ( left + right ) / 2;

        while (receivedDeliveryPackages.get(middle).getPackageId() != searchedPackageId && left < right) {
            if (receivedDeliveryPackages.get(middle).getPackageId() < searchedPackageId) {
                left = middle + 1;
            } else if (receivedDeliveryPackages.get(middle).getPackageId() > searchedPackageId) {
                right = middle - 1;
            }
            middle = (left + right) / 2;
        }

        if (receivedDeliveryPackages.get(middle).getPackageId() == searchedPackageId) {
            System.out.println("The searched value (" + receivedDeliveryPackages.get(middle).getPackageId() + ") has been found at position " + (middle + 1));
        } else {
            System.out.println("The searched value has not been found!");
        }
    }

    /** Binary search for client id in the ArrayList package data. **/
    public void binarySearchForClientId(int clientId, ArrayList<Client> clientArrayList) {

        int left = 0;
        int right = clientArrayList.size()-1;
        int middle = (left+right)/2;

        while (clientArrayList.get(middle).getClientId() != clientId & left<right) {
            if (clientArrayList.get(middle).getClientId() < clientId) {
                left=middle+1;

            }else if (clientArrayList.get(middle).getClientId() > clientId) {
                right=middle-1;

            }
            middle = (left+right)/2;
        }
        if (clientArrayList.get(middle).getClientId() == clientId) {
            System.out.println("The searched value (" + clientId + ") has been found at position " + (middle + 1));
        } else {
            System.out.println("The searched value has not been found.");
        }
    }





}
