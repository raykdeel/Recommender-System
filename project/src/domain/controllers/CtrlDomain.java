package domain.controllers;

import data.CtrlPersistence;
import domain.classes.*;
import utils.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * @author Ricard Guixaró Trancho
 */

public class CtrlDomain {
    private static CtrlDomain instance;
    private CtrlPersistence ctrlPersistence;
    private CtrlDomainUser ctrlDomainUser;

    private SetItems setItems;
    private SetRatings setRatings;
    private SetRatings setKnown;
    private SetRatings setUnknown;
    private SetAttributes setAttributes;
    private SetUserGroup setUserGroups;
    private Recommendation recommendation;
    private String path;
    private HashMap<String, TreeMap<Double, String>> distancesItems;
    private HashMap<String, Pair<String, Integer>> dbConfig;

    /**
     * Empty constructor of the domain controller
     */
    private CtrlDomain() {
        ctrlPersistence = CtrlPersistence.getInstance();
        ctrlDomainUser = CtrlDomainUser.getInstance();
        setAttributes = new SetAttributes();
        setItems = new SetItems();
        setRatings = new SetRatings();
        setKnown = new SetRatings();
        setUnknown = new SetRatings();
        setUserGroups = new SetUserGroup();
        distancesItems = new HashMap<>();
        dbConfig = new HashMap<>();
        if (jsonExists()) getPreprocessedData();
    }

    /**
     * Returns the instance of the domain controller
     * @return returns the instance (singleton) if exists, and creates a new one if it does not
     */
    public static CtrlDomain getInstance() {
        if (instance == null) instance = new CtrlDomain();
        return instance;
    }

    /**
     * Add a boolean attribute to the set of attributes named name
     * @param name
     */
    public void createBooleanAttribute(String name) {
        setAttributes.addAttribute(new BooleanAttribute(name));
    }

    /**
     * Add a double attribute to the set of attributes named name
     * @param name
     */
    public void createDoubleAttribute(String name) {
        setAttributes.addAttribute(new DoubleAttribute(name));
    }

    /**
     * Add an integer attribute to the set of attributes named name
     * @param name
     */
    public void createIntegerAttribute(String name) {
        setAttributes.addAttribute(new IntegerAttribute(name));
    }

    /**
     * Add a string attribute to the set of attributes named name
     * @param name
     */
    public void createStringAttribute(String name) {
        setAttributes.addAttribute(new StringAttribute(name));
    }

    /**
     * Add a set attribute to the set of attributes named name
     * @param name
     */
    public void createSetAttribute(String name) {
        setAttributes.addAttribute(new SetAttribute(name));
    }

    /**
     * Add a rating to the set of ratings indicated by csv
     * @param newRating
     */
    public void createRating(ArrayList<String> newRating) {
        String itemId = newRating.get(0);
        Double score = Double.parseDouble(newRating.get(1));
        String userId;
        if (newRating.size()>2) userId = newRating.get(2);
        else userId = ctrlDomainUser.getUser().getUserId();
        Rating r = new Rating(userId, itemId, score);
        newRating.add(userId);
        Double temp;
        User activeUser = ctrlDomainUser.getUser();
        if (activeUser.getUsername().equals(activeUser.getUserId())) {
            temp = setKnown.addRating(r.getUserID(), r);
            ctrlPersistence.writeCSV(newRating, "ratings.test.known.csv");
        }
        else {
            temp = setRatings.addRating(r.getUserID(), r);
            ctrlPersistence.writeCSV(newRating, "ratings.db.csv");
        }
        setItems.getItems().get(itemId).updateStats(score, temp);
    }

    /**
     * Creates an item and adds it to the set of items
     * @param newItem (values of each attribute)
     *
     */
    private void createItem(ArrayList<String> newItem) {
        Item item = new Item();
        for (int i = 0; i < newItem.size(); ++i) {
            if (setAttributes.getAttributesNames().get(i).equals("id")) item.setItemId(newItem.get(i));
            item.addValue(newItem.get(i), setAttributes.getAttributes().get(i));
        }
        setItems.addItem(item);
    }

    /**
     * Gets the ratings read by the persistence controller, and creates a rating for each of them
     * @param filename
     * @param csv
     */
    public void loadRatings(String filename, String csv) {
        ArrayList<ArrayList<String>> data = ctrlPersistence.readCSV(filename);
        data.remove(0);
        createRatingsFrom(data, csv);
    }

