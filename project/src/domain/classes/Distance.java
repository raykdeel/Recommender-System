package domain.classes;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Miguel García Soler
 */

public class Distance {
    /**
     * Aquesta funcio calcula la distancia entre dos usuaris basant-se en el rating
     * de tots els items que han valorat en comu. La funcio pren com a parametres
     * dos Maps, representant dues taules, de tots els items i la valoracio
     * corresponent per cadascun dels usuaris que s'esta comparant. La funcio nomes
     * te en compte aquells items que ambdos usuaris han valorat.
     *
     * @param dp1 - datapoint 1
     * @param dp2 - datapoint 2
     * @return the distance between the two datapoints.
     */
    public static double calculate(Map<String, Double> dp1, Map<String, Double> dp2) {
        double sum = 0.0;
        for (String key : dp1.keySet()) {
            Double r1 = dp1.get(key);
            Double r2 = dp2.get(key);

            if (r1 != null && r2 != null) {
                sum += Math.pow(r1 - r2, 2);
            }
        }
        return Math.sqrt(sum);
    }

    /**
     * Calculates the distances between all items of the set
     * @param setItems
     * @return a hashMap with each item identifier and a treeMap with the distances between all other items ordered decreasingly.
     */
    public static HashMap<String, TreeMap<Double, String>> calculateItemDistances(SetItems setItems) {
        HashMap<String, HashMap<String, Double>> distances = new HashMap<>();
        HashMap<String, TreeMap<Double, String>> temp = new HashMap<>();
        HashMap<String, Item> items = setItems.getItems();
        Double maxDouble = max_double(items);
        Integer maxInteger = max_integer(items);
        for(String i1 : items.keySet()) {
            HashMap<String, Double> d = new HashMap<>();
            TreeMap<Double, String> d2 = new TreeMap<>();
            for(String i2 : items.keySet()) {
                if(i1 != i2) {
                    Double dist = compareItems(items.get(i1), items.get(i2), maxDouble, maxInteger);
                    d.put(i2, dist);
                    d2.put(dist, i2);
                }
            }
            distances.put(i1, d);
            temp.put(i1, d2);
        }
        return temp;
    }


    /**
     * Searches the highest double value among all item values.
     * @param items
     * @return the highest double value
     */
    private static Double max_double(HashMap<String, Item> items) {
        Double max_diff = 0.;
        Double diff;
        for(String i1 : items.keySet()) {
            for(String i2 : items.keySet()) {
                if(i1 != i2) {
                    for(int i = 0; i < items.get(i1).getValues().size(); i++) {
                        if(items.get(i1).getValues().get(i) instanceof DoubleValue) {
                            double val1 = Double.parseDouble(items.get(i1).getValues().get(i).discretize());
                            double val2 = Double.parseDouble(items.get(i2).getValues().get(i).discretize());
                            diff = Math.pow(val1-val2, 2);
                            if(max_diff == 0. || max_diff < diff) max_diff = diff;
                        }
                    }
                }
            }
        }
        return max_diff;
    }

    /**
     * Searches the highest integer value among all item values.
     * @param items
     * @return the highest integer value
     */
    private static Integer max_integer(HashMap<String, Item> items) {
        Integer max_diff = 0;
        Integer diff;
        for(String i1 : items.keySet()) {
            for(String i2 : items.keySet()) {
                if(i1 != i2) {
                    for(int i = 0; i < items.get(i1).getValues().size(); i++) {
                        if(items.get(i1).getValues().get(i) instanceof IntegerValue && !items.get(i1).getValues().get(i).getAttribute().getAttributeName().equals("id")) {
                            Integer val1 = Integer.parseInt(items.get(i1).getValues().get(i).discretize());
                            Integer val2 = Integer.parseInt(items.get(i2).getValues().get(i).discretize());
                            diff = val1 - val2;
                            diff *= diff;
                            if(max_diff == 0. || max_diff < diff) max_diff = diff;
                        }
                    }
                }
            }
        }
        return max_diff;
    }

