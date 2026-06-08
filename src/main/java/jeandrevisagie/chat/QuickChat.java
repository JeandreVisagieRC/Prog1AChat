package jeandrevisagie.chat;

import java.util.Scanner;

public class QuickChat {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // registration
        System.out.println("=== REGISTER ===");
        user registeredUser = registration.registerUser(scanner);

        if (registeredUser == null) {
            System.out.println("Registration failed. Exiting.");
            return;
        }

        // login
        System.out.println("\n=== LOGIN ===");
        boolean loggedIn = registration.loginUser(scanner, registeredUser);

        if (!loggedIn) {
            System.out.println("Access denied. Exiting.");
            return;
        }

        System.out.println("Welcome to QuickChat.");

        // message count
        System.out.print("How many messages would you like to send? ");
        int messageLimit = Integer.parseInt(scanner.nextLine().trim());
        int messageCount = 0;

        int choice;
        do {
            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Stored Messages");
            System.out.println("4) Quit");
            System.out.print("Choose an option: ");
            choice = Integer.parseInt(scanner.nextLine().trim());

            switch (choice) {
                case 1:
                    while (messageCount < messageLimit) {

                        // validate recipient
                        String recipient;
                        while (true) {
                            System.out.print("Enter recipient number (e.g. +27...): ");
                            recipient = scanner.nextLine();
                            Message temp = new Message(recipient, "temp");
                            String cellCheck = temp.checkRecipientCell();
                            System.out.println(cellCheck);
                            if (cellCheck.equals(
                                "Cell phone number successfully captured.")) {
                                break;
                            }
                        }

                        // validate message length
                        String messageText;
                        while (true) {
                            System.out.print("Enter your message (max 250 chars): ");
                            messageText = scanner.nextLine();
                            if (messageText.length() <= 250) {
                                break;
                            } else {
                                int excess = messageText.length() - 250;
                                System.out.println("Message exceeds 250 characters" +
                                    " by " + excess + "; please reduce the size.");
                            }
                        }

                        // create and process message
                        Message msg = new Message(recipient, messageText);
                        System.out.println("Message ID generated: " + msg.getMessageID());
                        System.out.println("Message Hash: " + msg.getMessageHash());

                        String result = msg.SentMessage();
                        System.out.println(result);

                        System.out.println("\n--- Message Details ---");
                        System.out.println("Message ID:   " + msg.getMessageID());
                        System.out.println("Message Hash: " + msg.getMessageHash());
                        System.out.println("Recipient:    " + msg.getRecipient());
                        System.out.println("Message:      " + msg.getMessage());

                        messageCount++;
                    }
                    System.out.println("Message limit reached.");
                    break;

                case 2:
                    System.out.println("Coming Soon.");
                    break;

                case 3:// provides a menu for stored messages with options to display, search, and delete messages, as well as view a report before returning to the main menu
    int storedChoice;
    do {
        System.out.println("\n--- Stored Messages Menu ---");
        System.out.println("1) Display all stored messages");
        System.out.println("2) Display longest message");
        System.out.println("3) Search by message ID");
        System.out.println("4) Search by recipient");
        System.out.println("5) Delete message by hash");
        System.out.println("6) Display report");
        System.out.println("7) Back");
        System.out.print("Choose: ");
        storedChoice = Integer.parseInt(scanner.nextLine().trim());

        switch (storedChoice) {
            case 1: System.out.println(Message.displayStoredMessages()); break;
            case 2: System.out.println(Message.getLongestMessage()); break;
            case 3:
                System.out.print("Enter message ID: ");
                System.out.println(Message.searchByMessageID(scanner.nextLine()));
                break;
            case 4:
                System.out.print("Enter recipient number: ");
                System.out.println(Message.searchByRecipient(scanner.nextLine()));
                break;
            case 5:
                System.out.print("Enter message hash: ");
                System.out.println(Message.deleteByHash(scanner.nextLine()));
                break;
            case 6: System.out.println(Message.displayReport()); break;
            case 7: break;
            default: System.out.println("Invalid option.");
        }
    } while (storedChoice != 7);
    break;

case 4://fulfils old case 3 functionality by printing all sent messages and the total count before exiting
    System.out.println(Message.printMessages());
    System.out.println("Total messages sent: " + Message.returnTotalMessages());
    System.out.println("Goodbye!");
    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }

        } while (choice != 4);

        scanner.close();
    }
}