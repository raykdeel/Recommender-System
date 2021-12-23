package domain.classes;

/**
 * @author Abdelali El Attar Yalaoui
 */

public class BooleanValue extends Value {
    private boolean value;
    private BooleanAttribute booleanAttribute;

    public BooleanValue(String value, BooleanAttribute booleanAttribute) {
        this.value = (value.equals("true"));
        this.booleanAttribute = booleanAttribute;
    }

    @Override
    public String getValue() {
        return (value ? "true" : "false");
    }

    @Override
    public Attribute getAttribute() {
        return booleanAttribute;
    }

    @Override
    public String discretize() {
        if(value) return "true";
        else return "false";
    }

    @Override
    public double distance(String value) {
        if(discretize().equals(value)) return 0.;
        else return 1.;
    }

}
