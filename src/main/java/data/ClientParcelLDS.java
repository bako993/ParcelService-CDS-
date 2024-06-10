package data;

import entities.DeliveryPackage;

import java.util.*;

/**
 * Custom Linear Data Structure.
 * This is our customer linear data structure which will intake the client id and all delivery packages of the specific client.
 * It will be used to produce the top 10 recipients for a certain period.
 **/
public class ClientParcelLDS extends HashMap<Integer, ArrayList<DeliveryPackage>> {
    public HashMap<Integer, ArrayList<DeliveryPackage>> clientParcelLDS = new HashMap<>();

    public HashMap<Integer, ArrayList<DeliveryPackage>> getClientParcelLDS() {
        sortByValue();
        return clientParcelLDS;
    }

    public ArrayList<DeliveryPackage> get(Integer elementToGet) {
        return clientParcelLDS.get(elementToGet);
    }

    public void add(DeliveryPackage deliveryPackage) {
        if (clientParcelLDS.get(deliveryPackage.getClientId()) == null) {
            ArrayList<DeliveryPackage> deliveryPackages = new ArrayList<>();
            deliveryPackages.add(deliveryPackage);
            clientParcelLDS.put(deliveryPackage.getClientId(),deliveryPackages);
        } else {
            ArrayList<DeliveryPackage> deliveryPackages = clientParcelLDS.get(deliveryPackage.getClientId());
            deliveryPackages.add(deliveryPackage);
            clientParcelLDS.put(deliveryPackage.getClientId(),deliveryPackages);
        }
    }

    public void remove(Integer elementToRemove) {
        clientParcelLDS.remove(elementToRemove);
    }

    public void sortByValue() {
        List<Map.Entry<Integer, ArrayList<DeliveryPackage>>> sortHelperList = new LinkedList<Map.Entry<Integer, ArrayList<DeliveryPackage>>>(clientParcelLDS.entrySet());
        sortHelperList.sort(new Comparator<Map.Entry<Integer, ArrayList<DeliveryPackage>>>() {
            @Override
            public int compare(Map.Entry<Integer, ArrayList<DeliveryPackage>> o1, Map.Entry<Integer, ArrayList<DeliveryPackage>> o2) {
                return -Integer.compare(o1.getValue().size(), o2.getValue().size());
            }
        });

        HashMap<Integer, ArrayList<DeliveryPackage>> sortedHashMap = new LinkedHashMap<Integer, ArrayList<DeliveryPackage>>();
        for (Map.Entry<Integer, ArrayList<DeliveryPackage>> listEntry : sortHelperList) {
            sortedHashMap.put(listEntry.getKey(), listEntry.getValue());
        }

        clientParcelLDS = sortedHashMap;
    }
}
