package data;

import entities.Client;
import entities.DeliveryPackage;

import java.util.*;

public class DataTransformer {

    /** Convert Package ArrayList to HashMap. **/
    public HashMap<Integer, DeliveryPackage> convertPackageArrayListToHashMap(ArrayList<DeliveryPackage> packages) {
        HashMap<Integer,DeliveryPackage> packagesHashMap = new HashMap<>();

        for (DeliveryPackage deliveryPackage : packages) {
            packagesHashMap.put(deliveryPackage.getPackageId(),deliveryPackage);
        }

        return packagesHashMap;
    }

    /** Convert Client ArrayList to HashMap. **/
    public HashMap<Integer, Client> convertClientArrayListToHashMap(ArrayList<Client> clients) {
        HashMap<Integer,Client> map = new HashMap<>();

        for (Client  client : clients) {
            map.put(client.getClientId(),client);
        }

        return map;
    }

    /** Convert Package ArrayList to Array. **/
    public DeliveryPackage[] readPackagesFromCsvReturnArray(ArrayList<DeliveryPackage> packages) {
        DeliveryPackage[] arrayDeliveryPackages = new DeliveryPackage[packages.size()];

        for (int i = 0; i < packages.size(); i++) {
            arrayDeliveryPackages[i] = packages.get(i);
        }

        return arrayDeliveryPackages;
    }

    /** Convert Package ArrayList to PriorityQueue. **/
    public PriorityQueue<DeliveryPackage> readPackagesFromCsvReturnPriorityQueue(ArrayList<DeliveryPackage> packages) {
        PriorityQueue<DeliveryPackage> priorityQueueDeliveryPackages = new PriorityQueue<>((deliveryPackage1, deliveryPackage2) -> {
            if (deliveryPackage1.getEntryDate().isBefore(deliveryPackage2.getEntryDate())) {
                return -1;
            } else if (deliveryPackage1.getEntryDate().isAfter(deliveryPackage2.getEntryDate())) {
                return 1;
            } else {
                return Integer.compare(deliveryPackage1.getPackageId(), deliveryPackage2.getPackageId());
            }
        });

        priorityQueueDeliveryPackages.addAll(packages);

        return priorityQueueDeliveryPackages;
    }
}
