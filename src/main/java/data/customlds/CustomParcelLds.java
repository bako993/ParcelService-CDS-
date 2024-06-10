package data.customlds;

/**
 * Custom Linear Data Structure.
 * This is our customer linear data structure which will intake the client id and all delivery packages of the specific client.
 * It will be used to produce the top 10 recipients for a certain period.
 * Received from https://www.devglan.com/java8/hashmap-custom-implementation-java.
 **/

public class CustomParcelLds<K, V> {
    private int capacity = 16;

    private Entry<K, V>[] table;

    public CustomParcelLds(){
        table = new Entry[capacity];
    }

    public CustomParcelLds(int capacity){
        this.capacity = capacity;
        table = new Entry[capacity];
    }

    public void put(K key, V value){
        int index = index(key);
        Entry newEntry = new Entry(key, value, null);
        if(table[index] == null){
            table[index] = newEntry;
        }else {
            Entry<K, V> previousNode = null;
            Entry<K, V> currentNode = table[index];
            while(currentNode != null){
                if(currentNode.getKey().equals(key)){
                    currentNode.setValue(value);
                    break;
                }
                previousNode = currentNode;
                currentNode = currentNode.getNext();
            }
            if(previousNode != null)
                previousNode.setNext(newEntry);
        }
    }

    public V get(K key){
        V value = null;
        int index = index(key);
        Entry<K, V> entry = table[index];
        while (entry != null){
            if(entry.getKey().equals(key)) {
                value = entry.getValue();
                break;
            }
            entry = entry.getNext();
        }
        return value;
    }

    public void remove(K key){
        int index = index(key);
        Entry previous = null;
        Entry entry = table[index];
        while (entry != null){
            if(entry.getKey().equals(key)){
                if(previous == null){
                    entry = entry.getNext();
                    table[index] = entry;
                    return;
                }else {
                    previous.setNext(entry.getNext());
                    return;
                }
            }
            previous = entry;
            entry = entry.getNext();
        }
    }

    private int index(K key){
        if(key == null){
            return 0;
        }
        return Math.abs(key.hashCode() % capacity);
    }

}
