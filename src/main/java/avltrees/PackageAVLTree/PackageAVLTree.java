package avltrees.PackageAVLTree;

import entities.DeliveryPackage;

public class PackageAVLTree {
    private PackageNode root;

    public DeliveryPackage find(int id) {
        PackageNode current = root;
        while (current != null) {
            if (current.deliveryPackage.getPackageId() == id) {
                break;
            }
            current = current.deliveryPackage.getPackageId() < id ? current.right : current.left;
        }
        assert current != null;
        return current.deliveryPackage;
    }

    public void insert(DeliveryPackage deliveryPackage) {
        root = insert(root, deliveryPackage);
    }

    public void delete(DeliveryPackage deliveryPackage) {
        root = delete(root, deliveryPackage);
    }

    public PackageNode getRoot() {
        return root;
    }

    public int height() {
        return root == null ? -1 : root.height;
    }

    private PackageNode insert(PackageNode node, DeliveryPackage deliveryPackage) {
        if (node == null) {
            return new PackageNode(deliveryPackage);
        } else if (node.deliveryPackage.getPackageId() > deliveryPackage.getPackageId()) {
            node.left = insert(node.left, deliveryPackage);
        } else if (node.deliveryPackage.getPackageId() < deliveryPackage.getPackageId()) {
            node.right = insert(node.right, deliveryPackage);
        } else {
            throw new RuntimeException("duplicate Key!");
        }
        return rebalance(node);
    }

    private PackageNode delete(PackageNode node, DeliveryPackage deliveryPackage) {
        if (node == null) {
            return node;
        } else if (node.deliveryPackage.getPackageId() > deliveryPackage.getPackageId()) {
            node.left = delete(node.left, deliveryPackage);
        } else if (node.deliveryPackage.getPackageId() < deliveryPackage.getPackageId()) {
            node.right = delete(node.right, deliveryPackage);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                PackageNode mostLeftChild = mostLeftChild(node.right);
                node.deliveryPackage = mostLeftChild.deliveryPackage;
                node.right = delete(node.right, node.deliveryPackage);
            }
        }

        if (node != null) {
            node = rebalance(node);
        }

        return node;
    }

    private PackageNode mostLeftChild(PackageNode node) {
        PackageNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private PackageNode rebalance(PackageNode z) {
        updateHeight(z);
        int balance = getBalance(z);
        if (balance > 1) {
            if (height(z.right.right) > height(z.right.left)) {
                z = rotateLeft(z);
            } else {
                z.right = rotateRight(z.right);
                z = rotateLeft(z);
            }
        } else if (balance < -1) {
            if (height(z.left.left) > height(z.left.right)) {
                z = rotateRight(z);
            } else {
                z.left = rotateLeft(z.left);
                z = rotateRight(z);
            }
        }
        return z;
    }

    private PackageNode rotateRight(PackageNode y) {
        PackageNode x = y.left;
        PackageNode z = x.right;
        x.right = y;
        y.left = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private PackageNode rotateLeft(PackageNode y) {
        PackageNode x = y.right;
        PackageNode z = x.left;
        x.left = y;
        y.right = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private void updateHeight(PackageNode n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    private int height(PackageNode n) {
        return n == null ? -1 : n.height;
    }

    public int getBalance(PackageNode n) {
        return (n == null) ? 0 : height(n.right) - height(n.left);
    }

    public void traverse(int traversMethod) {
        if (traversMethod == 1) {
            traverseInOrder(root);
        } else if (traversMethod == 2) {
            traversePreOrder(root);
        } else if (traversMethod == 3) {
            traversePostOrder(root);
        }
    }

    public void traverseInOrder(PackageNode node) {
        if (node != null) {
            traverseInOrder(node.left);
            System.out.println(node.deliveryPackage);
            traverseInOrder(node.right);
        }
    }

    public void traversePreOrder(PackageNode node) {
        if (node != null) {
            System.out.println(node.deliveryPackage);
            traversePreOrder(node.left);
            traversePreOrder(node.right);
        }
    }

    public void traversePostOrder(PackageNode node) {
        if (node != null) {
            traversePostOrder(node.left);
            traversePostOrder(node.right);
            System.out.println(node.deliveryPackage);
        }
    }

    public void printTree(PackageNode currPtr) {
        if (currPtr != null) {
            if (currPtr.left != null) System.out.println("\""+ currPtr.deliveryPackage.getPackageId() + "\"" + " -> " + "\""+ currPtr.left.deliveryPackage.getPackageId() + "\"");
            if (currPtr.right != null) System.out.println("\""+ currPtr.deliveryPackage.getPackageId() + "\"" + " -> " + "\""+ currPtr.right.deliveryPackage.getPackageId() + "\"");

            if (currPtr.left != null) printTree(currPtr.left);
            if (currPtr.right != null) printTree(currPtr.right);
        }
    }
}
