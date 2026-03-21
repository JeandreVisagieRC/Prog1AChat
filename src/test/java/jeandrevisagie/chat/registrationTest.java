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
}