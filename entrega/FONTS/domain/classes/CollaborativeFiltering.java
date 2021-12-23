package domain.classes;

import java.util.*;

/**
 * @author Miguel Garcia Soler
 */

public class CollaborativeFiltering extends Algorithm {

    @Override
    public void setDistances(HashMap<String, TreeMap<Double, String>> distances) {}

    @Override
    public TreeMap<Double, String> algorithm(SetRatings setKnown, SetRatings setUnknown, String user, int k) {
        TreeMap<Double, String> prov = new TreeMap<>(Comparator.reverseOrder());

        Dataset ds_helper = new Dataset(setKnown.getMappedUsersRatings());
        KMeans.init(ds_helper, 6, 50);
        ArrayList<HashMap<String, Double>> results = ds_helper.getResultingPartitions();

        ArrayList<String> all_items = setKnown.getItems();
        Collections.shuffle(all_items);
        double weight = 0.1;
        for (int i = 0; i < k; i++) {
            prov.put(weight, all_items.get(i));
            weight += 0.1;
        }

        return prov;
    }
}
