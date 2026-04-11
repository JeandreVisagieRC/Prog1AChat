package jeandrevisagie.chat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;


public class Message {
    
    // variable declaration
    private String messageID;
    private String recipientCell;
    private String messageContent;
    private String messageHash;
    private LocalDateTime timestamp;
    private boolean isSent;
    
    // static variables to track messages 
    private static List<Message> messageHistory = new ArrayList<>();
    private static int totalMessagesSent = 0;
    private static final int MAX_MESSAGE_ID_LENGTH = 10;
    private static final int MAX_PHONE_LENGTH = 10;
    private static final String PHONE_PREFIX = "+27";
    
   
    public Message(String messageID, String recipientCell, String messageContent) {
        this.messageID = messageID;
        this.recipientCell = recipientCell;
        this.messageContent = messageContent;
        this.timestamp = LocalDateTime.now();
        this.isSent = false;
    }
    
    /**
     * Checks that the message ID is no more than 10 characters
     * 
     * @return true if valid, false otherwise
     */
    public boolean checkMessageID() {
        if (messageID == null) {
            return false;
        }
        return messageID.length() <= MAX_MESSAGE_ID_LENGTH;
    }
    
    /**
     * Validates that the recipient's phone number is no more than 10 characters
     * long and begins with '+27'
     * 
     * @return A string message indicating validation result
     */
    public String checkRecipientCell() {
        if (recipientCell == null) {
            return "Error: Field cannot be blank.";
        }
        
        if (!recipientCell.startsWith(PHONE_PREFIX)) {
            return "Error: Phone number must start with '+27'.";
        }
        
        // Remove the '+27' prefix to check the remaining length
        String phoneWithoutPrefix = recipientCell.substring(3);
        if (phoneWithoutPrefix.length() > MAX_PHONE_LENGTH - 3) {
            return "Error: Phone number is too long. Maximum length is " + MAX_PHONE_LENGTH + " characters.";
        }
        
        return "Valid: Phone number " + recipientCell + " is valid.";
    }
    
    /**
     * Creates and returns the message hash in the format:
     * [First 2 chars of ID]:[Message Number]:[First Word]-[Last Word]
     * All in uppercase
     * 
     * @return A formatted message hash
     */
    public String createMessageHash() {
        if (messageID == null || messageID.length() < 2 || messageContent == null) {
            return "Error: Invalid message ID or content.";
        }
        
        // Get first two characters of message ID
        String idPrefix = messageID.substring(0, Math.min(2, messageID.length())).toUpperCase();
        
        // Get message number (position in history + 1, since not added yet)
        int messageNumber = messageHistory.size() + 1;
        
        // Get first and last words
        String[] words = messageContent.trim().split("\\s+");
        if (words.length == 0) {
            return "Error: Message content is empty.";
        }
        
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        
        // Build the hash
        this.messageHash = idPrefix + ":" + messageNumber + ":" + firstWord + "-" + lastWord;
        return this.messageHash;
    }
    
    /**
     * Allows the user to choose to send, store, or disregard the message
     * 
     * @return A string indicating the action taken
     */
    public String sentMessage() {
        Scanner scanner = new Scanner(System.in);
        String choice;
        
        System.out.println("\n--- Message Action Menu ---");
        System.out.println("1. Send message");
        System.out.println("2. Store message");
        System.out.println("3. Disregard message");
        System.out.print("Enter your choice (1-3): ");
        
        choice = scanner.nextLine().trim();
        
        switch(choice) {
            case "1":
                this.isSent = true;
                totalMessagesSent++;
                messageHistory.add(this);
                return "Message sent successfully!";
                
            case "2":
                messageHistory.add(this);
                return "Message stored successfully!";
                
            case "3":
                return "Message disregarded.";
                
            default:
                return "Invalid choice. Please enter 1, 2, or 3.";
        }
    }
    
    /**
     * Returns all the messages sent/stored while the program is running
     * 
     * @return A string representation of all messages
     */
    public String printMessages() {
        if (messageHistory.isEmpty()) {
            return "No messages in history.";
        }
        
        StringBuilder result = new StringBuilder();
        result.append("\n--- Message History ---\n");
        
        for (int i = 0; i < messageHistory.size(); i++) {
            Message msg = messageHistory.get(i);
            result.append(String.format("Message %d:\n", i + 1));
            result.append(String.format("  ID: %s\n", msg.messageID));
            result.append(String.format("  Recipient: %s\n", msg.recipientCell));
            result.append(String.format("  Content: %s\n", msg.messageContent));
            result.append(String.format("  Hash: %s\n", msg.messageHash));
            result.append(String.format("  Timestamp: %s\n", msg.timestamp));
            result.append(String.format("  Sent: %s\n", msg.isSent ? "Yes" : "No"));
            result.append("\n");
        }
        
        return result.toString();
    }
    
    /**
     * Returns the total number of messages sent
     * 
     * @return The total count of sent messages
     */
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
    
    /**
     * Stores all messages in JSON format and returns as a string
     * 
     * @return A JSON string representation of all messages
     */
    public String storeMessagesInJSON() {
        JSONArray jsonArray = new JSONArray();
        
        for (Message msg : messageHistory) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("messageID", msg.messageID);
            jsonObj.put("recipientCell", msg.recipientCell);
            jsonObj.put("messageContent", msg.messageContent);
            jsonObj.put("messageHash", msg.messageHash);
            jsonObj.put("timestamp", msg.timestamp.toString());
            jsonObj.put("isSent", msg.isSent);
            
            jsonArray.put(jsonObj);
        }
        
        JSONObject root = new JSONObject();
        root.put("totalMessagesSent", totalMessagesSent);
        root.put("messages", jsonArray);
        
        return root.toString(2); // Pretty print with 2-space indentation
    }
    
    // Getters and Setters
    public String getMessageID() {
        return messageID;
    }
    
    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }
    
    public String getRecipientCell() {
        return recipientCell;
    }
    
    public void setRecipientCell(String recipientCell) {
        this.recipientCell = recipientCell;
    }
    
    public String getMessageContent() {
        return messageContent;
    }
    
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
    
    public String getMessageHash() {
        return messageHash;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public boolean isSent() {
        return isSent;
    }
    
    public void setSent(boolean sent) {
        isSent = sent;
    }
    
    public static List<Message> getMessageHistory() {
        return messageHistory;
    }
    
    public static void clearMessageHistory() {
        messageHistory.clear();
        totalMessagesSent = 0;
    }
}
