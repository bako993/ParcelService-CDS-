package avltrees.ClientAVLTree;

import entities.Client;

public class ClientAVLTree {
    private ClientNode root;

    public void insertClient(Client client) {
        root = insert(root, client);
    }

    public void deleteClient(Client client) {
        root = delete(root, client);
    }

    public Client findClient(int clientId){
        try {
            return searchClient(root, clientId).client;
        } catch (NullPointerException e) {
            System.out.println("Client not found!");
            return null;
        }
    }

    public ClientNode searchClient(ClientNode root, int clientId) {

        if (root.compareTo(clientId) == 0){
            return root;
        } else {
            if (root.compareTo(clientId) > 0) {
                return searchClient(root.right, clientId);
            }

            return searchClient(root.left, clientId);
        }
    }

    public int height() {
        return root == null ? -1 : root.height;
    }

    private ClientNode insert(ClientNode node, Client client) {
        if (node == null) {
            return new ClientNode(client);
        } else if (node.client.getAddressX() + node.client.getAddressY() > client.getAddressX() + client.getAddressY()) {
            node.left = insert(node.left, client);
        } else if (node.client.getAddressX() + node.client.getAddressY() < client.getAddressX() + client.getAddressY()) {
            node.right = insert(node.right, client);
        } else {
            throw new RuntimeException("duplicate Key!");
        }
        return rebalance(node);
    }

    private ClientNode delete(ClientNode node, Client client) {
        if (node == null) {
            return node;
        } else if (node.client.getAddressX() + node.client.getAddressY() > client.getAddressX() + client.getAddressY()) {
            node.left = delete(node.left, client);
        } else if (node.client.getAddressX() + node.client.getAddressY() < client.getAddressX() + client.getAddressY()) {
            node.right = delete(node.right, client);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                ClientNode mostLeftChild = mostLeftChild(node.right);
                node.client = mostLeftChild.client;
                node.right = delete(node.right, node.client);
            }
        }
        if (node != null) {
            node = rebalance(node);
        }
        return node;
    }

    private ClientNode mostLeftChild(ClientNode node) {
        ClientNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private ClientNode rebalance(ClientNode z) {
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

    private ClientNode rotateRight(ClientNode y) {
        ClientNode x = y.left;
        ClientNode z = x.right;
        x.right = y;
        y.left = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private ClientNode rotateLeft(ClientNode y) {
        ClientNode x = y.right;
        ClientNode z = x.left;

        x.left = y;
        y.right = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private void  updateHeight(ClientNode n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    private int height(ClientNode n) {
        return n == null ? -1 : n.height;
    }

    public int getBalance(ClientNode n) {
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

    public void traverseInOrder(ClientNode node) {
        if (node != null) {
            traverseInOrder(node.left);
            System.out.println(node.client);
            traverseInOrder(node.right);
        }
    }

    public void traversePreOrder(ClientNode node) {
        if (node != null) {
            System.out.println(node.client);
            traversePreOrder(node.left);
            traversePreOrder(node.right);
        }
    }

    public void traversePostOrder(ClientNode node) {
        if (node != null) {
            traversePostOrder(node.left);
            traversePostOrder(node.right);
            System.out.println(node.client);
        }
    }

    public void printTree(ClientNode currPtr, String indent, boolean last) {
        if (currPtr != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "   ";
            } else {
                System.out.print("L----");
                indent += "|  ";
            }
            System.out.println(currPtr.client.getClientId());
            printTree(currPtr.left, indent, false);
            printTree(currPtr.right, indent, true);
        }
    }
}
