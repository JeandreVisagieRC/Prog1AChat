package jeandrevisagie.chat;

import java.util.Scanner;

public class registration {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean isUserValid = false;
        boolean isPassValid = false;
        boolean isPhoneValid = false;
        
        System.out.print("Enter Username: ");
        String username = input.nextLine();
        
        isUserValid = checkUserName(username);
        if (isUserValid) {
            System.out.println("Username successfully captured");
        } else {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length");
        }

        System.out.print("Enter Password: ");
        String password = input.nextLine();
        
        isPassValid = checkPasswordComplexity(password);

        if (isPassValid) {
            System.out.println("Password successfully captured");
        } else {
            System.out.println("Password is not correctly formatted.");
        }

        System.out.print("Enter Phone Number: ");
        String phone = input.nextLine();
        
        isPhoneValid = checkPhone(phone);
        if (isPhoneValid) {
            System.out.println("Phone number successfully added");
        } else {
            System.out.println("Phone number is incorrectly formatted or does not contain international code");
        }

        if (isUserValid && isPassValid && isPhoneValid) {
            user newUser = new user(username, password, phone);
            System.out.println("Registration successful!");
            System.out.println("User object created for: " + newUser.getUsername());
    
            System.out.println("\n--- LOGIN TO YOUR ACCOUNT ---");
            System.out.print("Enter Username: ");
            String loginUsername = input.nextLine();
            
            System.out.print("Enter Password: ");
            String loginPassword = input.nextLine();
            
            if (loginUsername.equals(newUser.getUsername()) && loginPassword.equals(newUser.getPassword())) {
                System.out.println("Welcome back, " + newUser.getUsername() + "! Login Successful.");
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }
        } else {
            System.out.println("\n--- REGISTRATION FAILED ---");
            if (!isUserValid) System.out.println("-> Username is invalid.");
            if (!isPassValid) System.out.println("-> Password is invalid.");
            if (!isPhoneValid) System.out.println("-> Phone number is invalid.");
        }
    }

    public static boolean checkUserName(String name) {
        return name.contains("_") && name.length() <= 5;
    }

    public static boolean checkPasswordComplexity(String pd) {
        if (pd == null || pd.length() < 8) {
            return false; 
        }

        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        String specialChars = "!@#$%^&*";

        for (int i = 0; i < pd.length(); i++) {
            char c = pd.charAt(i);
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isDigit(c)) hasDigit = true;
            if (specialChars.contains(String.valueOf(c))) {
                hasSpecial = true;
            }
        }

        return hasUpper && hasDigit && hasSpecial; 
    }

    public static boolean checkPhone(String phone) {
        return phone.startsWith("+27") && (phone.length() == 13 || phone.length() == 12);
    }
}