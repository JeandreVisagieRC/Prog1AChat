package jeandrevisagie.chat;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class registrationTest {

    @Test
    public void testUsernameFormatting() {
        // Test correct: underscore and <= 5 chars
        assertEquals(true, registration.checkUserName("Ky_1")); 
        
        // Test incorrect: no underscore
        assertEquals(false, registration.checkUserName("Kylie")); 
    }

    @Test
    public void testPasswordComplexity() {
        // Test correct: (Assuming your logic for 8+ chars, cap, etc.)
        assertEquals(true, registration.checkPasswordComplexity("Ch@tt3r!"));
        
        // Test incorrect: too short
        assertEquals(false, registration.checkPasswordComplexity("password"));
    }

    @Test
    public void testPhoneFormatting() {
        // Test correct: starts with +27
        assertEquals(true, registration.checkPhone("+2712345678"));
        
        // Test incorrect: wrong prefix
        assertEquals(false, registration.checkPhone("0721234567"));
    }
}