package jeandrevisagie.chat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    // message length test

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
}