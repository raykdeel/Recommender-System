package domain.classes;

import java.util.ArrayList;

/**
 * @author Ricard Guixaró Trancho
 */

public class User {
    private String userId;
    private String username;
    private String password;
    private boolean admin;
    private String groupId;
    private String theme;

    /**
     * Constructor of user
     * Creates a user with the data from the parameters
     * @param userId
     * @param username
     * @param password
     * @param admin
     */
    public User(String userId, String username, String password, Boolean admin) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.theme = "light";
    }

    /**
     * Constructor of user
     * Creates a user from the data stored in the parameter
     * @param data
     */
    public User(ArrayList<String> data) {
        this.userId = data.get(0);
        this.username = data.get(1);
        this.password = data.get(2);
        this.admin = Boolean.parseBoolean(data.get(3));
        this.theme = data.get(4);
    }

    /**
     * Getter userId
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Getter admin
     * @return true if admin, false otherwise
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Getter username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter of the usergroup
     * @return groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Setter username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Setter userGroup
     * @param groupId
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * Gets the theme of the user
     * @return
     */
    public String getTheme() {
        return theme;
    }

    /**
     * Sets the theme for the user
     * @param theme
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * Returns the user data into a usable format
     * @return user data in a string array
     */
    public ArrayList<String> discretize() {
        ArrayList<String> aux = new ArrayList<>();
        aux.add(userId);
        aux.add(username);
        aux.add(password);
        aux.add(String.valueOf(admin));
        aux.add(theme);
        aux.add(groupId);
        return aux;
    }
}
