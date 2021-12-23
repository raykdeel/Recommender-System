package drivers;

import domain.classes.Recommendation;

import java.util.*;

public class DriverRecommendation {
    private static Scanner scanner;
    private static Recommendation recommendation;

    public static void main(String[] args) {
        String s;
        scanner = new Scanner(System.in);
        showUsage();
        while (!(s = scanner.nextLine()).equals("0")) {
            switch (s) {
                case "1":
                    createRecommendation();
                    break;
                case "2":
                    addItem();
                    break;
                case "3":
                    calculateQuality();
                    break;
                case "4":
                    showUsage();
                    break;
                case "5":
                    System.exit(0);
            }
        }
    }

    private static void createRecommendation() {
        System.out.println("Insert the recommendationId");
        //recommendation = new Recommendation(scanner.nextLine(), "CollaborativeFiltering");
        //System.out.println("New Recommendation created with recommendationId=" + recommendation.getRecommendationId() + " and the algorithm 'Collaborative Filtering'");
    }

    private static void addItem() {
        try {
            //System.out.println("Items of the Recommendation" + recommendation.getRecommendationId() + ":");
            TreeMap<Double, String> entries = recommendation.getRecommendedItems();
            Set<Map.Entry<Double, String>> recommendations = entries.entrySet();
            for (Map.Entry<Double, String> entry : recommendations) {
                System.out.println("Recommended item" + entry.getValue() + " with relevance=" + entry.getKey().doubleValue());
            }
            System.out.println("We add an item with itemId='item1' and relevance='4.' and another with itemId='item2' and relevance='3.5'");
            //recommendation.addRecommendedItem(4., "item1");
            //recommendation.addRecommendedItem(3.5, "item2");

            System.out.println();
            entries = recommendation.getRecommendedItems();
            recommendations = entries.entrySet();
            for (Map.Entry<Double, String> entry : recommendations) {
                System.out.println("Recommended item" + entry.getValue() + " with relevance=" + entry.getKey().doubleValue());
            }
        } catch (Exception e) {
            System.out.println("Recommendation not created yet. Try option 1");
        }
    }

    private static void calculateQuality() {
        try {
            TreeMap<Double, String> real = new TreeMap<>(Comparator.reverseOrder());
            real.put(4., "item1");
            real.put(3., "item2");
            //recommendation.calculateQuality(real);
            System.out.println("Calculated Discounted Cumulative Gain, DCG= " + recommendation.getQuality());
        } catch (Exception e) {
            System.out.println("Recommendation not created yet. Try option 1");
        }
    }

    private static void showUsage() {
        System.out.println("#####################################");
        System.out.println("Driver of the Recommendation Class");
        System.out.println("Options:");
        System.out.println("1. Create a Recommendation");
        System.out.println("2. Add Items to the Recommendation");
        System.out.println("3. Calculate the quality of the Recommendation");
        System.out.println("4. Show Usage");
        System.out.println("5. Exit");
        System.out.println("#####################################");
    }
}
