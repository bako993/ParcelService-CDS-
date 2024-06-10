package sort;

import entities.DeliveryPackage;

import java.util.ArrayList;

public class SelectionSort {

    /** Selection sort an unsorted packages. **/
    public ArrayList<DeliveryPackage> doSelectionSort(ArrayList<DeliveryPackage> unsortedPackages) {
        for (int i = 0; i < unsortedPackages.size() - 1; i++) {

            int position = i;
            for (int j = i + 1; j < unsortedPackages.size(); j++) {
                if (unsortedPackages.get(j).getPackageId() < unsortedPackages.get(position).getPackageId()) {
                    position = j;
                }
            }

            DeliveryPackage min = unsortedPackages.get(position);
            unsortedPackages.set(position, unsortedPackages.get(i));
            unsortedPackages.set(i, min);
        }

        return unsortedPackages;
    }
}
