package domain.classes;

/**
 * @author Sergi Casau Pueyo
 */

public abstract class Value {

    public abstract String getValue();
    public abstract Attribute getAttribute();
    public abstract String discretize();
    public abstract double distance(String value);

}
