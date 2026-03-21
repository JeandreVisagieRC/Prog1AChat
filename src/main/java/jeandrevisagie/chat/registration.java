
package jeandrevisagie.chat;
import java.util.Scanner;

/**
 *
 * @author jeand
 */
public class registration { //taking in relevant user data
    
    public static void main (String[]args){
        Scanner sc = new Scanner(System.in);
        
        System.out.println ("Enter Username:");
        String user = sc.nextLine ();
        
        System.out.println ("Enter Password:");
        String pass = sc.nextLine();
        
        System.out.println("Enter SA Phone Number (+27...):");
        String phone = sc.nextLine ();
       
        //checking rules for registration
        if (isUsernameValid(user) && isPasswordValid (pass)&& isPhoneValid (phone)) {
            System.out.println ("Registration successful!");
        } else{
            System.out.println ("Registration failed. Please check the requirements.");
        } sc.close();
    }
    
    public static boolean isUsernameValid (String name){//checking username is less than 5 chars and contains underscore
        if (name.length() > 5) return false;
        if (!name.contains ("_")) return false;
        return true;
    }
    public static boolean isPasswordValid(String pass){
        if (pass.length() < 8) return false;
        
        boolean hasUpper = false; //variables will become true when conditions are met
        boolean hasDigit = false;
        boolean hasSpecial = false;
        String specialChars = "!@#$%^&*"; //defining 'special characters'
        
        for (int i = 0; i < pass.length(); i++){
            char c = pass.charAt (i);
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isDigit(c)) hasDigit = true;
            if (specialChars.contains (String.valueOf(c))) hasSpecial = true; // reading through each digit in password and checking uppercase, digit, special chars
        }
        return hasUpper && hasDigit && hasSpecial;
    }
    public static boolean isPhoneValid (String phone) {
        if (!phone.startsWith("+27")) return false; //checking for SA dialling code
        
        String numbersOnly = phone.substring(3);//removes +27 digits from the check
        if (numbersOnly.length() > 10 || numbersOnly.isEmpty()) return false;
        
        for (int i = 0; i < numbersOnly.length(); i++) { //cycling through numbers - same process as with password
            if (!Character.isDigit(numbersOnly.charAt(i))) return false;       
    }
      return true;
     
    }} 
   
