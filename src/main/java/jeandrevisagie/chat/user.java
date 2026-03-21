

package jeandrevisagie.chat;

/**
 *
 * @author jeand
 */
public class user {
    private String username;
    private String password;
    private String phoneNumber;
           
public user (String username, String password, String phoneNumber){
    this.username = username;
    this.password = password;
    this.phoneNumber = phoneNumber;
}

public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getPhoneNumber() { return phoneNumber; }
}

