package domain.classes;

/**
 * @author Sergi Casau Pueyo
 */

public class Rating {

    private Double score;
    private String userID;
    private String itemID;

    /**
     * Creadora amb valors
     * @param userID
     * @param itemID
     * @param score
     */
    public Rating(String userID, String itemID, Double score) {
        this.userID = userID;
        this.itemID = itemID;
        this.score = score;
    }

    /**
     * Getter del score
     * @return score
     */
    public Double getScore() {
        return score;
    }

    /**
     * Getter del userID
     * @return userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Getter del itemID
     * @return itemID
     */
    public String getItemID() {
        return itemID;
    }

    @Override
    public boolean equals(Object b) {
        if (!(b instanceof Rating)) return false;
        else {
            Rating temp = (Rating) b;
            return (itemID.equals(temp.itemID) && userID.equals(temp.userID));
        }
    }
}

