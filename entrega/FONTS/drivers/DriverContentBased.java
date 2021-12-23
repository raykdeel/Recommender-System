package drivers;


import domain.classes.Algorithm;
import domain.classes.ContentBased;
import domain.classes.Item;

import java.util.*;

public class DriverContentBased {
    private static Scanner scanner;

    public static void main(String[] args) {

        String s;
        scanner = new Scanner(System.in);
        showOptions();
        while (!(s = scanner.nextLine()).equals("0")) {
            switch (s) {
                case "1":
                    ContentBasedAlgorithm();
                    break;
                case "2":
                    showOptions();
                    break;
                case "3":
                    System.exit(0);
            }
        }
    }

    private static void ContentBasedAlgorithm() {
        System.out.println("We will input two sets of items, the first one represents the items which the main user likes, and the second one, the set of items not rated from user.");
        HashMap<String, Item> items = new HashMap<>();
        HashMap<String, HashMap<String, Double>> ratings = new HashMap<>();
        String userId = "user1";
        int k = 3;

        HashMap<String, Double> aux1 = new HashMap<>();
        aux1.put("item1", 5.);
        aux1.put("item2", 4.);
        aux1.put("item3", 4.5);
        ratings.put("user1", aux1);

        /*
        Item i1 = new Item("item1");
        i1.addBooleanAttribute("adulto", false);
        i1.addIntegerAttribute("inversion", 1);
        i1.addStringAttribute("categoria", "drama");
        i1.addDoubleAttribute("nota", 9.5);
        items.put(i1.getItemId(), i1);

        Item i2 = new Item("item2");
        i2.addBooleanAttribute("adulto", true);
        i2.addIntegerAttribute("inversion", 2);
        i2.addStringAttribute("categoria", "accion");
        i2.addDoubleAttribute("nota", 7.87);
        items.put(i2.getItemId(), i2);

        Item i3 = new Item("item3");
        i3.addBooleanAttribute("adulto", true);
        i3.addIntegerAttribute("inversion", 3);
        i3.addStringAttribute("categoria", "accion");
        i3.addDoubleAttribute("nota", 5.5);
        items.put(i3.getItemId(), i3);

        Item i4 = new Item("item4");
        i4.addBooleanAttribute("adulto", false);
        i4.addIntegerAttribute("inversion", 4);
        i4.addStringAttribute("categoria", "drama");
        i4.addDoubleAttribute("nota", 8.);
        i4.updateStats(2.3, -1.);
        items.put(i4.getItemId(), i4);

        Item i5 = new Item("item5");
        i5.addBooleanAttribute("adulto", false);
        i5.addIntegerAttribute("inversion", 5);
        i5.addStringAttribute("categoria", "comedia");
        i5.addDoubleAttribute("nota", 7.);
        i5.updateStats(1.0, -1.);
        items.put(i5.getItemId(), i5);

        Item i6 = new Item("item6");
        i6.addBooleanAttribute("adulto", true);
        i6.addIntegerAttribute("inversion", 6);
        i6.addStringAttribute("categoria", "accion");
        i6.addDoubleAttribute("nota", 3.);
        i6.updateStats(4.0, -1.);
        items.put(i6.getItemId(), i6);

        Item i7 = new Item("item7");
        i7.addBooleanAttribute("adulto", false);
        i7.addIntegerAttribute("inversion", 7);
        i7.addStringAttribute("categoria", "terror");
        i7.addDoubleAttribute("nota", 6.);
        i7.updateStats(2.0, -1.);
        items.put(i7.getItemId(), i7);

        Item i8 = new Item("item8");
        i8.addBooleanAttribute("adulto", true);
        i8.addIntegerAttribute("inversion", 8);
        i8.addStringAttribute("categoria", "accion");
        i8.addDoubleAttribute("nota", 5.);
        i8.updateStats(3.0, -1.);
        items.put(i8.getItemId(), i8);


         */
        Algorithm contentBased = new ContentBased();
        //TreeMap<Double, String> recommendation = contentBased.algorithm(k);
        //for (Map.Entry<Double, String> entry : recommendation.entrySet())
        //System.out.println(entry.getKey().doubleValue() + " " + entry.getValue());
    }

    private static void showOptions() {
        System.out.println("Driver of the SlopeOne Class");
        System.out.println("Options:");
        System.out.println("1. ContentBased example:");
        System.out.println("2. ShowOptions");
        System.out.println("3. Exit");
    }

}