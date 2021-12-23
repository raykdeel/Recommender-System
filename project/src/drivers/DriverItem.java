package drivers;

import domain.classes.Item;

import java.util.*;

/**
 * @author Ricard Guixaró Trancho
 */

public class DriverItem {
    private static Scanner scanner;
    private static Item item;

    public static void main(String[] args) {
        String s;
        scanner = new Scanner(System.in);
        showUsage();
        while (!(s = scanner.nextLine()).equals("0")) {
            switch (s) {
                case "1":
                    createItem();
                    break;
                case "2":
                    //addIntegerAttribute();
                    break;
                case "3":
                    //addStringAttribute();
                    break;
                case "4":
                    //addDoubleAttribute();
                    break;
                case "5":
                    //addBooleanAttribute();
                    break;
                case "6":
                    //addSetAttribute();
                    break;
                case "7":
                    getStatistics();
                    break;
                case "8":
                    showUsage();
                    break;
                case "9":
                    System.exit(0);
            }
        }
    }

    private static void createItem() {
        System.out.println("Insert the itemId");
        //item = new Item(scanner.nextLine());
        //System.out.println("New Item created with itemId=" + item.getItemId());
    }
/*
    private static void addIntegerAttribute() {
        try {
            System.out.println("Integer Attributes of item " + item.getItemId() + ":");
            HashMap<String, Integer> integerAttirbutes = item.getIntegerAttributes();
            for (Map.Entry<String, Integer> entry : integerAttirbutes.entrySet()) {
                System.out.println(entry.getKey() + " :" + entry.getValue().intValue());
            }
            System.out.println();
            System.out.println("We add a new Attribute named 'Publication Year' with value '2011'");
            item.addIntegerAttribute("Publication year", 2011);
            System.out.println("Integer Attributes of item " + item.getItemId() + ":");
            integerAttirbutes = item.getIntegerAttributes();
            for (Map.Entry<String, Integer> entry : integerAttirbutes.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue().intValue());
            }
            System.out.println();
            System.out.println("We finally add another Attribute named 'Pages' with value '1016'");
            item.addIntegerAttribute("Pages", 1016);
            System.out.println("Integer Attributes of item " + item.getItemId() + ":");
            integerAttirbutes = item.getIntegerAttributes();
            for (Map.Entry<String, Integer> entry : integerAttirbutes.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue().intValue());
            }
        } catch (Exception e) {
            System.out.println("Item not created yet. Try option 1");
        }
    }


    private static void addStringAttribute() {
        try {
            System.out.println("String Attributes of item " + item.getItemId() + ":");
            HashMap<String, String> stringAttributes = item.getStringAttributes();
            for (Map.Entry<String, String> entry : stringAttributes.entrySet()) {
                System.out.println(entry.getKey() + " :" + entry.getValue());
            }
            System.out.println();
            System.out.println("We add a new Attribute named 'Author' with value 'George R. R. Martin'");
            item.addStringAttribute("Author", "George R. R. Martin");
            System.out.println("String Attributes of item " + item.getItemId() + ":");
            stringAttributes = item.getStringAttributes();
            for (Map.Entry<String, String> entry : stringAttributes.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            System.out.println();
            System.out.println("We finally add another Attribute named 'Title' with value 'A Dance with Dragons'");
            item.addStringAttribute("Title", "A Dance with Dragons");
            System.out.println("String Attributes of item " + item.getItemId() + ":");
            stringAttributes = item.getStringAttributes();
            for (Map.Entry<String, String> entry : stringAttributes.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (Exception e) {
            System.out.println("Item not created yet. Try option 1");
        }
    }

    private static void addDoubleAttribute() {
        try {
            System.out.println("Double Attributes of item " + item.getItemId() + ":");
            HashMap<String, Double> doubleAttributes = item.getDoubleAttributes();
            for (Map.Entry<String, Double> entry : doubleAttributes.entrySet()) {
                System.out.println(entry.getKey() + " :" + entry.getValue());
            }
            System.out.println();
            System.out.println("We add a new Attribute named 'Rating' with value '8.4'");
            item.addDoubleAttribute("Rating", 8.4);
            System.out.println("Double Attributes of item " + item.getItemId() + ":");
            doubleAttributes = item.getDoubleAttributes();
            for (Map.Entry<String, Double> entry : doubleAttributes.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (Exception e) {
            System.out.println("Item not created yet. Try option 1");
        }
    }

    private static void addBooleanAttribute() {
        try {
            System.out.println("Boolean Attributes of item " + item.getItemId() + ":");
            HashMap<String, Boolean> booleanAttributes = item.getBooleanAttributes();
            for (Map.Entry<String, Boolean> entry : booleanAttributes.entrySet()) {
                System.out.println(entry.getKey() + " :" + entry.getValue());
            }
            System.out.println();
            System.out.println("We add a new Attribute named 'Translated' with value 'True'");
            item.addBooleanAttribute("Translated", true);
            System.out.println("Boolean Attributes of item " + item.getItemId() + ":");
            booleanAttributes = item.getBooleanAttributes();
            for (Map.Entry<String, Boolean> entry : booleanAttributes.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (Exception e) {
            System.out.println("Item not created yet. Try option 1");
        }
    }

    private static void addSetAttribute() {
        try {
            System.out.println("Set Attributes of item " + item.getItemId() + ":");
            HashMap<String, Set<String>> setAttributes = item.getSetAttributes();
            for (Map.Entry<String, Set<String>> entry : setAttributes.entrySet()) {
                System.out.println(entry.getKey() + " :" + entry.getValue());
            }
            System.out.println();
            System.out.println("We add a new Attribute named 'Publisher' with value '\"Voyager Books\", \"Bantam Spectra\"'");
            item.addSetAttribute("Publisher", new HashSet<>(Arrays.asList("Voyager Books", "Bantam Spectra")));
            System.out.println("Set Attributes of item " + item.getItemId() + ":");
            setAttributes = item.getSetAttributes();
            for (Map.Entry<String, Set<String>> entry : setAttributes.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            System.out.println();
            System.out.println("Finally, we add another Attribute named 'Genre' with value '\"Novel\", \"Fantasy\"'");
            item.addSetAttribute("Genre", new HashSet<>(Arrays.asList("Novel", "Fantasy")));
            System.out.println("Set Attributes of item " + item.getItemId() + ":");
            setAttributes = item.getSetAttributes();
            for (Map.Entry<String, Set<String>> entry : setAttributes.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (Exception e) {
            System.out.println("Item not created yet. Try option 1");
        }
    }
    */

