package domain.classes;

import java.util.HashMap;

/**
 * @author Ricard Guixaró Trancho
 */


public class SetItems {
    private HashMap<String, Item> items;

    /**
     * Empty Constructor of setItems
     */
    public SetItems() {
        items = new HashMap<>();
    }

    /**
     * Getter of the set of items
     * @return returns the set of items in a hashmap
     */
    public HashMap<String, Item> getItems() {
        return items;
    }

    /**
     * Getter of the size of set of items
     * @return returns the size of the set of items
     */
    public int size() {
        return items.size();
    }

    /**
     * Adds an item to the set of items
     * @param item
     */
    public void addItem(Item item) {
        items.put(item.getItemId(), item);
    }

}
