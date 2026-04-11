package jeandrevisagie.chat;

import java.util.Scanner;

public class QuickChat {
    private static Scanner input = new Scanner(System.in);
    private static user currentUser = null;
    
    public static void main(String[] args) {
        System.out.println("=== Chat Application ===\n");
        while (true) {
            if (currentUser == null) showAuthMenu();
            else showMainMenu();
        }
    }
    
    private static void showAuthMenu() {
        System.out.println("\n1. Register | 2. Exit");
        System.out.print("Choice: ");
        if ("1".equals(input.nextLine().trim())) {
            registerUser();
        } else {
            System.out.println("Goodbye!");
            System.exit(0);
        }
    }
    
    private static void registerUser() {
        System.out.print("Username (with '_', max 5 chars): ");
        String user = input.nextLine();
        if (!checkUserName(user)) {
            System.out.println("Invalid username.");
            return;
        }
        
        System.out.print("Password (8+ chars, uppercase, digit, special): ");
        String pass = input.nextLine();
        if (!checkPasswordComplexity(pass)) {
            System.out.println("Invalid password.");
            return;
        }
        
        System.out.print("Phone (+27XXXXXXXXXX): ");
        String phone = input.nextLine();
        if (!checkPhone(phone)) {
            System.out.println("Invalid phone.");
            return;
        }
        
        currentUser = new user(user, pass, phone);
        System.out.println("Welcome, " + currentUser.getUsername() + "!");
    }
    
    private static void showMainMenu() {
        System.out.println("\n1. Create Message | 2. View Messages | 3. JSON Export | 4. Logout");
        System.out.print("Choice: ");
        switch(input.nextLine().trim()) {
            case "1": createMessage(); break;
            case "2": System.out.println(new Message("t", "t", "t").printMessages()); break;
            case "3": System.out.println(new Message("t", "t", "t").storeMessagesInJSON()); break;
            case "4": currentUser = null; System.out.println("Logged out."); break;
        }
    }
    
    private static void createMessage() {
        System.out.print("Message ID: ");
        String id = input.nextLine();
        System.out.print("Recipient Phone: ");
        String phone = input.nextLine();
        System.out.print("Content: ");
        String content = input.nextLine();
        
        Message msg = new Message(id, phone, content);
        
        if (!msg.checkMessageID()) {
            System.out.println("Invalid message ID.");
            return;
        }
        
        String validation = msg.checkRecipientCell();
        if (validation.startsWith("Error")) {
            System.out.println(validation);
            return;
        }
        
        System.out.println("Hash: " + msg.createMessageHash());
        System.out.println(msg.sentMessage());
    }
    
    private static boolean checkUserName(String name) {
        return name.contains("_") && name.length() <= 5;
    }
    
    private static boolean checkPasswordComplexity(String pd) {
        if (pd == null || pd.length() < 8) return false;
        boolean hasUpper = false, hasDigit = false, hasSpecial = false;
        for (char c : pd.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isDigit(c)) hasDigit = true;
            if ("!@#$%^&*".indexOf(c) >= 0) hasSpecial = true;
        }
        return hasUpper && hasDigit && hasSpecial;
    }
    
    private static boolean checkPhone(String phone) {
        return phone.startsWith("+27") && (phone.length() == 13 || phone.length() == 12);
    }
}
