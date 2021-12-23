package domain.classes;

/**
 * @author Abdelali El Attar Yalaoui
 */

public class DoubleValue extends Value {
    private Double value;
    private DoubleAttribute doubleAttribute;

    public DoubleValue(String value, DoubleAttribute doubleAttribute) {
        this.value = Double.parseDouble(value);
        this.doubleAttribute = doubleAttribute;
    }

    @Override
    public String getValue() {
        return Double.toString(value);
    }

    @Override
    public Attribute getAttribute() {
        return doubleAttribute;
    }

    @Override
    public String discretize() {
        return String.valueOf(value);
    }

    @Override
    public double distance(String value) {
        return this.value-Double.parseDouble(value);
    }

}
