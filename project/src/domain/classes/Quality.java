package domain.classes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author Ricard Guixaró Trancho
 */

public class Quality {

    /**
     * Calculates the quality of the predicted recommendation
     * @param predictedData
     * @param setUnknown
     * @param user
     * @return quality of the predicted recommendation
     */
    public static double calculateQuality(TreeMap<Double, String> predictedData, SetRatings setUnknown, String user) {
        HashMap<String, Double> ratingsUser = setUnknown.getRatingUser(user);
        return calculateDCG(predictedData, ratingsUser);
    }

    /**
     * Calculates de discounted cumulative gain of the predicted recommendation
     * @param predictedData
     * @param ratingsUser
     * @return discounted cumulative gain
     */
    private static double calculateDCG(TreeMap<Double, String> predictedData, HashMap<String, Double> ratingsUser) {
        double dcg = 0;
        double lastRating = -1.;
        int duplicated = 0;
        boolean duplicatedBoolean = false;
        int rank = 0;
        int i = 0;
        for (Map.Entry<Double, String> entry1 : predictedData.entrySet()) {
            String itemId = entry1.getValue();
            double ratingItem = entry1.getKey();
            double rel = 0;
            if (ratingsUser.containsKey(itemId)) {
                rel = ratingsUser.get(itemId);
            }
            if (lastRating == -1) {
                lastRating = ratingItem;
                rank = i;
            } else {
                if (lastRating == ratingItem) duplicatedBoolean = true;
                else {
                    lastRating = ratingItem;
                    rank = i;
                }
            }
            double denom = log2(rank - duplicated + 1);
            dcg += (Math.pow(2, rel) - 1) / denom;
            if (duplicatedBoolean) duplicated++;
            ++i;
        }
        BigDecimal decimal = new BigDecimal(dcg);
        decimal = decimal.setScale(2, RoundingMode.HALF_UP);
        return decimal.doubleValue();
    }

    /**
     * Function used to calculate the log value
     * @param x
     * @return log2(x) value
     */
    private static double log2(int x) {
        return Math.log(x + 1) / Math.log(2);
    }
}
