package drivers;

import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * @author Ricard Guixaró Trancho
 */

public class DriverQuality {
    private static Scanner scanner;
    private static TreeMap<Double, String> predicted;
    private static TreeMap<Double, String> real;

    public static void main(String[] args) {
        predicted = new TreeMap<>(Comparator.reverseOrder());
        real = new TreeMap<>(Comparator.reverseOrder());

        predicted.put(4., "item1");
        predicted.put(3., "item2");
        predicted.put(2.5, "item3");

        real.put(4., "item1");
        real.put(3., "item2");
        real.put(2.5, "item3");

        String s;
        scanner = new Scanner(System.in);
        showUsage();
        while (!(s = scanner.nextLine()).equals("0")) {
            switch (s) {
                case "1":
                    log2();
                    break;
                case "2":
                    calculateDCG();
                    break;
                case "3":
                    showUsage();
                    break;
                case "4":
                    System.exit(0);
            }
        }
    }

    public static void log2() {
        System.out.println("Insert a integer value for x");
        String aux = scanner.nextLine();
        try {
            Integer.parseInt(aux);
            //double r = Quality.log2(Integer.parseInt(aux));
            //System.out.println("log2(" + aux + ")= " + r);
            System.out.println("Type 3 to see the other options");
        } catch (NumberFormatException e) {
            System.out.println("Invalid integer");
            showUsage();
        }
    }

    public static void calculateDCG() {
        System.out.println("Predicted Recommendation (itemId,rating) : {(item1,4.), (item2,3.), (item3, 2.5)}");
        System.out.println("Real Recommendation (itemId,rating) : {(item1,3.5), (item2,4.), (item3,3.5)}");
        //double dcg = Quality.calculateDCG(predicted, real);
        //System.out.println("Calculated Discounted Cumulative Gain, DCG= " + dcg);
        System.out.println("Type 3 to see the other options");
    }

    private static void showUsage() {
        System.out.println("#####################################");
        System.out.println("Driver of the Quality Class");
        System.out.println("Options:");
        System.out.println("1. log2(int x)");
        System.out.println("2. Calculate DCG of a recommendation");
        System.out.println("3. Show Usage");
        System.out.println("4. Exit");
        System.out.println("#####################################");
    }

}
