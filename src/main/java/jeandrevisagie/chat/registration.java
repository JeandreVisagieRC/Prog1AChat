package jeandrevisagie.chat;

import java.util.Scanner;

public class registration {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        
        System.out.print("Enter Username: "); //captures username
        String username = input.nextLine();
        
        if (checkUserName(username)) {
            System.out.println("Username successfully captured");
        } else {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length");
        }

        
       System.out.print("Enter Password: ");
        String password = input.nextLine();
            boolean isValid = checkPasswordComplexity(password); //calling password checker


if (isValid) {
    System.out.println("Password successfully captured");
} else {
    System.out.println("Password is not correctly formatted.");
}

      
    System.out.print("Enter Phone Number: "); //captures phone number
    String phone = input.nextLine();
        
        if (checkPhone(phone)) {
            System.out.println("Phone number successfully added");
        } else {
            System.out.println("Phone number is incorrectly formatted or does not contain international code");
        }

        input.close();
    }

   

    public static boolean checkUserName(String name) {
        return name.contains("_") && name.length() <= 5;
    }

    public static boolean checkPasswordComplexity(String pd) {
    // check length of password
    if (pd == null || pd.length() < 8) {
        return false; 
    }

    // assume all requirements are wrong
    boolean hasUpper = false;
    boolean hasDigit = false;
    boolean hasSpecial = false;
    String specialChars = "!@#$%^&*";

    // check each character and flag requirements as they are met
    for (int i = 0; i < pd.length(); i++) {
        char c = pd.charAt(i);
        if (Character.isUpperCase(c)) hasUpper = true;
        if (Character.isDigit(c)) hasDigit = true;
        if (specialChars.contains(String.valueOf(c))) {
            hasSpecial = true;
        }
    }

    // all 3 reqs met?
    return hasUpper && hasDigit && hasSpecial; 
}

   public static boolean checkPhone(String phone) {
   
    return phone.startsWith("+27") && phone.length() == 13 || phone.length() == 12;//this should cover the edge case of user keeping the 0 in their number w/the dialing code
}
}