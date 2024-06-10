package avltrees.PackageAVLTree;

import entities.DeliveryPackage;

public class PackageNode implements Comparable<Integer> {
    public DeliveryPackage deliveryPackage;
    int deliveryPackageId;
    public PackageNode left;
    public PackageNode right;
    public int height;
    public PackageNode(DeliveryPackage deliveryPackage) {
        this.deliveryPackage = deliveryPackage;
        deliveryPackageId = deliveryPackage.getPackageId();
    }

    @Override
    public int compareTo(Integer id) {
        return Integer.compare(deliveryPackage.getPackageId(), id);
    }
}