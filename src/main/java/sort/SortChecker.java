package sort;

import entities.Client;
import entities.DeliveryPackage;

import java.util.ArrayList;

public class SortChecker {

    /** Check if Package ArrayList is sorted. Return boolean. **/
    public boolean isItSortedPackages(ArrayList<DeliveryPackage> packages) {
        for(int i = 1; i < packages.size(); i++) {
            if (packages.get(i - 1).getPackageId() > packages.get(i).getPackageId()) return false;
        }
        return true;
    }

    /** Check if Client ArrayList is sorted. Return boolean. **/
    public boolean isItSortedClients(ArrayList<Client> packages) {
        for(int i = 1; i < packages.size(); i++) {
            if (packages.get(i - 1).getClientId() > packages.get(i).getClientId()) return false;
        }
        return true;
    }

}
