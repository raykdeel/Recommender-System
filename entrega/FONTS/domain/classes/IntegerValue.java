package domain.classes;

/**
 * @author Abdelali El Attar Yalaoui
 */

public class IntegerValue extends Value {
    private Integer value;
    private IntegerAttribute integerAttribute;

    public IntegerValue(String value, IntegerAttribute integerAttribute) {
        this.value = Integer.parseInt(value);
        this.integerAttribute = integerAttribute;
    }

    @Override
    public String getValue() {
        return Integer.toString(value);
    }

    @Override
    public Attribute getAttribute() {
        return integerAttribute;
    }

    @Override
    public String discretize() {
        return String.valueOf(value);
    }

    @Override
    public double distance(String value) {
        return Double.parseDouble(String.valueOf(this.value-Double.parseDouble(value)));
    }

}
