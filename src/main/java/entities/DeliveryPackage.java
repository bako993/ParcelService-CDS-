package entities;

import java.time.LocalDate;
import java.util.*;

public class DeliveryPackage implements Comparable<DeliveryPackage> {
    private int packageId;
    private int length;
    private int breadth;
    private int height;
    private double weight;
    private LocalDate entryDate;
    private int clientId;


    public DeliveryPackage(int packageId, int length, int breadth, int height, double weight, LocalDate entryDate, int clientId) {

        this.packageId = packageId;
        this.length = length;
        this.breadth = breadth;
        this.height = height;
        this.weight = weight;
        this.entryDate = entryDate;
        this.clientId = clientId;
    }

    public int deliveryPackageArea() {
        if (height >= length  && height >= breadth) {
            return length * breadth;
        } else if (length >= height && length >= breadth) {
            return height * breadth;
        } else if (breadth >= length && breadth >= height) {
            return length * height;
        }
        return breadth * length;
    }

    public int getPackageId() {
        return packageId;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getBreadth() {
        return breadth;
    }

    public void setBreadth(int breadth) {
        this.breadth = breadth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Package Id: " + packageId +
                ", length: " + length +
                ", breadth: " + breadth +
                ", height: " + height +
                ", weight: " + weight +
                ", entry date: " + entryDate +
                ", clientId: " + clientId + ".";
    }

    @Override
    public int compareTo(DeliveryPackage deliveryPackage) {
        return Integer.compare(getPackageId(), deliveryPackage.getPackageId());
    }
}
