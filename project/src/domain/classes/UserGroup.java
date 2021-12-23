package domain.classes;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author Ricard Guixaró Trancho
 */

public class UserGroup {
    private String groupId;
    private ArrayList<String> usersId;

    public UserGroup(String groupId) {
        this.groupId = groupId;
        usersId = new ArrayList<>();
    }

    public UserGroup(String groupId, ArrayList<String> users) {
        this.groupId = groupId;
        this.usersId = users;
    }

    public String getGroupId() {
        return groupId;
    }

    public ArrayList<String> getUsers() {
        return usersId;
    }

    public void addUser(String userId) {
        usersId.add(userId);
    }

    public boolean contains(String userId) {
        return usersId.contains(userId);
    }

}
