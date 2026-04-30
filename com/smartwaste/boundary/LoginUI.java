package com.smartwaste.boundary;

import com.smartwaste.control.LoginController;
import com.smartwaste.entity.User;
import java.util.Scanner;


public class LoginUI {
    private Scanner scanner;
    private LoginController loginController;

    public LoginUI(Scanner scanner) {
        this.scanner = scanner;
        this.loginController = new LoginController();
    }

    
    public void displayLogin() {
        System.out.println("\n=========================================");
        System.out.println("    SMART WASTE MANAGEMENT SYSTEM");
        System.out.println("            USER LOGIN");
        System.out.println("=========================================");
    }

    
    public User getCredentials() {
        displayLogin();
        System.out.print("  Enter Username : ");
        String username = scanner.nextLine().trim();
        System.out.print("  Enter Password : ");
        String password = scanner.nextLine().trim();

        try {
            User user = loginController.authenticate(username, password);
            System.out.println("\n  [SUCCESS] Login successful! Welcome, "
                    + user.getUsername() + " (" + user.getRole() + ")");
            return user;
        } catch (Exception e) {
            System.out.println("\n  [ERROR] " + e.getMessage());
            return null;
        }
    }
}
