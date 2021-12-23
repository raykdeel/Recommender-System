package drivers;

import domain.classes.Rating;

import java.util.Scanner;

public class DriverRating {
    private static Scanner scanner;
    private static Rating rating;

    public static void main(String[] args) {
        String s;
        scanner = new Scanner(System.in);
        showUsage();
        while (!(s = scanner.nextLine()).equals("0")) {
            switch (s) {
                case "1":
                    createRating();
                    break;
                case "2":
                    getUserRating();
                    break;
                case "3":
                    getItemRating();
                    break;
                case "4":
                    getScoreRating();
                    break;
                case "5":
                    showUsage();
                    break;
                case "6":
                    System.exit(0);
            }
        }
    }

    private static void createRating() {
        System.out.println("Insert the user identifier");
        String userId = scanner.nextLine();
        System.out.println("Insert the item identifier");
        String itemId = scanner.nextLine();
        System.out.println("Insert the desired rating for the item " + itemId);
        rating = new Rating(userId, itemId, Double.parseDouble(scanner.nextLine()));
        System.out.println("New Rating created with userId=" + rating.getUserID() + ", itemId=" + rating.getItemID() + " and rating=" + rating.getScore());
    }

    private static void getUserRating() {
        try {
            System.out.println("The rating has been created by user " + rating.getUserID());
        } catch (Exception e) {
            System.out.println("Rating not created yet. Try option 1");
        }
    }

    private static void getItemRating() {
        try {
            System.out.println("The item that has been rated is item " + rating.getItemID());
        } catch (Exception e) {
            System.out.println("Rating not created yet. Try option 1");
        }
    }

    private static void getScoreRating() {
        try {
            System.out.println("The item has been rated with value " + rating.getScore().doubleValue());
        } catch (Exception e) {
            System.out.println("Rating not created yet. Try option 1");
        }
    }

    private static void showUsage() {
        System.out.println("#####################################");
        System.out.println("Driver of the Rating Class");
        System.out.println("Options:");
        System.out.println("1. Create a Rating");
        System.out.println("2. Get user");
        System.out.println("3. Get item");
        System.out.println("4. Get rating");
        System.out.println("5. Show Usage");
        System.out.println("6. Exit");
        System.out.println("#####################################");
    }
}
