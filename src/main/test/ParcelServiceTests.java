import avltrees.PackageAVLTree.PackageAVLTree;
import data.DataReader;
import data.DataTransformer;
import data.PackageGenerator;
import entities.DeliveryPackage;
import search.BinarySearch;
import search.LinearSearch;
import sort.InsertionSort;
import sort.MergeSort;
import sort.SelectionSort;
import sort.SortChecker;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class ParcelServiceTests {
    public static void main(String[] arg) {
        testingAvlWebGraphvizPrint();
    }

    public static void testingAvlWebGraphvizPrint() {
        DataReader dataReader = new DataReader();
        ArrayList<DeliveryPackage> deliveryPackages = dataReader.readPackagesFromCsvReturnArrayList();

        PackageAVLTree packageAVLTree = new PackageAVLTree();
        for (int i = 0; i < 7; i++) {
            packageAVLTree.insert(deliveryPackages.get(i));
        }
        System.out.println("AvlTree after insertion of elements.");
        packageAVLTree.printTree(packageAVLTree.getRoot());

        System.out.println("\nAvlTree after manual insertion of elements.");
        packageAVLTree.insert(deliveryPackages.get(8));
        packageAVLTree.insert(deliveryPackages.get(9));
        packageAVLTree.printTree(packageAVLTree.getRoot());

        System.out.println("\nAvlTree after deletion of elements.");
        packageAVLTree.delete(packageAVLTree.find(260651));
        packageAVLTree.printTree(packageAVLTree.getRoot());
    }

    public static void testLinearAndBinarySearchSpeed(int searchedValue) {

        System.out.println("");

        DataReader dataReader = new DataReader();
        DataTransformer dataTransformer = new DataTransformer();

        ArrayList<DeliveryPackage> deliveryPackages = dataReader.readPackagesFromCsvReturnArrayList();

        SortChecker sortChecker = new SortChecker();
        if (sortChecker.isItSortedPackages(deliveryPackages)) {
            System.out.println("There are " + deliveryPackages.size() + " sorted packages.");
        }

        System.out.println("");

        LinearSearch linearSearch = new LinearSearch();

        System.out.println("First element: " + deliveryPackages.get(0).getPackageId());
        System.out.println("Middle element: " + deliveryPackages.get((deliveryPackages.size()-1)/2).getPackageId());
        System.out.println("Last element: " + deliveryPackages.get(deliveryPackages.size()-1).getPackageId());

        System.out.println("");

        long startLS = System.nanoTime();
        linearSearch.linearSearchInPackagesForPackageId(searchedValue,deliveryPackages);
        long finishLS = System.nanoTime();

        long timeLS = finishLS - startLS;
        System.out.println("Finding a delivery package with LINEAR SEARCH takes " + timeLS + " nanos.");

        System.out.println("");

        BinarySearch binarySearch = new BinarySearch();

        long startBS = System.nanoTime();
        binarySearch.binarySearchInPackagesForPackageId(searchedValue,deliveryPackages);
        long finishBS = System.nanoTime();

        long timeBS = finishBS - startBS;
        System.out.println("Finding a delivery package with BINARY SEARCH takes " + timeBS + " nanos.");

        System.out.println("");

        HashMap<Integer, DeliveryPackage> packageHashMap = dataTransformer.convertPackageArrayListToHashMap(deliveryPackages);

        long startHM = System.nanoTime();
        packageHashMap.get(searchedValue);
        long finishHM = System.nanoTime();

        long timeHM = finishHM - startHM;
        System.out.println("Finding a delivery package with HASHMAP takes " + timeHM + " nanos.");

        System.out.println("");

        PackageAVLTree packageAVLTree = new PackageAVLTree();

        for (DeliveryPackage deliveryPackage: deliveryPackages) {
            packageAVLTree.insert(deliveryPackage);
        }

        long startAVL = System.nanoTime();
        packageAVLTree.find(searchedValue);
        long finishAVL = System.nanoTime();

        if (packageAVLTree.find(searchedValue).getPackageId() == searchedValue) {
            System.out.println("The searched value has been found.");
            System.out.println(packageAVLTree.find(searchedValue));
        } else {
            System.out.println("The searched value has NOT been found.");
        }
        long timeAVL = finishAVL - startAVL;
        System.out.println("Finding a delivery package with PackageAVLTree search takes " + timeAVL + " nanos.");

    }

    public static void testSortingAlgorithms() {
        DataReader dataReader = new DataReader();

        PackageGenerator packageGenerator = new PackageGenerator();
        packageGenerator.setNumberOfPackages(20000);
        packageGenerator.run();

        ArrayList<DeliveryPackage> deliveryPackages = dataReader.readPackagesFromCsvReturnArrayList();

        Collections.shuffle(deliveryPackages);

        SortChecker sortChecker = new SortChecker();
        if (!sortChecker.isItSortedPackages(deliveryPackages)) {
            System.out.println("There are " + deliveryPackages.size() + " waiting to be sorted.");
        }

        System.out.println("");

        InsertionSort insertionSort = new InsertionSort();
        Instant startIS = Instant.now();
        deliveryPackages = insertionSort.doInsertionSortPackages(deliveryPackages);
        Instant finishIS = Instant.now();

        long timeLS = Duration.between(startIS, finishIS).toMillis();
        System.out.print("Insertion sort (");
        if (sortChecker.isItSortedPackages(deliveryPackages)) {
            System.out.print("SORTED");
        }
        System.out.print(") " + timeLS + " ms.\n");

        Collections.shuffle(deliveryPackages);

        SelectionSort selectionSort = new SelectionSort();
        Instant startSS = Instant.now();
        deliveryPackages = selectionSort.doSelectionSort(deliveryPackages);
        Instant finishSS = Instant.now();

        long timeSS = Duration.between(startSS, finishSS).toMillis();
        System.out.print("Selection sort (");
        if (sortChecker.isItSortedPackages(deliveryPackages)) {
            System.out.print("SORTED");
        }
        System.out.print(") " + timeSS + " ms.\n");

        Collections.shuffle(deliveryPackages);

        MergeSort mergeSort = new MergeSort();
        Instant startMS = Instant.now();
        deliveryPackages = mergeSort.doMergeSort(deliveryPackages);
        Instant finishMS = Instant.now();

        long timeMS = Duration.between(startMS, finishMS).toMillis();
        System.out.print("Merge sort (");
        if (sortChecker.isItSortedPackages(deliveryPackages)) {
            System.out.print("SORTED");
        }
        System.out.print(") " + timeMS + " ms.\n");

    }

}
