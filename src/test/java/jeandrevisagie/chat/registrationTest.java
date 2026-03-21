package jeandrevisagie.chat;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class registrationTest {

    @Test
    public void testUsernameFormatting() {
        
        assertEquals(true, registration.checkUserName("kyl_1")); 
        
       
        assertEquals(false, registration.checkUserName("kyle!!!!!!!")); 
    }

    @Test
    public void testPasswordComplexity() {
        
        assertEquals(true, registration.checkPasswordComplexity("Ch&&sec@ke99!"));
        
       
        assertEquals(false, registration.checkPasswordComplexity("password"));
    }

    @Test
    public void testPhoneFormatting() {
       
        assertEquals(true, registration.checkPhone("+27838968976"));
        
        
        assertEquals(false, registration.checkPhone("08966553"));
    }
    
    @Test
    public void testUsernameCorrectlyFormatted() {
        // Must contain "_" and be <= 5 chars
        assertTrue(registration.checkUserName("kyl_1"));
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        // Too long or no underscore
        assertFalse(registration.checkUserName("kyle!!!!!!!"));
        assertFalse(registration.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testPasswordMeetsComplexity() {
        // 8+ chars, Upper, Digit, Special
        assertTrue(registration.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testPasswordDoesNotMeetComplexity() {
        // Missing special char or too short
        assertFalse(registration.checkPasswordComplexity("password"));
        assertFalse(registration.checkPasswordComplexity("password"));
    }

    @Test
    public void testPhoneCorrectlyFormatted() {
        // Starts +27, length 12 or 13
        assertTrue(registration.checkPhone("+27838968976"));
    }

    @Test
    public void testPhoneIncorrectlyFormatted() {
        // Wrong prefix or wrong length
        assertFalse(registration.checkPhone("08966553"));
        assertFalse(registration.checkPhone("+27123"));
    }

    @Test
    public void testLoginSuccessful() {
        user testUser = new user("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        
        String loginUser = "kyl_1";
        String loginPass = "Ch&&sec@ke99!";
        
        assertTrue(loginUser.equals(testUser.getUsername()) && loginPass.equals(testUser.getPassword()));
    }

    @Test
    public void testLoginFailed() {
        user testUser = new user("kyle!!!!!!!", "password", "08966553");
        
        String loginUser = "WrongUser";
        String loginPass = "WrongPass";
        
        assertFalse(loginUser.equals(testUser.getUsername()) && loginPass.equals(testUser.getPassword()));
    }
}
