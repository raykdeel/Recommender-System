package drivers;

import domain.classes.Statistics;

import java.util.Scanner;

public class DriverStatistics {
    private static Scanner scanner;
    private static Statistics statistics;


    public static void main(String[] args) {
        String s;
        scanner = new Scanner(System.in);
        showUsage();
        while (!(s = scanner.nextLine()).equals("0")) {
            switch (s) {
                case "1":
                    createStatistics();
                    break;
                case "2":
                    getAverageRating();
                    break;
                case "3":
                    getNRatings();
                    break;
                case "4":
                    addRating();
                    break;
                case "5":
                    showUsage();
                    break;
                case "6":
                    System.exit(0);
            }
        }
    }

    private static void createStatistics() {
        statistics = new Statistics();
        System.out.println("New Statistics associated to an item have been created");
    }

    private static void getAverageRating() {
        try {
            System.out.println("Statistics: average of ratings=" + statistics.getAverageRating());
        } catch (Exception e) {
            System.out.println("Statistics now created yet. Try option 1");
        }
    }

    private static void getNRatings() {
        try {
            System.out.println("Statistics: number of ratings=" + statistics.getnRatings());

        } catch (Exception e) {
            System.out.println("Statistics now created yet. Try option 1");
        }
    }

    private static void addRating() {
        try {
            statistics.getnRatings();
            System.out.println("We add two new ratings to the item, one with score=4. and the other with 4.75");
            statistics.add_rating(4.);
            statistics.add_rating(4.5);
            getNRatings();
            getAverageRating();
            System.out.println();
            System.out.println("We add two more ratings to the item, one with score=2.7 and another with 3.8");
            getNRatings();
            getAverageRating();
            System.out.println();
        } catch (Exception e) {
            System.out.println("Statistics now created yet. Try option 1");
        }
    }

    private static void showUsage() {
        System.out.println("#####################################");
        System.out.println("Driver of the Statistics Class");
        System.out.println("Options:");
        System.out.println("1. Create Statistics");
        System.out.println("2. Get average rating");
        System.out.println("3. Get number of rating");
        System.out.println("4. Add rating");
        System.out.println("5. Show Usage");
        System.out.println("6. Exit");
        System.out.println("#####################################");
    }
}
