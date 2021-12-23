package domain.classes;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Sergi Casau Pueyo
 */

public class SetRatings {
    private HashMap<String, ArrayList<Rating>> ratings;

    /**
     * Empty Constructor of setRatings
     */
    public SetRatings() {
        ratings = new HashMap<>();
    }

    /**
     * Getter of the set of ratings
     * @return returns the set of ratings in a hashmap
     */
    public HashMap<String, ArrayList<Rating>> getRatings() {
        return ratings;
    }

    /**
     * Getter of the ratings of the given user
     * @param user
     * @return returns a hashmap with the ratings of the given user
     */
    public HashMap<String, Double> getRatingUser(String user) {
        HashMap<String, Double> ratingsMap = new HashMap<>();
        if(ratings.containsKey(user)) {
            for (Rating r : ratings.get(user)) {
                ratingsMap.put(r.getItemID(), r.getScore());
            }
        }
        return ratingsMap;
    }

    /**
     * Adds a rating to the set of ratings if it did not exist before, and modifies it if it did.
     * @param user
     * @param rating
     * @return -1 if the ratings did not exist before, the score the existing item had received otherwise
     */
    public double addRating(String user, Rating rating) {
        if(ratings.containsKey(user)) {
            if (!ratings.get(user).contains(rating)) {
                ratings.get(user).add(rating);
                return -1.;
            } else {
                int idx = ratings.get(user).indexOf(rating);
                double temp = ratings.get(user).get(idx).getScore();
                ratings.get(user).remove(idx);
                ratings.get(user).add(rating);
                return temp;
            }
        } else {
            ArrayList<Rating> aux = new ArrayList<>();
            aux.add(rating);
            ratings.put(user, aux);
            return -1.;
        }
    }

    /**
     * Returns the set of items that have been rated in the set of ratings
     * @return arraylist with rated items
     */
    public ArrayList<String> getItems() {
        ArrayList<String> temp = new ArrayList<>();
        for(ArrayList<Rating> lr : ratings.values()) {
            for(Rating r : lr) {
                if (!temp.contains(r.getItemID())) temp.add(r.getItemID());
            }
        }
        return temp;
    }

    /**
     * Returns the set of users that have rated items in the set of ratings
     * @return arraylist with users
     */
    public ArrayList<String> getUsers() {
        ArrayList<String> temp = new ArrayList<>();
        for(ArrayList<Rating> lr : ratings.values()) {
            for(Rating r : lr) {
                if (!temp.contains(r.getUserID())) temp.add(r.getUserID());
            }
        }
        return temp;
    }

    /**
     * Returns the list of all users as a hashmap to the items and their rating.
     * @return Returns the list of all users as a hashmap to the items and their rating.
     */
    public ArrayList<HashMap<String, Double>> getMappedUsersRatings() {
        ArrayList<HashMap<String, Double>> temp = new ArrayList<>();
        ArrayList<String> users = getUsers();
        for (String user_id : users) {
            temp.add(this.getRatingUser(user_id));
        }
        return temp;
    }
}
