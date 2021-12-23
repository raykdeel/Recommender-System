package domain.classes;

import java.util.ArrayList;

/**
 * @author Ricard Guixaró Trancho
 */

public class Item {
    private String itemId;
    private Statistics stats;
    private ArrayList<Value> values;

    /**
     * Item empty constructor
     */
    public Item() {
        this.stats = new Statistics();
        values = new ArrayList<>();
    }

    /**
     * Adds a value with its corresponding attribute type to the values of the item
     * @param value
     * @param attribute
     */
    public void addValue(String value, Attribute attribute) {
        if(attribute instanceof BooleanAttribute) values.add(new BooleanValue(value, (BooleanAttribute) attribute));
        else if (attribute instanceof DoubleAttribute) values.add(new DoubleValue(value, (DoubleAttribute) attribute));
        else if (attribute instanceof IntegerAttribute) values.add(new IntegerValue(value, (IntegerAttribute) attribute));
        else if (attribute instanceof StringAttribute) values.add(new StringValue(value, (StringAttribute) attribute));
        else values.add(new SetValue(value, (SetAttribute) attribute));
    }

    /**
     * Returns the values of the item
     * @return the values of the item in an array of values
     */
    public ArrayList<Value> getValues() {
        return values;
    }

    /**
     * Getter of itemId
     * @return itemId
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Setter of itemId
     * @param itemId
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * Getter of the statistics associated with the item
     * @return stats
     */
    public Statistics getStats() {
        return stats;
    }

    /**
     * Updates the associated statistics of the item after adding a rating
     * @param rating
     */
    public void updateStats(double rating, double oldRating) {
        if (oldRating == -1.) this.stats.add_rating(rating);
        else this.stats.update_rating(rating, oldRating);
    }

    /**
     * Returns the item with its values in a string array
     * @return the item in a generic format
     */
    public ArrayList<String> discretize() {
        ArrayList<String> aux = new ArrayList<>();
        for(int i = 0; i < values.size(); ++i) {
            aux.add(values.get(i).discretize());
        }
        return aux;
    }

}
