package drivers;

import domain.classes.UserGroup;

import java.util.Scanner;

/**
 * @author Ricard Guixaró Trancho
 */

public class DriverUserGroup {
    private static Scanner scanner;
    private static UserGroup userGroup;

    public static void main(String[] args) {
        String s;
        scanner = new Scanner(System.in);
        showUsage();
        while (!(s = scanner.nextLine()).equals("0")) {
            switch (s) {
                case "1":
                    createUserGroup();
                    break;
                case "2":
                    addUser();
                    break;
                case "3":
                    listMembers();
                    break;
                case "4":
                    checkMember();
                    break;
                case "5":
                    showUsage();
                    break;
                case "6":
                    System.exit(0);
            }
        }
    }

    private static void createUserGroup() {
        System.out.println("Insert the groupId");
        userGroup = new UserGroup(scanner.nextLine());
        System.out.println("New UserGroup created with groupId=" + userGroup.getGroupId());
    }

    private static void addUser() {
        try {
            String group = userGroup.getGroupId();
            System.out.println("Insert the user that will be added to the group of users");
            String user = scanner.nextLine();
            if (userGroup.contains(user))
                System.out.println("User " + user + " is already part of this group of users");
            else {
                userGroup.addUser(user);
                System.out.println("User " + user + " has been added to the UserGroup " + group);
            }
        } catch (Exception e) {
            System.out.println("UserGroup not created yet. Try option 1");
        }
    }

    private static void listMembers() {
        try {
            System.out.println("UserGroup" + userGroup.getGroupId() + " has the following users:");
            System.out.println(userGroup.getUsers());
        } catch (Exception e) {
            System.out.println("UserGroup not created yet. Try option 1");
        }
    }

    private static void checkMember() {
        try {
            String group = userGroup.getGroupId();
            System.out.println("Insert the user in order to check if it belongs to the group");
            String user = scanner.nextLine();
            if (userGroup.contains(user)) System.out.println("User" + user + " is member of group " + group);
            else System.out.println("User " + user + " is not a member of group " + group);
        } catch (Exception e) {
            System.out.println("UserGroup not created yet. Try option 1");
        }
    }

    private static void showUsage() {
        System.out.println("#####################################");
        System.out.println("Driver of the UserGroup Class");
        System.out.println("Options:");
        System.out.println("1. Create a UserGroup");
        System.out.println("2. Add User to group");
        System.out.println("3. List members of the group");
        System.out.println("4. Check if user belongs to the group");
        System.out.println("5. Show Usage");
        System.out.println("6. Exit");
        System.out.println("#####################################");
    }
}

