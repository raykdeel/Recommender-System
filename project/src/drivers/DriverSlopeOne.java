package drivers;

import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Ricard Guixaró Trancho
 */

public class DriverSlopeOne {
    private static Scanner scanner;
    private static HashMap<String, HashMap<String, Double>> itemsM;
    ;

    public static void main(String[] args) {
        itemsM = new HashMap<>();

        HashMap<String, Double> aux = new HashMap<>();
        aux.put("item1", 5.);
        aux.put("item2", 3.);
        aux.put("item3", 2.);
        itemsM.put("user1", aux);

        HashMap<String, Double> aux2 = new HashMap<>();
        aux2.put("item1", 3.);
        aux2.put("item2", 4.);
        itemsM.put("user2", aux2);

        HashMap<String, Double> aux3 = new HashMap<>();
        aux3.put("item2", 2.);
        aux3.put("item3", 5.);
        itemsM.put("user3", aux3);
        System.out.println("Slope One (Third) starting...");

        String s;
        scanner = new Scanner(System.in);
        showOptions();
        while (!(s = scanner.nextLine()).equals("0")) {
            switch (s) {
                case "1":
                    slopeOne();
                    break;
                case "2":
                    showOptions();
                    break;
                case "3":
                    System.exit(0);
            }
        }
    }

    private static void slopeOne() {
        System.out.println("First, we set an input of which we know the answer (example Wikipedia) and we know that our user belongs to a group with 'user1' and 'user2'");
        System.out.println("Then, the prediction for user3 of item1 is computed...");
        //Set<String> usersGroup = new HashSet<>(Arrays.asList("user1", "user2"));
        //double p = SlopeOne.slopeOne(itemsM, usersGroup, "user3", "item1");
        //System.out.println("Prediction for user3 of item1, = " + p);
    }

    private static void showOptions() {
        System.out.println("Driver of the SlopeOne Class");
        System.out.println("Options:");
        System.out.println("1. SlopeOne Prediction:");
        System.out.println("2. ShowOptions");
        System.out.println("3. Exit");
    }

}
