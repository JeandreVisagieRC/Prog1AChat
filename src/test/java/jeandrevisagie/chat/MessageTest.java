package jeandrevisagie.chat;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessageTest {

    // message length test

    @BeforeEach
public void reset() throws Exception {//resets all static fields in the Message class to their initial values before each test to ensure test independence
    resetField("numMessagesSent", 0);
    resetField("totalSent", 0);
    resetField("sentMessages", new ArrayList<>());
    resetField("storedMessages", new ArrayList<>());
    resetField("disregardedMessages", new ArrayList<>());
    resetField("messageHashes", new ArrayList<>());
    resetField("messageIDs", new ArrayList<>());
}

private void resetField(String fieldName, Object value) throws Exception {
    Field field = Message.class.getDeclaredField(fieldName);
    field.setAccessible(true);
    field.set(null, value);
}

    @Test
    public void testMessageLengthSuccess() {
        String msg = "Hi Mike, can you join us for dinner tonight?";
        assertTrue(msg.length() <= 250);
    }

    @Test
    public void testMessageLengthFailure() {
        String msg = "A".repeat(260);
        int excess = msg.length() - 250;
        assertTrue(msg.length() > 250);
        assertEquals(10, excess);
    }

    // recipient cell number test

    @Test
    public void testRecipientCellSuccess() {
        Message m = new Message("+27718693002",
            "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Cell phone number successfully captured.",
            m.checkRecipientCell());
    }

    @Test
    public void testRecipientCellFailure() {
        Message m = new Message("08575975889",
            "Hi Keegan, did you receive the payment?");
        assertEquals(
            "Cell phone number is incorrectly formatted or does not " +
            "contain an International code. Please correct the " +
            "number and try again.",
            m.checkRecipientCell());
    }

    // hash test

    @Test
    public void testMessageHashCorrect() {
        Message m = new Message("+27718693002",
            "Hi Mike, can you join us for dinner tonight?");
        assertTrue(m.getMessageHash().endsWith(":HITONIGHT"),
            "Expected hash ending :HITONIGHT but got: " + m.getMessageHash());
    }

    @Test
    public void testMessageHashLoop() {
        String[] messages = {
            "Hi Mike, can you join us for dinner tonight?",
            "Hi Keegan, did you receive the payment?"
        };
        String[] expectedEndings = { ":HITONIGHT", ":HIPAYMENT" };

        for (int i = 0; i < messages.length; i++) {
            Message m = new Message("+27718693002", messages[i]);
            assertTrue(m.getMessageHash().endsWith(expectedEndings[i]),
                "Hash mismatch for message " + (i + 1));
        }
    }

    // message ID test

    @Test
    public void testMessageIDCreated() {
        Message m = new Message("+27718693002",
            "Hi Mike, can you join us for dinner tonight?");
        assertNotNull(m.getMessageID());
        assertEquals(10, m.getMessageID().length());
    }

    // sent message test

     @Test
public void testSentMessageSend() {
        System.setIn(new java.io.ByteArrayInputStream("1\n".getBytes()));
        Message m = new Message("+27718693002",
            "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message successfully sent.", m.SentMessage());
    }

    @Test
    public void testSentMessageDisregard() {
        System.setIn(new java.io.ByteArrayInputStream("2\n".getBytes()));
        Message m = new Message("+27718693002",
            "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Press 0 to delete the message.", m.SentMessage());
    }

    @Test
    public void testSentMessageStore() {
        System.setIn(new java.io.ByteArrayInputStream("3\n".getBytes()));
        Message m = new Message("+27718693002",
            "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message successfully stored.", m.SentMessage());
    }
    @Test
public void testSentMessagesArrayPopulated() {
    System.setIn(new java.io.ByteArrayInputStream("1\n".getBytes()));
    new Message("+27834557896", "Did you get the cake?").SentMessage();

    System.setIn(new java.io.ByteArrayInputStream("1\n".getBytes()));
    new Message("0838884567", "It is dinner time!").SentMessage();

    String result = Message.printMessages();
    assertTrue(result.contains("Did you get the cake?"));
    assertTrue(result.contains("It is dinner time!"));
}

@Test
public void testLongestMessage() {
    System.setIn(new java.io.ByteArrayInputStream("1\n".getBytes()));
    new Message("+27834557896", "Did you get the cake?").SentMessage();

    System.setIn(new java.io.ByteArrayInputStream("1\n".getBytes()));
    new Message("+27838884567",
        "Where are you? You are late! I have asked you to be on time.").SentMessage();

    assertEquals(
        "Where are you? You are late! I have asked you to be on time.",
        Message.getLongestMessage());
}

@Test
public void testSearchByMessageID() {
    System.setIn(new java.io.ByteArrayInputStream("1\n".getBytes()));
    Message m = new Message("0838884567", "It is dinner time!");
    m.SentMessage();
    assertTrue(Message.searchByMessageID(m.getMessageID()).contains("It is dinner time!"));
}

@Test
public void testSearchByRecipient() {
    System.setIn(new java.io.ByteArrayInputStream("3\n".getBytes()));
    new Message("+27838884567",
        "Where are you? You are late! I have asked you to be on time.").SentMessage();

    System.setIn(new java.io.ByteArrayInputStream("3\n".getBytes()));
    new Message("+27838884567", "Ok, I am leaving without you.").SentMessage();

    String result = Message.searchByRecipient("+27838884567");
    assertTrue(result.contains(
        "Where are you? You are late! I have asked you to be on time."));
    assertTrue(result.contains("Ok, I am leaving without you."));
}

@Test
public void testDeleteByHash() {
    System.setIn(new java.io.ByteArrayInputStream("3\n".getBytes()));
    Message m = new Message("+27838884567",
        "Where are you? You are late! I have asked you to be on time.");
    m.SentMessage();
    assertTrue(Message.deleteByHash(m.getMessageHash()).contains("successfully deleted"));
}
}