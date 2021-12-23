package domain.classes;

import java.util.ArrayList;

/**
 * @author Miguel Garcia Soler
 */

public class SetValue extends Value {
    private String value;
    private SetAttribute setAttribute;

    public SetValue(String value, SetAttribute setAttribute) {
        this.value = value;
        this.setAttribute = setAttribute;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Attribute getAttribute() {
        return setAttribute;
    }

    @Override
    public String discretize() {
        return value;
    }

    @Override
    public double distance(String value) {
        ArrayList<String> value1 = parseLine(this.value, ';');
        ArrayList<String> value2 = parseLine(value, ';');
        double same = 0;
        for(String string : value1) if(value2.contains(string)) ++same;
        return Double.valueOf((value1.size()-same)/value1.size());
    }

    private static ArrayList<String> parseLine(String line, char delimiter) {
        ArrayList<String> splitted = new ArrayList<>();
        boolean stringEscape = false;
        int start = 0;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (!stringEscape && c == delimiter) {
                splitted.add(line.substring(start, i));
                start = i + 1;
            }
            if (!stringEscape && c == '"') {
                stringEscape = true;
            } else if (stringEscape && c == '"') {
                stringEscape = false;
            }
        }
        if (start != line.length()) {
            splitted.add(line.substring(start, line.length()));
        }
        return splitted;
    }

}
