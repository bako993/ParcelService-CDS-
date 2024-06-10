package avltrees.ClientAVLTree;

import entities.Client;

public class ClientNode implements Comparable<Integer> {
    Client client;
    ClientNode left;
    ClientNode right;

    int height;

    public ClientNode(Client client) {
        this.client = client;
    }

    @Override
    public int compareTo(Integer id) {
        return Integer.compare(client.getClientId(),id);
    }
}