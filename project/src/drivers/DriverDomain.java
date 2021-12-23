package drivers;

import domain.classes.CollaborativeFiltering;
import domain.classes.ContentBased;
import domain.classes.HybridApproaches;
import domain.controllers.CtrlDomain;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class DriverDomain {
    private static CtrlDomain ctrlDomain;

    public static void main(String[] args) {
        System.out.println("Loading the data...");
        ctrlDomain = CtrlDomain.getInstance();
        //ctrlDomain.loadItems("data/items.csv");
        ctrlDomain.loadRatings("data/ratings.db.csv", "ratings");
        ctrlDomain.loadRatings("data/ratings.test.known", "known");
        ctrlDomain.loadRatings("data/ratings.test.unknown", "unknown");
        System.out.println("Insert the userId that will receive the recommendation");
        String userId;
        String option;
        Scanner scanner = new Scanner(System.in);
        while (!(userId = scanner.nextLine()).equals("0")) {
            System.out.println("Insert the 'k' value for the recommendation");
            int k = scanner.nextInt();
            algorithms();
            while (!(option = scanner.nextLine()).equals("0")) {
                switch (option) {
                    case "1":
                        ContentBased contentBased = new ContentBased();
                        //contentBased.setItems(ctrlDomain.getSetItems());
                        //contentBased.setRatings(ctrlDomain.getSetRatings());
                        //contentBased.setUser(ctrlDomain.getUser().get(0));
                        //contentBased.setDistances(ctrlDomain.getDistancesItems());
                        //printRecommendation(contentBased.algorithm(k));
                        algorithms();
                        break;
                    case "2":
                        CollaborativeFiltering collaborativeFiltering = new CollaborativeFiltering();
                        //collaborativeFiltering.setItems(ctrlDomain.getSetItems());
                        //collaborativeFiltering.setRatings(ctrlDomain.getSetRatings());
                        //collaborativeFiltering.setUser(ctrlDomain.getUser().get(0));
                        //printRecommendation(collaborativeFiltering.algorithm(k));
                        algorithms();
                        break;
                    case "3":
                        HybridApproaches hybridApproaches = new HybridApproaches();
                        //hybridApproaches.setItems(ctrlDomain.getSetItems());
                        //hybridApproaches.setRatings(ctrlDomain.getSetRatings());
                        //hybridApproaches.setUser(ctrlDomain.getUser().get(0));
                        //printRecommendation(hybridApproaches.algorithm(k));
                        algorithms();
                        break;
                    case "4":
                        System.exit(0);
                }
            }
        }
        System.out.println("Insert the userId that will receive the recommendation");
    }

    private static void printRecommendation(TreeMap<Double, String> recommendation) {
        System.out.println("Printing the recommended "+recommendation.size()+" items...");
        for (Map.Entry<Double, String> entry : recommendation.entrySet())
            System.out.println(entry.getKey().doubleValue() + " " + entry.getValue());
    }

    private static void algorithms() {
        System.out.println("Select the algorithm to use");
        System.out.println("1. ContentBased algorithm");
        System.out.println("2. Collaborative algorithm");
        System.out.println("3. Hybrid algorithm");
        System.out.println("4. Exit");

    }
}

