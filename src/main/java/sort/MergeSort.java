package sort;

import entities.DeliveryPackage;

import java.util.ArrayList;

/**
 * MergeSort function.
 * We have used JavaPoint to understand and implement this sorting algorithm.
 * Reference: https://www.javatpoint.com/merge-sort
 */
public class MergeSort {
    public ArrayList<DeliveryPackage> doMergeSort(ArrayList<DeliveryPackage> unsortedPackages){
        divide(unsortedPackages, 0, unsortedPackages.size()-1);
        return unsortedPackages;
    }

    public void divide(ArrayList<DeliveryPackage> unsortedPackages, int startIndex,int endIndex){
        if(startIndex < endIndex && (endIndex - startIndex) >= 1){
            int mid = (endIndex + startIndex)/2;
            divide(unsortedPackages,startIndex, mid);
            divide(unsortedPackages, mid + 1, endIndex);

            merger(unsortedPackages, startIndex, mid, endIndex);
        }
    }

    public void merger(ArrayList<DeliveryPackage> unsortedPackages, int startIndex,int midIndex,int endIndex){
        ArrayList<DeliveryPackage> mergedSortedArray = new ArrayList<>();

        int leftIndex = startIndex;
        int rightIndex = midIndex+1;

        while(leftIndex<=midIndex && rightIndex<=endIndex){
            if(unsortedPackages.get(leftIndex).getPackageId() <= unsortedPackages.get(rightIndex).getPackageId()){
                mergedSortedArray.add(unsortedPackages.get(leftIndex));
                leftIndex++;
            }else{
                mergedSortedArray.add(unsortedPackages.get(rightIndex));
                rightIndex++;
            }
        }

        while(leftIndex<=midIndex){
            mergedSortedArray.add(unsortedPackages.get(leftIndex));
            leftIndex++;
        }

        while(rightIndex<=endIndex){
            mergedSortedArray.add(unsortedPackages.get(rightIndex));
            rightIndex++;
        }

        int i = 0;
        int j = startIndex;

        while(i<mergedSortedArray.size()){
            unsortedPackages.set(j, mergedSortedArray.get(i++));
            j++;
        }
    }
}
