/**
 * @author Miguel García Soler
 */

package drivers;

import domain.classes.KMeans;
import domain.classes.Dataset;

import java.util.*;

public class DriverKMeans {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random rand = new Random();
    private static Dataset data;

    public static void main(String[] args) {
        HashMap<String, Double> table = new HashMap<>();
        List<HashMap<String, Double>> table_list = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            table.put("item1", 5 * rand.nextDouble());
            table.put("item2", 5 * rand.nextDouble());
            table.put("item3", 5 * rand.nextDouble());
            table.put("item4", 5 * rand.nextDouble());
            table.put("item5", 5 * rand.nextDouble());
            table_list.add(table);
        }
        data = new Dataset(table_list);

        String s;
        showOptions();
        while (!(s = scanner.nextLine()).equals("0")) {
            switch (s) {
                case "1":
                    applyKMeans();
                    break;
                case "2":
                    showOptions();
                    break;
                case "3":
                    System.exit(0);
            }
        }
    }

    private static void applyKMeans() {
        System.out.println("Applying KMeans...");
        KMeans.init(data, 3, 20);
        // System.out.println("Ooutput of KMeans: " + data.getDatapoints());
    }

    private static void showOptions() {
        System.out.println("Driver of the KMeans Class");
        System.out.println("Options:");
        System.out.println("1. KMeans:");
        System.out.println("2. ShowOptions");
        System.out.println("3. Exit");
    }

}
