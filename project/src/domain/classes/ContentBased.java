package domain.classes;

import java.util.*;

/**
 * @author Sergi Casau Pueyo
 */

public class ContentBased extends Algorithm {

    private HashMap<String, TreeMap<Double, String>> distances;

    @Override
    public void setDistances(HashMap<String, TreeMap<Double, String>> distances) {
        this.distances = distances;
    }

    @Override
    public TreeMap<Double, String> algorithm(SetRatings setKnown, SetRatings setUnknown, String user, int k) {
        ArrayList<String> liked = new ArrayList<>();
        HashMap<String, Double> ratingsUser = setKnown.getRatingUser(user);
        for (Map.Entry<String, Double> rating : ratingsUser.entrySet()) {
            if (rating.getValue() >= 7.5) liked.add(rating.getKey());
        }
        TreeMap<Double, String> recommendation = new TreeMap<>();
        HashMap<String, Double> itemsNotRated = setUnknown.getRatingUser(user);
        for (String itemLiked : liked) {
            TreeMap<Double, String> similarItems = distances.get(itemLiked);
            similarItems = leaveOnlyItemsNotRated(similarItems, itemsNotRated.keySet(), k);
            recommendation = unionTreeMap(recommendation, similarItems, k);
        }
        return recommendation;
    }

    private TreeMap<Double, String> leaveOnlyItemsNotRated(TreeMap<Double, String> similarItems, Set<String> itemsNotRated, int k) {
        Set<Double> remove = new HashSet<>();
        for (Map.Entry<Double, String> item : similarItems.entrySet()) {
            if (!itemsNotRated.contains(item.getValue())) remove.add(item.getKey());
        }
        for (Double d : remove) {
            similarItems.remove(d);
        }
        return trim(similarItems, k);
    }

    private TreeMap<Double, String> trim(TreeMap<Double, String> recommendation, int k) {
        while (recommendation.size() > k) recommendation.pollLastEntry();
        return recommendation;
    }

    private TreeMap<Double, String> unionTreeMap(TreeMap<Double, String> t1, TreeMap<Double, String> t2, int k) {
        if (t1.size() == 0) t1.putAll(t2);
        else {
            t1.putAll(t2);
            Set<Double> remove = new HashSet<>();
            for (Map.Entry<Double, String> entry1 : t1.entrySet()) {
                for (Map.Entry<Double, String> entry2 : t1.entrySet()) {
                    if (entry1.getValue().equals(entry2.getValue())) {
                        if (entry1.getKey() > entry2.getKey()) remove.add(entry1.getKey());
                    }
                }
            }
            for (Double d : remove) t1.remove(d);
        }
        trim(t1, k);
        return t1;
    }
}








