package entities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Client implements Comparable<Client> {
    private int clientId;
    private String name;
    private String initial;
    private int addressX;
    private int addressY;

    private final List<Client> neighbours;
    private final ArrayList<DeliveryPackage> deliveryPackages;

    public Client(int clientId, String name, String initial, int addressX, int addressY) {
        this.clientId = clientId;
        this.name = name;
        this.initial = initial;
        this.addressX = addressX;
        this.addressY = addressY;
        neighbours = new LinkedList<>();
        deliveryPackages = new ArrayList<>();
    }

    public ArrayList<DeliveryPackage> getDeliveryPackages() {
        return deliveryPackages;
    }

    public int getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public int getAddressX() {
        return addressX;
    }

    public void setAddressX(int addressX) {
        this.addressX = addressX;
    }

    public int getAddressY() {
        return  addressY;
    }

    public void setAddressY(int addressY) {
        this.addressY = addressY;
    }

    public List<Client> getNeighbours(){
        return new LinkedList<Client>(neighbours);
    }

    public void addNeighbour(Client newNeighbour){
        if (newNeighbour != null) {
            neighbours.add(newNeighbour);
        }
    }

    public void removeNeighbour(Client theNeighbour){
        neighbours.remove(theNeighbour);
    }

    public boolean hasNeighbour(Client aClient) {
        for(Client client: neighbours){
            if (client.equals(aClient))
                return true;
        }
        return false;
    }

    public String toDOTString() {
        String result = "";
        result += "\tn" + name + " [label=\"" + name + "\"];\n";
        if (neighbours.size() > 0) {
            for (Client client : neighbours) {
                result += "\tn" + client.name + " [label=\"" + client.name + "\"];\n";
                result += "\t" + "n" + name + " -> n" + client.name + "\n";
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return
                "clientId; " + clientId +
                        ", name; " + name +
                        ", initial; " + initial +
                        ", addressX; " + addressX +
                        ", addressY; " + addressY;
    }

    @Override
    public int compareTo(Client client) {
        return Integer.compare(getClientId(), client.getClientId());
    }
}