    private static void getStatistics() {
        try {
            System.out.println("Statistics of item " + item.getItemId() + ":");
            System.out.println("Average Rating: " + item.getStats().getAverageRating());
            System.out.println("Number of Ratings: " + item.getStats().getnRatings());
            System.out.println();
            System.out.println("As there are no ratings associated with this item, we create one with score '4' and another with score '4.5'");
            item.updateStats(4., -1.);
            item.updateStats(4.5, -1.);
            System.out.println("Statistics of item " + item.getItemId() + ":");
            System.out.println("Average Rating: " + item.getStats().getAverageRating());
            System.out.println("Number of Ratings: " + item.getStats().getnRatings());
            System.out.println();
        } catch (Exception e) {
            System.out.println("Item not created yet. Try option 1");
        }
    }



    private static void showUsage() {
        System.out.println("#####################################");
        System.out.println("Driver of the Item Class");
        System.out.println("Options:");
        System.out.println("1. Create an Item");
        System.out.println("2. Add IntegerAttributes");
        System.out.println("3. Add StringAttributes");
        System.out.println("4. Add DoubleAttributes");
        System.out.println("5. Add BooleanAttributes");
        System.out.println("6. Add SetAttributes");
        System.out.println("7. Get Statistics associated");
        System.out.println("8. Show Usage");
        System.out.println("9. Exit");
        System.out.println("#####################################");
    }
}
