package domain.classes;

import utils.Pair;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ricard Guixaró Trancho
 */

public class SlopeOne {

    /**
     * Slope One Algorithm
     * @param ratings set of rating
     * @param userId user for which the prediction is being made
     * @param itemId item for which the prediction is being made
     * @return the prediction that user would do to the item, depending on the ratings from similar users
     */
    public static double slopeOne(SetRatings ratings, ArrayList<String> usersGroup, String userId, String itemId) {
        double sum = 0;
        int n = 0;
        HashMap<String, Double> ratingsUser = ratings.getRatingUser(userId);
        for (Map.Entry<String, Double> rating : ratingsUser.entrySet()) {
            if (!rating.getKey().equals(itemId)) {
                Pair<Double, Integer> aux = diffs(ratings, itemId, rating.getKey(), usersGroup);
                sum += aux.second * (ratingsUser.get(rating.getKey()) + aux.first);
                n += aux.second;
            }
        }
        DecimalFormat df = new DecimalFormat("#.##");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        return Double.parseDouble(df.format(sum / n));
    }

    /**
     * Calculated the deviation between the two given items
     * @param ratings
     * @param itemId1
     * @param itemId2
     * @param usersGroup
     * @return a pair with the calculated deviation and n
     */
    private static Pair<Double, Integer> diffs(SetRatings ratings, String itemId1, String itemId2, ArrayList<String> usersGroup) {
        ArrayList<Double> diffs = new ArrayList<>();
        int n = 0;
        double dev = 0.;
        for (String user : usersGroup) {
            HashMap<String, Double> ratingsUser = ratings.getRatingUser(user);
            if (ratingsUser.containsKey(itemId1) && ratingsUser.containsKey(itemId2)) {
                diffs.add(ratingsUser.get(itemId1) - ratingsUser.get(itemId2));
                ++n;
            }
        }
        for (Double d : diffs) dev += d / n;
        return new Pair<>(dev, n);
    }
}
