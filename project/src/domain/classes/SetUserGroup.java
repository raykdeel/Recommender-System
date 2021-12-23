package domain.classes;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Miguel Garcia Soler
 */

public class SetUserGroup {
    private HashMap<String, UserGroup> groups;

    public SetUserGroup() {
        groups = new HashMap<>();
    }

    public HashMap<String, UserGroup> getGroups() {
        return groups;
    }

    public void setGroups(HashMap<String, ArrayList<String>> newGroups) {
        for (String groupId : newGroups.keySet()) {
            UserGroup temp = new UserGroup(groupId, newGroups.get(groupId));
            this.groups.put(groupId, temp);
        }
    }

    public void addGroup(UserGroup group) {
        groups.put(group.getGroupId(), group);
    }

    public UserGroup getGroup(String groupId) {
        return groups.get(groupId);
    }

    public HashMap<String, ArrayList<String>> discretize() {
        HashMap<String, ArrayList<String>> temp = new HashMap<>();
        for (String groupId : groups.keySet()) {
            temp.put(groupId, groups.get(groupId).getUsers());
        }
        return temp;
    }
}
