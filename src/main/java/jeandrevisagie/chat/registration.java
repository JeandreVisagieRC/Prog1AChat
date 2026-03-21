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

        
        System.out.print("Enter Password: "); //captures password
        String password = input.nextLine();
        
        boolean isValid = true;
        if (password.length() < 8) {
            isValid = false; //length check
        } else {
            boolean hasUpper = false; 
            boolean hasDigit = false;
            boolean hasSpecial = false;
            String specialChars = "!@#$%^&*"; //declare special chars

            // check each char to fit requirements
            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                if (Character.isUpperCase(c)) hasUpper = true;
                if (Character.isDigit(c)) hasDigit = true;
                if (specialChars.contains(String.valueOf(c))) hasSpecial = true;
            }

            // all 3 conditions met?
            isValid = hasUpper && hasDigit && hasSpecial;
        }

        // error/success message
        if (isValid) {
            System.out.println("Password successfully captured");
        } else {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least 8 characters, a capital letter, a number and a special character");
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
       
        return pd.length() >= 8; 
    }

   public static boolean checkPhone(String phone) {
   
    return phone.startsWith("+27") && phone.length() == 13;
}
}