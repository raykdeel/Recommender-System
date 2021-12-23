package domain.classes;

import java.util.TreeMap;

/**
 * @author Abdelali El Attar Yalaoui
 */

public class Recommendation {
    private Double quality;
    private TreeMap<Double, String> recommendedItems;

    /**
     * Constructor of Recommendation with the recommended items
     * @param recommendedItems
     */
    public Recommendation(TreeMap<Double, String> recommendedItems) {
        this.recommendedItems = recommendedItems;
    }

    /**
     * Getter del quality de la recommendation
     *
     * @return
     */
    public Double getQuality() {
        return quality;
    }

    /**
     * Calcula la qualitat d'una recomanació tenint en compte una recomanació real
     *
     * @param setUnknown
     */
    public double calculateQuality(SetRatings setUnknown, String user) {
        this.quality = Quality.calculateQuality(this.recommendedItems, setUnknown, user);
        return quality;
    }

    /**
     * Getter del conjunt de recomanacions
     *
     * @return
     */
    public TreeMap<Double, String> getRecommendedItems() {
        return recommendedItems;
    }
}
