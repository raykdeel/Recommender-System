package domain.classes;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * @author Abdelali El Attar Yalaoui
 */

public abstract class Algorithm {

    public abstract void setDistances(HashMap<String, TreeMap<Double, String>> distances);
    public abstract TreeMap<Double, String> algorithm(SetRatings known, SetRatings setUnknown, String user, int k);


}
