/**
 * @author Miguel García Soler
 */

package drivers;

import domain.classes.Distance;

import java.util.*;

public class DriverDistance {

    private static final Scanner scanner = new Scanner(System.in);
    private static Map<String, Double> user1 = new HashMap<>();
    private static Map<String, Double> user2 = new HashMap<>();

    public void testCalculate() {

    }

    public static void main(String[] args) {
        // user 1
        user1.put("item1", 1.0);
        user1.put("item2", 2.5);
        user1.put("item3", 2.0);
        user1.put("item4", 1.5);
        // user 2
        user2.put("item1", 4.0);
        user2.put("item2", 4.5);
        user2.put("item3", 2.0);
        user2.put("item4", 2.5);

        String s;
        showOptions();
        while (!(s = scanner.nextLine()).equals("0")) {
            switch (s) {
                case "1":
                    printDistance();
                    break;
                case "2":
                    showOptions();
                    break;
                case "3":
                    System.exit(0);
            }
        }
    }

    private static void printDistance() {
        System.out.println("First we set the input to some hard-coded values so we know what the output will be");
        System.out.println("Then, we compute the ditance");
        double dist = Distance.calculate(user1, user2);
        System.out.println("Distance between user1 and user2 is " + dist);
    }

    private static void showOptions() {
        System.out.println("#####################################");
        System.out.println("Driver of the Distance class");
        System.out.println("Options:");
        System.out.println("1. Calculate distance between 2 users");
        System.out.println("2. Show Options");
        System.out.println("3. Exit");
        System.out.println("#####################################");
    }
}
