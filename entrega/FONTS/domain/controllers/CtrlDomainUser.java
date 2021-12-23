package domain.controllers;

import data.CtrlPersistence;
import domain.classes.User;

import java.util.ArrayList;


/**
 * @author Miguel Garcia Soler
 */

public class CtrlDomainUser {
    private static CtrlDomainUser instance;
    private CtrlPersistence ctrlPersistence;
    private User user;


    /**
     * Empty constructor of the Domain User controller
     */
    private CtrlDomainUser() {
        ctrlPersistence = CtrlPersistence.getInstance();
    }

    /**
     * Returns the instance of the domain user controller
     * @return returns instance (singleton) if exists, and created a new one if it doesn't
     */
    public static CtrlDomainUser getInstance() {
        if (instance == null) instance = new CtrlDomainUser();
        return instance;
    }


    public User getUser() {
        return user;
    }

    /**
     * Asks the persistence controller to check if the given credentials actually belong to some user
     * @param username
     * @param pwd
     * @return true if that user exists, false othrewise
     */
    public boolean logIn(String username, String pwd) {
        ArrayList<String> userdata = ctrlPersistence.logIn(username, pwd);
        if (userdata != null) {
            user = new User(userdata);
            return true;
        }
        return false;
    }

    /**
     * Asks the persistence controller to sign up a new user into the database with the given credentials
     * @param username
     * @param pwd
     * @param admin
     * @return returns true, if the used could be created, false otherwise
     */
    public boolean signUp(String username, String pwd, Boolean admin) {
        String id = ctrlPersistence.signUp(username, pwd, admin);
        if (id != null) {
            user = new User(id, username, pwd, admin);
            return true;
        }
        return false;
    }

    /**
     * Asks the persistence controller to create a user.
     * @param id
     */
    public void createUser(String id) {
        ctrlPersistence.createUser(id);
    }

    /**
     * Asks the persistence controller if a user can change its username, if true, the user is modified.
     * @param newUsername
     * @param oldUsername
     * @return true if the change has been completed successfully, false otherwise
     */
    public boolean changeUsername(String newUsername, String oldUsername) {
        if (ctrlPersistence.changeUsername(newUsername, oldUsername)) {
            user.setUsername(newUsername);
            return true;
        }
        return false;
    }

    /**
     * Asks the persistence controller to change the password for a user
     * @param newPassword
     * @param username
     */
    public void changePassword(String newPassword, String username) {
        ctrlPersistence.changePassword(newPassword,username);
        user.setPassword(newPassword);
    }

    public void changeTheme(String theme) {
        ctrlPersistence.changeTheme(user.getUsername(), theme);
        user.setTheme(theme);
    }

    /**
     * Asks the persistence controller to delete the actual user.
     */
    public void deleteUser() {
        ctrlPersistence.deleteUser(user.getUsername());
    }

    /**
     * Logs out the actual user
     */
    public void logOut() {
        user = null;
    }

    /**
     * Returns if the user is administrator of the system
     * @return true, if the user is admin, false otherwise
     */
    public boolean isAdmin() {
        return user.isAdmin();
    }
}
