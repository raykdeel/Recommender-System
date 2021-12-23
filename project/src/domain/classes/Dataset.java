/**
 * @author Miguel García Soler
 */

package domain.classes;

import java.util.*;

/**
 * @author Miguel Garcia Soler
 */

public class Dataset {

    /**
     * The only reason this needs to be a nested class instead of directly using a
     * HashMap for instance, is that the centroid ID has to be associated with every
     * datapoint, so later they can be grouped into clusters.
     */
    static class Datapoint {
        private HashMap<String, Double> table;
        private Integer cluster_id = -1;

        public Datapoint(HashMap<String, Double> table) {
            this.table = table;
        }

        public HashMap<String, Double> getTable() {
            return table;
        }

        public Integer getClusterID() {
            return cluster_id;
        }

        public void setClusterID(Integer cluster_id) {
            this.cluster_id = cluster_id;
        }
    }

    private Integer num_centroids = null;
    private final List<String> field_ids = new ArrayList<>();
    private final List<Datapoint> datapoints = new ArrayList<>();
    private static final Random rand = new Random();

    /**
     * Constructor.
     *
     * @param dataset - data obtained fom the csv files.
     */
    public Dataset(List<HashMap<String, Double>> dataset) {
        for (var data : dataset) {
            datapoints.add(new Datapoint(data));
        }
        for (var field : datapoints.get(0).getTable().keySet()) {
            field_ids.add(field);
        }
    }

    /**
     * Generates a random list of k centroids out of all the available data points
     * from the dataset. Affects the internal state of the dataset.
     *
     * @param k - number of datapoints to be selected.
     */
    public List<Map<String, Double>> selectRandomPoints(int k) {
        List<Map<String, Double>> centroids = new ArrayList<>();
        List<Integer> indices = new ArrayList<>(k);
        int n = datapoints.size();
        this.num_centroids = k;

        for (int i = 0; i < k; i++) {
            int val;
            do {
                val = rand.nextInt(n);
            } while (indices.contains(val));
            indices.add(val);
            centroids.add(datapoints.get(val).getTable());
        }

        return centroids;
    }

    /**
     * Calculates the mean of the given field/attribute from the list of datapoints
     * with index equal to indices.get(i). If the datapoints field is null, a random
     * value between the max and min possible values is used.
     *
     * @param field_id - identifier of the field/attribute.
     * @param indices  - list of datapoint indices.
     * @return the average value of the field/attribute.
     */
    public Double meanOfField(String field_id, List<Integer> indices) {
        Double total = 0.0;
        for (int idx : indices) {
            Double value = datapoints.get(idx).getTable().get(field_id);
            total += (value != null) ? value : (5 * rand.nextDouble());
        }
        return total / indices.size();
    }

    /**
     * @param cluster_id - cluster identifier, range [0, k)
     * @return the resulting centroid of computing the average of all the datapoints
     * inside the cluster.
     */
    public Map<String, Double> calculateClusterCentroid(int cluster_id) {
        Map<String, Double> new_centroid = new HashMap<>();
        List<Integer> indices = new ArrayList<>();
        // First identify all datapoints that belong to the given cluster.
        int i = 0;
        for (var dp : datapoints) {
            if (dp.getClusterID() == cluster_id) {
                indices.add(i);
            }
            i++;
        }

        field_ids.forEach((field_id) -> {
            new_centroid.put(field_id, meanOfField(field_id, indices));
        });

        return new_centroid;
    }

    /**
     * The number of centroids is set by {@link #selectRandomPoints(int)}. So
     * before calling this function make sure you call the other one first.
     *
     * @return a list of length K with all recalculated centroids based on the the
     * dataset internal state (a.k.a. the average of all K clusters).
     */
    public List<Map<String, Double>> recalculateCentroids() {
        List<Map<String, Double>> new_centroids = new ArrayList<>();
        for (int i = 0; i < num_centroids; i++) {
            new_centroids.add(calculateClusterCentroid(i));
        }
        return new_centroids;
    }

    /**
     * @return Returns the resulting partitions.
     */
    public ArrayList<HashMap<String, Double>> getResultingPartitions() {
        ArrayList<HashMap<String, Double>> partitions = new ArrayList<>();
        // for (int i = 0; i < num_centroids; i++) {
        // }
        // return partitions;
        // FIXME: Hack to at least get something on the screen.
        return new ArrayList<>();
    }

    /**
     * @return the list of datapoints.
     */
    public List<Datapoint> getDatapoints() {
        return datapoints;
    }
}
