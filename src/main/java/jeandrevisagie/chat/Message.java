package jeandrevisagie.chat;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class Message {

    private String messageID;
    private int numMessagesSent;
    private String recipientCell;
    private String messageContent;
    private String messageHash;
    private boolean isSent;

    private static List<Message> messageHistory = new ArrayList<>();
    private static int totalMessagesSent = 0;
    private static int messageCounter = 0;

    private static final int MAX_MESSAGE_LENGTH = 250;

    // Production constructor – messageID is auto-generated
    public Message(String recipientCell, String messageContent) {
        this.messageID = generateMessageID();
        this.numMessagesSent = messageCounter++;
        this.recipientCell = recipientCell;
        this.messageContent = messageContent;
        this.isSent = false;
    }

    // Testing constructor – messageID is provided so hashes are deterministic
    public Message(String messageID, String recipientCell, String messageContent) {
        this.messageID = messageID;
        this.numMessagesSent = messageCounter++;
        this.recipientCell = recipientCell;
        this.messageContent = messageContent;
        this.isSent = false;
    }

    private String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

    /** Returns true if messageID is not more than 10 characters. */
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    /** Returns a formatted string confirming the generated message ID. */
    public String displayMessageID() {
        return "Message ID generated: " + messageID;
    }

    /** Validates the recipient cell number – must start with an international code ('+').  */
    public String checkRecipientCell() {
        if (recipientCell == null || recipientCell.isEmpty() || !recipientCell.startsWith("+")) {
            return "Cell phone number is incorrectly formatted or does not contain an International code. Please correct the number and try again.";
        }
        return "Cell phone number successfully captured.";
    }

    /** Validates message length. Returns a human-readable result string. */
    public static String checkMessageLength(String message) {
        if (message == null || message.length() <= MAX_MESSAGE_LENGTH) {
            return "Message ready to send.";
        }
        int excess = message.length() - MAX_MESSAGE_LENGTH;
        return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
    }

    /**
     * Generates the message hash in the format:
     *   <first 2 chars of messageID>:<message number>:<FIRSTWORDLASTWORD>
     * All alpha characters forced to uppercase; punctuation stripped.
     */
    public String createMessageHash() {
        if (messageID == null || messageID.length() < 2 || messageContent == null || messageContent.trim().isEmpty()) {
            return "Error: Invalid message data.";
        }

        String idPrefix = messageID.substring(0, 2);
        String[] words = messageContent.trim().split("\\s+");
        String firstWord = words[0].toUpperCase().replaceAll("[^A-Z]", "");
        String lastWord = words[words.length - 1].toUpperCase().replaceAll("[^A-Z]", "");

        this.messageHash = idPrefix + ":" + numMessagesSent + ":" + firstWord + lastWord;
        return this.messageHash;
    }

    /**
     * Processes the chosen action for a message.
     * Accepts: "Send Message", "Disregard Message", "Store Message".
     */
    public String sentMessage(String action) {
        switch (action) {
            case "Send Message":
                this.isSent = true;
                totalMessagesSent++;
                messageHistory.add(this);
                return "Message successfully sent.";
            case "Disregard Message":
                return "Press 0 to delete the message.";
            case "Store Message":
                messageHistory.add(this);
                return "Message successfully stored.";
            default:
                return "Invalid choice.";
        }
    }

    /** Interactive version – prompts the user and delegates to sentMessage(String). */
    public String sentMessage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n1. Send Message");
        System.out.println("2. Store Message");
        System.out.println("3. Disregard Message");
        System.out.print("Choice: ");
        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1": return sentMessage("Send Message");
            case "2": return sentMessage("Store Message");
            case "3": return sentMessage("Disregard Message");
            default:  return "Invalid choice.";
        }
    }

    /**
     * Returns a formatted string of all messages in the session.
     * Order per spec: Message ID → Message Hash → Recipient → Message.
     */
    public String printMessages() {
        if (messageHistory.isEmpty()) {
            return "No messages in history.";
        }
        StringBuilder sb = new StringBuilder("\n--- Message History ---\n");
        for (Message msg : messageHistory) {
            sb.append("Message ID: ").append(msg.messageID).append("\n");
            sb.append("Message Hash: ").append(msg.messageHash).append("\n");
            sb.append("Recipient: ").append(msg.recipientCell).append("\n");
            sb.append("Message: ").append(msg.messageContent).append("\n\n");
        }
        return sb.toString();
    }

    /** Returns the total number of messages sent (not stored or disregarded). */
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    /** Appends this message to messages.json. */
    public void storeMessage() {
        JSONObject json = new JSONObject();
        json.put("messageID", messageID);
        json.put("messageHash", messageHash != null ? messageHash : "");
        json.put("recipient", recipientCell);
        json.put("message", messageContent);

        try (FileWriter fw = new FileWriter("messages.json", true)) {
            fw.write(json.toString(2));
            fw.write("\n");
        } catch (IOException e) {
            System.err.println("Error storing message: " + e.getMessage());
        }
    }

    /** Returns JSON string of all messages (for display/export). */
    public String storeMessagesInJSON() {
        JSONArray array = new JSONArray();
        for (Message msg : messageHistory) {
            JSONObject obj = new JSONObject();
            obj.put("messageID", msg.messageID);
            obj.put("messageHash", msg.messageHash != null ? msg.messageHash : "");
            obj.put("recipient", msg.recipientCell);
            obj.put("message", msg.messageContent);
            obj.put("sent", msg.isSent);
            array.put(obj);
        }
        JSONObject root = new JSONObject();
        root.put("totalMessagesSent", totalMessagesSent);
        root.put("messages", array);
        return root.toString(2);
    }

    /** Resets all static state – used between tests. */
    public static void clearHistory() {
        messageHistory.clear();
        totalMessagesSent = 0;
        messageCounter = 0;
    }

    // Getters
    public String getMessageID()      { return messageID; }
    public String getRecipientCell()  { return recipientCell; }
    public String getMessageContent() { return messageContent; }
    public String getMessageHash()    { return messageHash; }
    public int    getNumMessagesSent(){ return numMessagesSent; }
    public boolean isSent()           { return isSent; }

    public static List<Message> getMessageHistory() { return messageHistory; }
}
