package jeandrevisagie.chat;

public class registration {

    public static boolean checkUserName(String name) { //username checker
        return name.contains("_") && name.length() <= 5;
    }

    public static boolean checkPasswordComplexity(String pd) { //password checker
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

    public static boolean checkPhone(String phone) { //phone no. checker
        return phone.startsWith("+27") && (phone.length() == 13 || phone.length() == 12); // this should cover edge case of user including the 0 when entering phone number
    }
}