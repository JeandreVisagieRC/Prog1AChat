package jeandrevisagie.chat;

import java.util.Scanner;

public class QuickChat {

    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to QuickChat.");

        user currentUser = registerAndLogin();
        if (currentUser == null) {
            System.out.println("Login failed. Goodbye.");
            return;
        }

        System.out.print("\nHow many messages would you like to send? ");
        int limit;
        try {
            limit = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Goodbye.");
            return;
        }

        int sent = 0;
        boolean running = true;

        while (running && sent < limit) {
            System.out.println("\n1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.print("Choice: ");

            switch (input.nextLine().trim()) {
                case "1":
                    if (sendMessage()) sent++;
                    break;
                case "2":
                    System.out.println("Coming Soon.");
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }

        System.out.println("\nTotal messages sent: " + Message.returnTotalMessages());
    }

    private static user registerAndLogin() {
        System.out.println("\n--- Registration ---");
        System.out.print("Username (contains '_', max 5 chars): ");
        String username = input.nextLine();
        if (!registration.checkUserName(username)) {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            return null;
        }

        System.out.print("Password (8+ chars, uppercase, digit, special char): ");
        String password = input.nextLine();
        if (!registration.checkPasswordComplexity(password)) {
            System.out.println("Password is not correctly formatted.");
            return null;
        }

        System.out.print("Phone number (+27XXXXXXXXX): ");
        String phone = input.nextLine();
        if (!registration.checkPhone(phone)) {
            System.out.println("Phone number is incorrectly formatted or does not contain international code.");
            return null;
        }

        user registered = new user(username, password, phone);
        System.out.println("Registration successful!");

        System.out.println("\n--- Login ---");
        System.out.print("Username: ");
        String loginUser = input.nextLine();
        System.out.print("Password: ");
        String loginPass = input.nextLine();

        if (loginUser.equals(registered.getUsername()) && loginPass.equals(registered.getPassword())) {
            System.out.println("Welcome, " + registered.getUsername() + "! Login successful.");
            return registered;
        }

        System.out.println("Invalid username or password.");
        return null;
    }

    private static boolean sendMessage() {
        System.out.print("\nRecipient cell number: ");
        String recipient = input.nextLine();

        System.out.print("Message: ");
        String content = input.nextLine();

        String lengthCheck = Message.checkMessageLength(content);
        if (!lengthCheck.equals("Message ready to send.")) {
            System.out.println(lengthCheck);
            return false;
        }

        Message msg = new Message(recipient, content);

        String recipientCheck = msg.checkRecipientCell();
        if (!recipientCheck.equals("Cell phone number successfully captured.")) {
            System.out.println(recipientCheck);
            return false;
        }

        String hash = msg.createMessageHash();
        System.out.println("Message Hash: " + hash);

        System.out.println("\n1. Send Message");
        System.out.println("2. Store Message");
        System.out.println("3. Disregard Message");
        System.out.print("Choice: ");
        String choice = input.nextLine().trim();

        String action;
        switch (choice) {
            case "1": action = "Send Message";      break;
            case "2": action = "Store Message";     break;
            case "3": action = "Disregard Message"; break;
            default:
                System.out.println("Invalid option.");
                return false;
        }

        String result = msg.sentMessage(action);
        System.out.println(result);

        if ("Send Message".equals(action)) {
            System.out.println("\nMessage ID: "   + msg.getMessageID());
            System.out.println("Message Hash: "  + msg.getMessageHash());
            System.out.println("Recipient: "     + msg.getRecipientCell());
            System.out.println("Message: "       + msg.getMessageContent());
            return true;
        }

        return false;
    }
}
