package presentation;

import domain.classes.Item;
import utils.Pair;
import domain.controllers.CtrlDomain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * @author Ricard Guixaró Trancho
 */

public class CtrlPresentation {
    private CtrlDomain ctrlDomain;
    private static CtrlPresentation ctrlPresentation;

    /**
     * Empty constructor of the presentation controller
     */
    public CtrlPresentation() {
        ctrlDomain = CtrlDomain.getInstance();
    }

    /**
     * Returns the instance of the presentation controller
     * @return returns instance (singleton) if exists, and creates a new one if it does no
     */
    public static CtrlPresentation getInstance() {
        if(ctrlPresentation == null) {
            ctrlPresentation = new CtrlPresentation();
        }
        return ctrlPresentation;
    }

    /**
     * Changes the stage of the application determined by path
     * @param path
     * @throws IOException
     */
    public void changeStage(String path) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage next = new Stage();
        next.setTitle("recommender");
        next.setResizable(false);
        next.setScene(new Scene(root));
        next.show();
    }

    /**
     * Asks the domain controller for the recommended items
     * @param algorithmName
     * @return recommended items
     */
    public TreeMap<Double, String> getRecommendation(String algorithmName) {
        return ctrlDomain.getRecommendation(algorithmName);
    }

    /**
     * Asks the domain controller for the arraylist with the attributes names
     * @return attributes names
     */
    public ArrayList<String> getAttributes() {
        return ctrlDomain.getAttributes();
    }

    /**
     * Asks the domain controller to log in a user with the given credentials
     * @param username
     * @param pwd
     * @return true if the user has logged in successfully, false otherwise
     */
    public boolean logIn(String username, String pwd) {
        return ctrlDomain.logIn(username, pwd);
    }

    /**
     * Asks the domain controller to sign up a user with the given credentials
     * @param username
     * @param pwd
     * @param admin
     * @return true if the used has been signed up, false otherwise
     */
    public boolean signUp(String username, String pwd, Boolean admin) {
        return ctrlDomain.signUp(username, pwd, admin);
    }

    /**
     * Asks the domain controller to change the user username
     * @param newUsername
     * @param oldUsername
     * @return true if the change has been done, false otherwise
     */
    public boolean changeUsername(String newUsername, String oldUsername) {
        return ctrlDomain.changeUsername(newUsername, oldUsername);
    }

    /**
     * Asks the domain controller to change the user password
     * @param newPassword
     * @param username
     */
    public void changePassword(String newPassword, String username) {
        ctrlDomain.changePassword(newPassword, username);
    }

    /**
     * Tells the domain controller to update the user configuration with the given theme
     * @param theme
     */
    public void changeTheme(String theme) {
        ctrlDomain.changeTheme(theme);
    }


    /**
     * Asks the domain controller to delete the actual user
     */
    public void deleteUser() { ctrlDomain.deleteUser();}


    /**
     * Asks the domain controller the actual user
     * @return the actual user in an arraylist
     */
    public ArrayList<String> getUser() {
        return ctrlDomain.getUser();
    }

    /**
     * Asks the domain controller to import the necessary data from the given path
     * @param path
     */
    public void importData(String path)  {
        ctrlDomain.importData(path);
    }

    /**
     * Asks the domain controller to sava all data to the given path
     */
    public void saveAll() {
        ctrlDomain.saveData();
    }

    /**
     * Asks the domain controller for the items
     * @return items in a hashmap
     */
    public HashMap<String, Item> getItems() {
        return ctrlDomain.getItems();
    }

    /**
     * Tells the domain controller to create a rating with the given data
     * @param rating
     */
    public void createRating(ArrayList<String> rating) {
        ctrlDomain.createRating(rating);
    }

    /**
     * Asks the domain controller if a database has been imported
     * @return true if a database has been imported, false otherwise
     */
    public boolean getImported() {
        return ctrlDomain.jsonExists();
    }

    /**
     * Asks the domain controller if the logged user is administrator
     * @return true, if the user is admin, false otherwise
     */
    public boolean isAdmin() {
        return ctrlDomain.isAdmin();
    }

    /**
     * Tells the domain controller to update the database config file
     * @param config
     */
    public void setDBConfig(HashMap<String, Pair<String, Integer>> config) {
        ctrlDomain.setDBConfig(config);
    }

    /**
     * Asks the domain controller for the database configuration
     * @return database configuration
     */
    public HashMap<String, Pair<String, Integer>> getDBConfig() {
        return ctrlDomain.getDBConfig();
    }

    /**
     * Tells the domain controller to log out the actual user
     */
    public void logOut() {
        ctrlDomain.logOut();
    }

    /**
     * Asks the domain controller for the quality of the recommendation
     * @return quality
     */
    public double calculateQuality() {
        return ctrlDomain.calculateQuality();
    }

    /**
     * Asks the domain controller to check if the .json files exist
     * @return true or false, depending on whether the files exist or not
     */
    public boolean jsonExists() {
        return ctrlDomain.jsonExists();
    }

    /**
     * Asks the domain controller to erase all the date generated
     */
    public void deleteData() {
        ctrlDomain.deleteData();
    }
}
