package domain.classes;

/**
 * @author Abdelali El Attar Yalaoui
 */

public abstract class Attribute {
    private String attributeName;

    public Attribute(String name) {
        this.attributeName = name;
    }

    public String getAttributeName() {
        return attributeName;
    }
}
