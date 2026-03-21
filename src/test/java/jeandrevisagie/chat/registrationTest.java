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
        assertTrue(registration.checkUserName("K_Vis"));
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        // Too long or no underscore
        assertFalse(registration.checkUserName("J_Visagie"));
        assertFalse(registration.checkUserName("JVis"));
    }

    @Test
    public void testPasswordMeetsComplexity() {
        // 8+ chars, Upper, Digit, Special
        assertTrue(registration.checkPasswordComplexity("P@ssword1"));
    }

    @Test
    public void testPasswordDoesNotMeetComplexity() {
        // Missing special char or too short
        assertFalse(registration.checkPasswordComplexity("password"));
        assertFalse(registration.checkPasswordComplexity("Pass1"));
    }

    @Test
    public void testPhoneCorrectlyFormatted() {
        // Starts +27, length 12 or 13
        assertTrue(registration.checkPhone("+27123456789"));
    }

    @Test
    public void testPhoneIncorrectlyFormatted() {
        // Wrong prefix or wrong length
        assertFalse(registration.checkPhone("0123456789"));
        assertFalse(registration.checkPhone("+27123"));
    }

    @Test
    public void testLoginSuccessful() {
        user testUser = new user("J_Vis", "P@ssword1", "+27123456789");
        
        String loginUser = "J_Vis";
        String loginPass = "P@ssword1";
        
        assertTrue(loginUser.equals(testUser.getUsername()) && loginPass.equals(testUser.getPassword()));
    }

    @Test
    public void testLoginFailed() {
        user testUser = new user("J_Vis", "P@ssword1", "+27123456789");
        
        String loginUser = "WrongUser";
        String loginPass = "WrongPass";
        
        assertFalse(loginUser.equals(testUser.getUsername()) && loginPass.equals(testUser.getPassword()));
    }
}
