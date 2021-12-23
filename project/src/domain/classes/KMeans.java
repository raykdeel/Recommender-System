/**
 * @author Miguel García Soler
 * @date 16/11/2021
 */

package domain.classes;

import java.util.*;

/**
 * @author Miguel Garcia Soler
 */

// import domain.classes.Algorithm;

//HashMap<String, ArrayList<String>> userGroups;
//userGroups = CtrlDomain.getInstance().getGroupUsers();
//els grups d'usuaris creats

public class KMeans {

    /**
     * This functions applies the K-Means algorithm to a dataset. Such dataset
     * can later be queried to get the resulting partitions.
     *
     * @param dataset  - The dataset upon which the algorithm will operate.
     * @param k        - Number of centroids.
     * @param max_iter - Maximum number of iterations the algorithm is allowed to do.
     */
    public static void init(Dataset dataset, int k, int max_iter) {
        // Choose k initial centroids
        List<Map<String, Double>> centroids = dataset.selectRandomPoints(k);
        assert centroids.size() >= 1 : "Too few centroids!";

        Boolean stalemate = false;
        int iter = 0;
        while (iter < max_iter && !stalemate) {
            // Let's assume no datapoint will change partitions
            stalemate = true;
            for (var dp : dataset.getDatapoints()) {
                double min_dist = Distance.calculate(centroids.get(0), dp.getTable());
                for (int i = 1; i < k; i++) {
                    double dist = Distance.calculate(centroids.get(i), dp.getTable());
                    if (dist < min_dist) {
                        min_dist = dist;
                        int old_cluster_id = dp.getClusterID();
                        dp.setClusterID(i);
                        // If the datapoint has been asigned to a different cluster,
                        // that means partitions have changed and the algorithm should
                        // continue.
                        if (old_cluster_id != i) {
                            stalemate = false;
                        }
                    }
                }
            }
            // Update centroids based on the new cluster assignments
            centroids = dataset.recalculateCentroids();
            iter++;
        }
    }

    // /********************/
    // /* Main *************/
    // /********************/
    // public static void main(String[] args) {
    //     HashMap<String, Double> table = new HashMap<>();
    //     List<HashMap<String, Double>> table_list = new ArrayList<>(8);
    //     for (int i = 0; i < 8; i++) {
    //         table.put("item1", 4.5 /*5 * rand.nextDouble()*/);
    //         table.put("item2", 4.5 /*5 * rand.nextDouble()*/);
    //         table.put("item3", 4.5 /*5 * rand.nextDouble()*/);
    //         table.put("item4", 4.5 /*5 * rand.nextDouble()*/);
    //         table.put("item5", 4.5 /*5 * rand.nextDouble()*/);
    //         table_list.add(table);
    //     }
    //     Dataset data = new Dataset(table_list);

    //     System.out.println(table_list);
    // }
}
