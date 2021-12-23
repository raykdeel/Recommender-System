package drivers;

import java.util.*;

/**
 * Ricard Guixaró Trancho
 */

public class DriverCollaborativeFiltering {
    private static Scanner scanner;
    private static HashMap<String, HashMap<String, Double>> ratingsUsers;
    ;

    public static void main(String[] args) {
        HashMap<String, Double> aux = new HashMap<>();
        aux.put("item1", 5.);
        aux.put("item2", 3.);
        aux.put("item3", 2.);
        ratingsUsers.put("user1", aux);

        HashMap<String, Double> aux2 = new HashMap<>();
        aux2.put("item1", 3.);
        aux2.put("item2", 4.);
        ratingsUsers.put("user2", aux2);

        HashMap<String, Double> aux3 = new HashMap<>();
        aux3.put("item2", 2.);
        aux3.put("item3", 5.);
        ratingsUsers.put("user3", aux3);
        System.out.println("Slope One (Third) starting...");

        String s;
        scanner = new Scanner(System.in);
        showOptions();
        while (!(s = scanner.nextLine()).equals("0")) {
            switch (s) {
                case "1":

                    break;
                case "2":
                    slopeOne();
                    break;
                case "3":
                    showOptions();
                    break;
                case "4":
                    System.exit(0);
            }
        }
    }

    private static void slopeOne() {
        Set<String> usersGroup = new HashSet<>(Arrays.asList("user1", "user2"));
        //Double p = SlopeOne.slopeOne(ratingsUsers, usersGroup, "user3", "item1");
        //System.out.println("Prediction for user3 of item1, = " + p);
    }

    private static void showOptions() {
        System.out.println("Driver of the Collaborative Filtering Algorithm Class");
        System.out.println("Options:");
        System.out.println("1. K-Means Algorithm");
        System.out.println("1. SlopeOne");
        System.out.println("2. ShowOptions");
        System.out.println("3. Exit");
    }

}
