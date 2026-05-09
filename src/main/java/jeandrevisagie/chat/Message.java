package jeandrevisagie.chat;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Message {//creates and stores messages, generates message ID and hash, and tracks the number of messages sent

    private String messageID;
    private String recipient;
    private String message;
    private String messageHash;

    private static int numMessagesSent = 0;
    private static int totalSent = 0;
    private static ArrayList<Message> sentMessages = new ArrayList<>();
    private static ArrayList<Message> storedMessages = new ArrayList<>();

    public Message(String recipient, String message) {
        this.recipient = recipient;
        this.message = message;
        this.messageID = generateMessageID();
        numMessagesSent++;
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random random = new Random();
        long id = (long)(random.nextDouble() * 9000000000L) + 1000000000L;
        return String.valueOf(id);
    }
// Getters for message properties
    public String getMessageID()            
    { return messageID; }
    public String getMessageHash()          
    { return messageHash; }
    public String getRecipient()            
    { return recipient; }
    public String getMessage()              
    { return message; }
    public static int getNumMessagesSent()  
    { return numMessagesSent; }

public boolean checkMessageID() {//checks that the message ID is no more than 10 characters in length
    return messageID.length() <= 10;
}

public String checkRecipientCell() {//checks that the recipient's cell phone number is correctly formatted and contains an international code
    if (recipient.startsWith("+") && 
        recipient.length() >= 10 && 
        recipient.length() <= 13) {
        return "Cell phone number successfully captured.";
    } else {
        return "Cell phone number is incorrectly formatted or does not " +
               "contain an International code. Please correct the " + 
               "number and try again.";
    }
}
public String createMessageHash() {//creates a message hash using the first two characters of the message ID, the number of messages sent, and the first and last words of the message
    String idPart = messageID.substring(0, 2);
    int messageNumber = numMessagesSent - 1;

    String[] words = message.trim().split("\\s+");
    String firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
    String lastWord  = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");

    String hash = idPart + ":" + messageNumber + ":" + firstWord + lastWord;
    return hash.toUpperCase();
}
public String SentMessage() {//provides options for the user to send, disregard, or store the message
    Scanner scanner = new Scanner(System.in);

    System.out.println("\n1) Send Message");
    System.out.println("2) Disregard Message");
    System.out.println("3) Store Message to send later");
    System.out.print("Enter your choice: ");

    int choice = Integer.parseInt(scanner.nextLine().trim());

    switch (choice) {//switch statement to execute the user's choice
        case 1:
            totalSent++;
            sentMessages.add(this);
            return "Message successfully sent.";
        case 2:
            return "Press 0 to delete the message.";
        case 3:
            storeMessage();
            return "Message successfully stored.";
        default:
            return "Invalid option selected.";
    }
}
public static String printMessages() {//prints all sent messages with their details. if no messages have been sent, it indicates that as well
    if (sentMessages.isEmpty()) {
        return "No messages sent.";
    }

    StringBuilder sb = new StringBuilder();
    sb.append("\n--- Sent Messages ---\n");

    for (Message m : sentMessages) {
        sb.append("Message ID:   ").append(m.messageID).append("\n");
        sb.append("Message Hash: ").append(m.messageHash).append("\n");
        sb.append("Recipient:    ").append(m.recipient).append("\n");
        sb.append("Message:      ").append(m.message).append("\n");
        sb.append("---------------------\n");
    }

    return sb.toString();
}

public static int returnTotalMessages() {
    return totalSent;
}
public void storeMessage() {//stores the message in a JSON file
    String filePath = "storedMessages.json";
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonArray jsonArray = new JsonArray();

    // If file already exists, load what's already in it
    File file = new File(filePath);
    if (file.exists()) {
        try (Reader reader = new FileReader(file)) {
            JsonElement element = JsonParser.parseReader(reader);
            if (element.isJsonArray()) {
                jsonArray = element.getAsJsonArray();
            }
        } catch (IOException e) {
            System.out.println("Could not read existing stored messages.");
        }
    }

    // builds out a JSON object for the current message
    JsonObject jsonMessage = new JsonObject();
    jsonMessage.addProperty("messageID", messageID);
    jsonMessage.addProperty("messageHash", messageHash);
    jsonMessage.addProperty("recipient", recipient);
    jsonMessage.addProperty("message", message);

    // writes object to file
    jsonArray.add(jsonMessage);
    try (Writer writer = new FileWriter(filePath)) {
        gson.toJson(jsonArray, writer);
        System.out.println("Message saved to " + filePath);
    } catch (IOException e) {
        System.out.println("Could not save message to file.");
    }
}}