    /**
     * Gets the items read by the persistence controller and creates all necessary attributes and items.
     * Moreover, it also sets the arraylist of the item attributes and calcules the distances between items.
     * @param data
     */
    private void createItemsFrom(ArrayList<ArrayList<String>> data) {
        ArrayList<String> header = data.get(0);
        data.remove(0);
        ArrayList<String> values = data.get(0);
        for (int i = 0; i < values.size(); i++){
            String value = values.get(i);
            String attr_name = header.get(i);
            Boolean typeFound = false;
            if (value.equals("true") || value.equals("false")) {
                createBooleanAttribute(attr_name);
                typeFound = true;
            } else if (value.split("\\.").length > 1) {
                try {
                    Double.parseDouble(value);
                    createDoubleAttribute(attr_name);
                    typeFound = true;
                } catch (NumberFormatException e) {}
            } else if (parseLine(value, ';').size() > 1) {
                createSetAttribute(attr_name);
                typeFound = true;
            }
            if (!typeFound) {
                try {
                    Integer.parseInt(value);
                    createIntegerAttribute(attr_name);
                    typeFound = true;
                } catch (NumberFormatException e) {}
            }
            if (!typeFound) createStringAttribute(attr_name);
        }
        for (int i = 0; i < data.size(); ++i) createItem(data.get(i));
    }


    /**
     * Creates ratings from the given string matrix, and stores them in the corresponding set of ratings
     * @param data
     * @param csv
     */
    private void createRatingsFrom(ArrayList<ArrayList<String>> data, String csv) {
        for (int i = 1; i < data.size(); ++i) {
            ArrayList<String> rating = data.get(i);
            String itemId = rating.get(0);
            Double score = Double.parseDouble(rating.get(1));
            String userId;
            User activeUser = ctrlDomainUser.getUser();
            if (rating.size()>2) userId = rating.get(2);
            else userId = activeUser.getUserId();
            Rating r = new Rating(userId, itemId, score);
            Double temp;
            if (csv.equals("ratings")) {
                temp = setRatings.addRating(r.getUserID(), r);
            } else if (csv.equals("known")) {
                temp = setKnown.addRating(r.getUserID(), r);
            } else {
                temp = setUnknown.addRating(r.getUserID(), r);
            }
            setItems.getItems().get(itemId).updateStats(score, temp);
        }
    }

    /**
     * Returns the name of the attributes
     * @return itemAttributes
     */
    public ArrayList<String> getAttributes() {
        return setAttributes.getAttributesNames();
    }

    /**
     * Returns the items
     * @return hashmap of items of setItems
     */
    public HashMap<String, Item> getItems() {
        return setItems.getItems();
    }

    /**
     * Asks the user domain controller for the actual user
     * @return ArrayList with user credentials (id, username, password)
     */
    public ArrayList<String> getUser() {
        return ctrlDomainUser.getUser().discretize();
    }

    /**
     * Asks the user domain controller to check if the given credentials actually belong to some user
     * @param username
     * @param pwd
     * @return true if that user exists, false othrewise
     */
    public boolean logIn(String username, String pwd) {
        return ctrlDomainUser.logIn(username, pwd);
    }

    /**
     * Asks the user domain controller to sign up a new user into the database with the given credentials
     * @param username
     * @param pwd
     * @param admin
     * @return returns true, if the used could be created, false otherwise
     */
    public boolean signUp(String username, String pwd, Boolean admin) {
        return ctrlDomainUser.signUp(username, pwd, admin);
    }

    /**
     * Asks the user domain controller to create a user.
     * @param id
     */
    public void createUser(String id) {
        ctrlDomainUser.createUser(id);
    }

    /**
     * Asks the user domain controller if a user can change its username, if true, the user is modified.
     * @param newUsername
     * @param oldUsername
     * @return true if the change has been completed successfully, false otherwise
     */
    public boolean changeUsername(String newUsername, String oldUsername) {
        return ctrlDomainUser.changeUsername(newUsername, oldUsername);
    }

    /**
     * Asks the user domain controller to change the password for a user
     * @param newPassword
     * @param username
     */
    public void changePassword(String newPassword, String username) {
        ctrlDomainUser.changePassword(newPassword, username);
    }

    /**
     * Asks the user domain controller to update the user theme
     * @param theme
     */
    public void changeTheme(String theme) {
        ctrlDomainUser.changeTheme(theme);
    }

    /**
     * Asks the user domain controller to delete the actual user.
     */
    public void deleteUser() {
        ctrlDomainUser.deleteUser();
    }

    /**
     * Asks the user domain controller to log out the actual user
     */
    public void logOut() {
        ctrlDomainUser.logOut();
    }

    /**
     * Asks the user domain controller if the active user is admin
     * @return true, if the user is admin, false otherwise
     */
    public boolean isAdmin() {
        return ctrlDomainUser.isAdmin();
    }

    /**
     * Asks the persistence controller to read all preprocessed data from both .json and .csv
     */
    private void getPreprocessedData() {
        distancesItems = ctrlPersistence.readJsonDistances();
        setUserGroups.setGroups(ctrlPersistence.readJsonUserGroups());
        path = ctrlPersistence.readJsonConfig().get("path").first;
        createItemsFrom(ctrlPersistence.readCSV(path + "/items.csv"));
        loadRatings(path+"/ratings.db.csv", "ratings");
        loadRatings(path+"/ratings.test.known.csv", "known");
        loadRatings(path+"/ratings.test.unknown.csv", "unknown");
    }

