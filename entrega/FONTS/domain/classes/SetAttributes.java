package domain.classes;

import java.util.ArrayList;

/**
 * @author Miguel Garcia Soler
 */

public class SetAttributes {
    private ArrayList<Attribute> attributes;

    public SetAttributes() {
        attributes = new ArrayList<>();
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }

    public ArrayList<String> getAttributesNames() {
        ArrayList<String> temp = new ArrayList<>();
        for (Attribute a : attributes) {
            temp.add(a.getAttributeName());
        }
        return temp;
    }
}
