package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import utils.Pair;
import domain.classes.User;

import java.io.*;
import java.util.*;

/**
 * @author Ricard Guixaró Trancho
 */

public class CtrlPersistence {
    private static CtrlPersistence instance;

    /**
     * Empty constructor of a data controller
     */
    private CtrlPersistence() {}

    /**
     * Returns the instance of the data controller
     * @return returns instance (singleton) if exists, and creates a new one if it did no
     */
    public static CtrlPersistence getInstance() {
        if (instance == null) instance = new CtrlPersistence();
        return instance;
    }

    /**
     * Reads a .csv file
     * @param filename
     * @return returns the .csv file transformed in a matrix of strings
     */
    public ArrayList<ArrayList<String>> readCSV(String filename) {
        BufferedReader br = null;
        String cvsSplitBy = ",";
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(filename));
            String line = null;
            while((line = br.readLine()) != null) {
                String[] vals = line.split(cvsSplitBy);
                data.add(new ArrayList<>(Arrays.asList(vals)));
            }
        } catch (IOException e) {}
        finally {
            if (br != null) {
                try { br.close(); }
                catch (IOException e) {}
            }
        }
        return data;
    }

    /**
     * Writes the .csv file identified by the filename
     * @param row
     * @param filename
     */
    public static void writeCSV(ArrayList<String> row, String filename) {
        File file = new File("data/"+filename);
        FileWriter fileWriter = null;
        try {
            //check if the user has already rated this item and update it
            fileWriter = new FileWriter(file, true);
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < row.size(); i++) {
                line.append(row.get(i));
                if (i != row.size()-1) {
                    line.append(',');
                }
            }
            line.append("\n");
            fileWriter.write(line.toString());
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * If exists a user that username, returns null, otherwise, creates the user and returns its id
     * @param username
     * @param pwd
     * @return userId if it could be created, null otherwise
     */
    public String signUp(String username, String pwd, Boolean admin) {
        List<User> users = readJsonUsers();
        String id = null;
        if (users == null) users = new ArrayList<>();
        else {
            if (usernameUsed(users, username)) return null;
            else {
                id = String.valueOf(Integer.parseInt(users.get(users.size() - 1).getUserId()) + 1);
                users.add(new User(id, username, pwd, admin));
            }
        }
        if (id == null) {
            users.add(new User("1", username, pwd, admin));
            id = "1";
        }
        writeJsonUsers(users);
        return id;
    }

    public void createUser(String id) {
        List<User> users = readJsonUsers();
        users.add(new User(id, id, "1234", false));
        writeJsonUsers(users);
    }

    /**
     * If exists a user with those credentials, the function returns its identifier.
     * @param username
     * @param pwd
     * @return the userdata, null otherwise
     */
    public ArrayList<String> logIn(String username, String pwd) {
        List<User> users = readJsonUsers();
        if (users == null) return null;
        else {
            for (User u : users) {
                if (u.getUsername().equals(username) && u.getPassword().equals(pwd)) return u.discretize();
            }
        }
        return null;
    }

    /**
     * Reads the user json file and, if the newUsername is not being used, the change is completed successfully.
     * @param newUsername
     * @param oldUsername
     * @return true if the change has been completed successfully, false otherwise
     */
    public boolean changeUsername(String newUsername, String oldUsername) {
        List<User> users = readJsonUsers();
        if (usernameUsed(users, newUsername)) return false;
        updateJsonUsers(newUsername, oldUsername, users, "username");
        return true;
    }

    /**
     * Reads the user json file and, changes the password for the given user. Then, updates the json file.
     * @param newPassword
     * @param username
     */
    public void changePassword(String newPassword, String username) {
        List<User> users = readJsonUsers();
        updateJsonUsers(newPassword, username, users, "password");
        writeJsonUsers(users);    }

    /**
     * Asks the user data controller to change the theme of the given user
     * @param username
     * @param newTheme
     */
    public void changeTheme(String username, String newTheme) {
        List<User> users = readJsonUsers();
        updateJsonUsers(newTheme, username, users, "theme");
        writeJsonUsers(users);    }

    /**
     * Reads the user json file and deletes the given user, then, updates the json file.
     * @param username
     */
    public void deleteUser(String username) {
        List<User> users = readJsonUsers();
        users.removeIf(u -> u.getUsername().equals(username));
        writeJsonUsers(users);
    }

    /**
     * Reads the user json file
     * @return returns a list of users if the file existed, null otherwise
     */
    private static List<User> readJsonUsers() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            File file = new File("data/users.json");
            FileReader fileReader = new FileReader(file);
            return gson.fromJson(fileReader, new TypeToken<List<User>>() {}.getType());
        } catch (FileNotFoundException e) {return  null;}
    }

    /**
     * Checks if a given username is being used by another user
     * @param users
     * @param username
     * @return true if it is being used, false otherwise
     */
    private static boolean usernameUsed(List<User> users, String username) {
        for (User u : users) if (u.getUsername().equals(username)) return true;
        return false;
    }

    /**
     * Writes the user json file with the user list passed in the parameter
     * @param users
     */
    private static void writeJsonUsers(List<User> users) {
        File file = new File("data/users.json");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(users, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            new File("data").mkdir();
            writeJsonUsers(users);
        }
    }

    /**
     * Updates the user json file with the data passed in the parameters. Then, writes the json file again.
     * @param newValue
     * @param username
     * @param users
     * @param field
     */
    private static void updateJsonUsers(String newValue, String username, List<User> users, String field) {
        boolean updated = false;
        int i = 0;;
        while (i < users.size() && !updated) {
            if (users.get(i).getUsername().equals(username)) {
                updated = true;
                if (field.equals("username")) {
                    users.get(i).setUsername(newValue);
                } else if (field.equals("password")) {
                    users.get(i).setPassword(newValue);
                } else {
                    users.get(i).setTheme(newValue);
                }
            }
            ++i;
        }
        writeJsonUsers(users);
    }

    /**
     * Writes some important data regarding the configuration of the database
     * @param config
     */
    public void writeJsonConfig(HashMap<String, Pair<String, Integer>> config) {
        writeJson(config, "config.json");
    }

    /**
     * Reads the database configuration json file
     * @return hashMap with database configuration
     */
    public HashMap<String, Pair<String, Integer>> readJsonConfig() {
        File file = new File("data/config.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileReader fileReader = new FileReader(file);
            return gson.fromJson(fileReader, new TypeToken<HashMap<String, Pair<String, Integer>>>() {}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Stores the items and their distances, the calculated usergroups and the ratings.
     * @param distances
     * @param groupUsers
     */
    public void storeDatabase(HashMap<String, TreeMap<Double, String>> distances, HashMap<String, ArrayList<String>> groupUsers){
        writeJson(distances, "distances.json");
        writeJson(groupUsers, "usergroups.json");
    }

    /**
     * Reads from the json file the distances between items
     * @return the distances between items in a matrix ordered by similarity
     */
    public HashMap<String, TreeMap<Double, String>> readJsonDistances() {
        File file = new File("data/distances.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileReader fileReader = new FileReader(file);
            return gson.fromJson(fileReader, new TypeToken<HashMap<String, TreeMap<Double, String>>>() {}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Reads from the json file the usergroups calculated
     * @return the usergroups in a matrix
     */
    public HashMap<String, ArrayList<String>> readJsonUserGroups() {
        File file = new File("data/usergroups.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileReader fileReader = new FileReader(file);
            return gson.fromJson(fileReader, new TypeToken<HashMap<String, ArrayList<String>>>() {}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Writes the json file specified by the parameter filename
     * @param obj
     * @param filename
     */
    private static void writeJson(Object obj, String filename) {
        File file = new File("data/"+filename);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(obj, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks whether the json files storing the preprocessed data exist or not
     * @return true if they exist, false otherwise
     */
    public boolean jsonExists() {
        File jsonDistances = new File("data/distances.json");
        File jsonUserGroups = new File("data/usergroups.json");
        File jsonConfig = new File("data/config.json");
        return jsonDistances.exists() && jsonUserGroups.exists() && jsonConfig.exists();
    }

    /**
     * Erased the stored json files
     */
    public void deleteJsons() {
        File jsonDistances = new File("data/distances.json");
        jsonDistances.delete();
        File jsonUserGroups = new File("data/usergroups.json");
        jsonUserGroups.delete();
        File jsonConfig = new File("data/config.json");
        jsonConfig.delete();
    }
}
