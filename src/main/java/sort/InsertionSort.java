package sort;

import entities.Client;
import entities.DeliveryPackage;

import java.util.ArrayList;

public class InsertionSort {
    /** Insertion sort on unsorted packages. **/
    public ArrayList<DeliveryPackage> doInsertionSortPackages(ArrayList<DeliveryPackage> unsortedPackages) {
        for (int j = 1; j < unsortedPackages.size(); j++) {
            DeliveryPackage current = unsortedPackages.get(j);
            int i = j-1;
            while ((i >= 0) && ((unsortedPackages.get(i).getPackageId() > current.getPackageId()))) {
                unsortedPackages.set(i+1, unsortedPackages.get(i));
                i--;
            }
            unsortedPackages.set(i+1, current);
        }

        return unsortedPackages;
    }

    /** Insertion sort on unsorted clients. **/
    public void doInsertionSortClients(ArrayList<Client> unsortedClientAddresses) {
        for (int j = 1; j < unsortedClientAddresses.size(); j++) {
            Client current = unsortedClientAddresses.get(j);
            int i = j-1;
            while ((i >= 0) && ((unsortedClientAddresses.get(i).getClientId() > current.getClientId()))) {
                unsortedClientAddresses.set(i+1, unsortedClientAddresses.get(i));
                i--;
            }
            unsortedClientAddresses.set(i+1, current);
        }
    }
}