    /**
     * Tells the persistence controller to save all data
     */
    public void saveData() {
        ctrlPersistence.storeDatabase(distancesItems, setUserGroups.discretize());
    }

    /**
     * Returns the recommended k items obtained using the method determined by algorithmName
     * @param algorithmName
     * @return k recommended items
     */
    public TreeMap<Double, String> getRecommendation(String algorithmName) {
        TreeMap<Double, String> temp;
        Random randomNum = new Random();
        int k = randomNum.nextInt(6)+1;
        boolean fakeUser = false;
        User activeUser = ctrlDomainUser.getUser();
        if (activeUser.getUsername().equals(activeUser.getUserId())) fakeUser = true;
        if (algorithmName.equals("collaborative")) {
            CollaborativeFiltering collaborativeFiltering = new CollaborativeFiltering();
            if (fakeUser) {
                temp = collaborativeFiltering.algorithm(setKnown, setUnknown, activeUser.getUserId(), k);
            } else {
                temp = collaborativeFiltering.algorithm(setRatings, setRatings, activeUser.getUserId(), k);
            }
        } else if (algorithmName.equals("contentBased")) {
            ContentBased contentBased = new ContentBased();
            contentBased.setDistances(distancesItems);
            if (fakeUser) {
                temp = contentBased.algorithm(setKnown, setUnknown, activeUser.getUserId(), k);
            } else {
                temp = contentBased.algorithm(setRatings, setRatings, activeUser.getUserId(), k);
            }
        } else {
            HybridApproaches hybridApproaches = new HybridApproaches();
            hybridApproaches.setDistances(distancesItems);
            if (fakeUser) {
                temp = hybridApproaches.algorithm(setKnown, setUnknown, activeUser.getUserId(), k);
            } else {
                temp = hybridApproaches.algorithm(setRatings, setRatings, activeUser.getUserId(), k);
            }
        }
        recommendation = new Recommendation(temp);
        return recommendation.getRecommendedItems();
    }

    //falta treure rated ratings de known unknown

    /**
     * Asks the Distance class to calculate the distances between the whole set of items.
     */
    private void calculateDistancesItems() {
        distancesItems = Distance.calculateItemDistances(setItems);
    }

    /**
     * Makes the necessary calls to import all the data needed.
     * @param path
     */
    public void importData(String path) {
        createItemsFrom(ctrlPersistence.readCSV(path + "/items.csv"));
        loadRatings(path+"/ratings.db.csv", "ratings");
        loadRatings(path+"/ratings.test.known.csv", "known");
        loadRatings(path+"/ratings.test.unknown.csv", "unknown");
        if (jsonExists()) {
            getPreprocessedData();
        } else {
            calculateDistancesItems();
            for (String id : setKnown.getUsers()) {
                createUser(id);
            }
            saveData();
        }
    }

    /**
     * Asks the persitence controller to check whether the .json files exist
     * @return true if they do, false otherwise
     */
    public boolean jsonExists() {
        return ctrlPersistence.jsonExists();
    }

    /**
     * Gets the set of groups
     * @return groups of users
     */
    public HashMap<String, UserGroup> getGroupUsers() {
        return setUserGroups.getGroups();
    }

    /**
     * Gets the usergroup identified by groupId
     * @param groupId
     * @return UserGroup
     */
    public UserGroup getUserGroup(String groupId) {
        return setUserGroups.getGroup(groupId);
    }

    /**
     * Returns an arraylist used to check whether an attribute is a set or not.
     * @param line
     * @param delimiter
     * @return arraylist with multiple values.
     */
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

    /**
     * Tells the persistence controller to update the database config file
     * @param config
     */
    public void setDBConfig(HashMap<String, Pair<String, Integer>> config) {
        ctrlPersistence.writeJsonConfig(config);
    }

    /**
     * Returns the database configuration
     * @return if it exists, returns it, if not, asks for it to the persistence controller
     */
    public HashMap<String, Pair<String, Integer>> getDBConfig() {
        if (!dbConfig.isEmpty()) {
            return dbConfig;
        }
        return ctrlPersistence.readJsonConfig();
    }

    /**
     * Calculates the quality of the recommendation
     * @return the quality of the recommendation
     */
    public double calculateQuality() {
        String userId = ctrlDomainUser.getUser().getUserId();
        return recommendation.calculateQuality(setUnknown, userId);
    }

    public void deleteData() {
        setAttributes = new SetAttributes();
        setItems = new SetItems();
        setRatings = new SetRatings();
        setKnown = new SetRatings();
        setUnknown =new SetRatings();
        setUserGroups = new SetUserGroup();
        distancesItems = new HashMap<>();
        dbConfig = new HashMap<>();
        ctrlPersistence.deleteJsons();
    }
}