    /**
     * Compares the two given items and returns their similarity
     * @param item1
     * @param item2
     * @param maxDouble
     * @param maxInteger
     * @return the calculated distance between both items
     */
    private static Double compareItems(Item item1, Item item2, Double maxDouble, Integer maxInteger) {
        Double averageRating = item2.getStats().getAverageRating();
        Double desvaloracio_mitja_normalitzada = 1 - (averageRating / 5.0);
        Double dissimilarityString = dissimilarityString(item1, item2);
        Double dissimilarityDouble = dissimilarityDouble(item1, item2, maxDouble);
        Double dissimilarityInteger = dissimilarityInteger(item1, item2, maxInteger);
        Double dissimilarityBoolean = dissimilarityBoolean(item1, item2);
        Double dissimilaritySet = dissimilaritySet(item1, item2);
        //com mes baixa sigui, més probabilitats hi ha de que se li recomani a l'usuari
        Double dissimilarity = 0.2 * dissimilarityBoolean + 0.2 * dissimilarityDouble + 0.2 * dissimilaritySet + 0.2 * dissimilarityInteger + 0.2 * dissimilarityString;
        return 0.6 * dissimilarity + 0.4 * desvaloracio_mitja_normalitzada;
    }

    /**
     * Calculated the dissimilarity of the Set attributes  between the two given items
     * @param item1
     * @param item2
     * @return the dissimilarity
     */
    private static Double dissimilaritySet(Item item1, Item item2) {
        Double puntuacio = 0.;
        int setAttr = 0;
        for(int i = 0; i < item1.getValues().size(); ++i) {
            if (item1.getValues().get(i) instanceof SetValue) {
                ++setAttr;
                puntuacio += item1.getValues().get(i).distance(item2.getValues().get(i).getValue()) * (1 / setAttr);
            }
        }
        if (setAttr==0) return 0.;
        return puntuacio;
    }

    /**
     * Calculated the dissimilarity of the Boolean attributes  between the two given items
     * @param item1
     * @param item2
     * @return the dissimilarity
     */
    private static Double dissimilarityBoolean(Item item1, Item item2) {
        Double nombre_bool_diff = 0.0;
        double booleanAttr = 0.;
        for(int i = 0; i < item1.getValues().size(); ++i) {
            if (item1.getValues().get(i) instanceof BooleanValue) {
                ++booleanAttr;
                if (item1.getValues().get(i).distance(item2.getValues().get(i).getValue()) == 0) ++nombre_bool_diff;
            }
        }
        if (booleanAttr==0) return 0.;
        else return nombre_bool_diff / booleanAttr;
    }

    /**
     * Calculated the dissimilarity of the Integer attributes  between the two given items
     * @param item1
     * @param item2
     * @return the dissimilarity
     */
    private static Double dissimilarityInteger(Item item1, Item item2, Integer maxInteger) {
        Double diff = 0.;
        int integerAttr = 0;
        for(int i = 0; i < item1.getValues().size(); ++i) {
            if(item1.getValues().get(i) instanceof IntegerValue && !item1.getValues().get(i).getAttribute().getAttributeName().equals("id")) {
                ++integerAttr;
                diff = diff + item1.getValues().get(i).distance(item2.getValues().get(i).getValue());
            }
        }
        Double puntuacio = diff / integerAttr;
        if (integerAttr==0) return 0.;
        return (puntuacio * puntuacio) / maxInteger;
    }

    /**
     * Calculated the dissimilarity of the Double attributes  between the two given items
     * @param item1
     * @param item2
     * @return the dissimilarity
     */
    private static Double dissimilarityDouble(Item item1, Item item2, Double maxDouble) {
        Double diff = 0.;
        int doubleAttr = 0;
        for(int i = 0; i < item1.getValues().size(); ++i) {
            if(item1.getValues().get(i) instanceof DoubleValue) {
                ++doubleAttr;
                diff = diff + item1.getValues().get(i).distance(item2.getValues().get(i).getValue());
            }
        }
        Double puntuacio = diff / doubleAttr;
        if (doubleAttr==0) return 0.;
        return (puntuacio * puntuacio) / maxDouble;
    }

    /**
     * Calculated the dissimilarity of the String attributes  between the two given items
     * @param item1
     * @param item2
     * @return the dissimilarity
     */
    private static Double dissimilarityString(Item item1, Item item2) {
        Double diff = 0.0;
        int stringAttr = 0;
        for(int i = 0; i < item1.getValues().size(); ++i) {
            if(item1.getValues().get(i) instanceof StringValue) {
                ++stringAttr;
                diff = diff + item1.getValues().get(i).distance(item2.getValues().get(i).getValue());
            }
        }
        if (stringAttr==0) return 0.;
        return diff/stringAttr;
    }
}
