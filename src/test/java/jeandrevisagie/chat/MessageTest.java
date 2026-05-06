package jeandrevisagie.chat;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessageTest {

    // ── test data ────────────────────────────────────────────────────────────
    private static final String ID1      = "0012345678";
    private static final String ID2      = "0198765432";
    private static final String PHONE1   = "+27718693002";
    private static final String PHONE2   = "08575975889";
    private static final String CONTENT1 = "Hi Mike, can you join us for dinner tonight?";
    private static final String CONTENT2 = "Hi Keegan, did you receive the payment?";

    @BeforeEach
    void setUp() {
        Message.clearHistory();
    }

    // ── 1. Message length ────────────────────────────────────────────────────

    @Test
    void testMessageLengthSuccess() {
        assertEquals("Message ready to send.", Message.checkMessageLength(CONTENT1));
    }

    @Test
    void testMessageLengthFailure() {
        String longMsg = "A".repeat(260);
        int excess = 260 - 250;
        assertEquals(
            "Message exceeds 250 characters by " + excess + "; please reduce the size.",
            Message.checkMessageLength(longMsg)
        );
    }

    // ── 2. Recipient cell number ─────────────────────────────────────────────

    @Test
    void testRecipientSuccess() {
        Message msg = new Message(ID1, PHONE1, CONTENT1);
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    void testRecipientFailure() {
        Message msg = new Message(ID2, PHONE2, CONTENT2);
        assertEquals(
            "Cell phone number is incorrectly formatted or does not contain an International code. Please correct the number and try again.",
            msg.checkRecipientCell()
        );
    }

    // ── 3. Message hash ──────────────────────────────────────────────────────

    @Test
    void testMessageHashCase1() {
        Message msg = new Message(ID1, PHONE1, CONTENT1);
        assertEquals("00:0:HITONIGHT", msg.createMessageHash());
    }

    @Test
    void testAllMessageHashesInLoop() {
        // Both messages created in sequence so numMessagesSent is 0, then 1
        String[][] data = {
            {ID1, PHONE1, CONTENT1, "00:0:HITONIGHT"},
            {ID2, PHONE2, CONTENT2, "01:1:HIPAYMENT"}
        };

        for (String[] row : data) {
            Message msg = new Message(row[0], row[1], row[2]);
            assertEquals(row[3], msg.createMessageHash(),
                "Hash mismatch for message with ID " + row[0]);
        }
    }

    // ── 4. Message ID ────────────────────────────────────────────────────────

    @Test
    void testMessageIDGenerated() {
        Message msg = new Message(PHONE1, CONTENT1);
        assertTrue(
            msg.displayMessageID().startsWith("Message ID generated: "),
            "Expected output to start with 'Message ID generated: '"
        );
        assertTrue(msg.checkMessageID(), "Message ID should be valid (≤10 chars)");
    }

    // ── 5. sentMessage return values ─────────────────────────────────────────

    @Test
    void testSentMessageSend() {
        Message msg = new Message(ID1, PHONE1, CONTENT1);
        msg.createMessageHash();
        assertEquals("Message successfully sent.", msg.sentMessage("Send Message"));
    }

    @Test
    void testSentMessageDisregard() {
        Message msg = new Message(ID1, PHONE1, CONTENT1);
        assertEquals("Press 0 to delete the message.", msg.sentMessage("Disregard Message"));
    }

    @Test
    void testSentMessageStore() {
        Message msg = new Message(ID1, PHONE1, CONTENT1);
        assertEquals("Message successfully stored.", msg.sentMessage("Store Message"));
    }
}
