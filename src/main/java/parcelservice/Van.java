package parcelservice;

import entities.DeliveryPackage;

import java.util.Stack;


public class Van {
    private final int length;
    private final int width;
    private Stack<DeliveryPackage> loadedPackages;

    public Van() {
        this.length = 150;
        this.width = 100;
    }

    public static Van newVan(){
        Van van = new Van();
        van.setLoadedPackages(new Stack<>());

        return van;
    }

    public void setLoadedPackages(Stack<DeliveryPackage> loadedPackages) {
        this.loadedPackages = loadedPackages;
    }

    public int vanArea(){
        return length * width;
    }

    public Stack<DeliveryPackage> getLoadedPackages() {
        return loadedPackages;
    }


    @Override
    public String toString() {
        return "New Van: " + loadedPackages.size() + " orders.";
    }
}
