package domain.classes;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * @author Miguel Garcia Soler
 */

public class HybridApproaches extends Algorithm {

    private HashMap<String, TreeMap<Double, String>> distances;

    @Override
    public void setDistances(HashMap<String, TreeMap<Double, String>> distances) {
        this.distances = distances;
    }

    @Override
    public TreeMap<Double, String> algorithm(SetRatings setKnown, SetRatings setUnknown, String user, int k) {
        CollaborativeFiltering collaborativeFiltering = new CollaborativeFiltering();
        TreeMap<Double, String> tempCollaborative = collaborativeFiltering.algorithm(setKnown, setUnknown, user, k);

        ContentBased contentBased = new ContentBased();
        contentBased.setDistances(distances);
        TreeMap<Double, String> tempContentBased = contentBased.algorithm(setKnown, setUnknown, user, k);

        TreeMap<Double, String> recommendation = new TreeMap<>(Comparator.reverseOrder());
        recommendation.putAll(tempCollaborative);
        recommendation.putAll(tempContentBased);
        while (recommendation.size() > k) {
            recommendation.pollLastEntry();
        }
        return recommendation;
    }

}
