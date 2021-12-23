package drivers;

import domain.classes.User;

import java.util.Scanner;

/**
 * @author Ricard Guixaró Trancho
 */

public class DriverUser {
    private static Scanner scanner;
    private static User user;

    public static void main(String[] args) {
        String s;
        scanner = new Scanner(System.in);
        showUsage();
        while (!(s = scanner.nextLine()).equals("0")) {
            switch (s) {
                case "1":
                    createUser();
                    break;
                case "2":
                    isAdmin();
                    break;
                case "3":
                    setCredentials();
                    break;
                case "4":
                    setGroup();
                    break;
                case "5":
                    userData();
                    break;
                case "6":
                    showUsage();
                    break;
                case "7":
                    System.exit(0);
            }
        }
    }

    private static void createUser() {
        System.out.println("Insert the userId");
        String userId = scanner.nextLine();
        System.out.println("Insert 'true' if the user has administrative privileges, 'false' otherwise");
        //user = new User(userId, scanner.nextBoolean());
        if (user.isAdmin()) System.out.println("New Administrative User created with userId=" + user.getUserId());
        else System.out.println("New User created with userId=" + user.getUserId());
    }

    private static void isAdmin() {
        try {
            if (user.isAdmin()) System.out.println("User" + user.getUserId() + " is administrative");
            else System.out.println("User" + user.getUserId() + " is not administrative");
        } catch (Exception e) {
            System.out.println("User not created yet. Try option 1");
        }
    }

    private static void setCredentials() {
        try {
            try {
                if (user.getUsername() == null) {
                    System.out.println("Insert the desired username");
                    user.setUsername(scanner.nextLine());
                    System.out.println("Insert the desired password");
                    user.setPassword(scanner.nextLine());
                    System.out.println("User" + user.getUserId() + " has username: " + user.getUsername() + " and password: " + user.getPassword());
                }
            } catch (Exception e) {
                System.out.println("User" + user.getUserId() + " already has credentials: [username=" + user.getUsername() + ", password=" + user.getPassword() + "]");
            }
        } catch (Exception e) {
            System.out.println("User not created yet. Try option 1");
        }
    }

    private static void setGroup() {
        try {
            if (user.getGroupId() == null) {
                System.out.println("Insert the desired groupId");
                user.setGroupId(scanner.nextLine());
                System.out.println("User" + user.getUserId() + " is now part of the group of users " + user.getGroupId());
            } else System.out.println("User" + user.getUserId() + " already is part of group of users");
        } catch (Exception e) {
            System.out.println("User not created yet. Try option 1");
        }
    }

    private static void userData() {
        try {
            System.out.println("Information of the user " + user.getUserId());
            System.out.println(user.toString());
        } catch (Exception e) {
            System.out.println("User not created yet. Try option 1");
        }
    }

    private static void showUsage() {
        System.out.println("#####################################");
        System.out.println("Driver of the User Class");
        System.out.println("Options:");
        System.out.println("1. Create a User");
        System.out.println("2. Check if admin");
        System.out.println("3. Set credentials");
        System.out.println("4. Set groupUsers");
        System.out.println("5. User Data");
        System.out.println("6. Show Usage");
        System.out.println("7. Exit");
        System.out.println("#####################################");
    }
}
