package domain.classes;

/**
 * @author Abdelali El Attar Yalaoui
 */

public class StringValue extends Value {
    private String value;
    private StringAttribute stringAttribute;

    public StringValue(String value, StringAttribute stringAttribute) {
        this.value = value;
        this.stringAttribute = stringAttribute;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Attribute getAttribute() {
        return stringAttribute;
    }

    @Override
    public String discretize() {
        return value;
    }

    @Override
    public double distance(String value) {
        if(this.value.equals(value)) return 0.;
        else return 1.;
    }
}
