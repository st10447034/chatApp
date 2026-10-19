package chatapp.pt1.pkg2;   // change package name if needed

import java.util.regex.Pattern;

public class Registration {

    private String storedUsername;
    private String storedPassword;
    private String storedCellNumber;
    private String firstName;
    private String lastName;

    // ========== checkUserName() ==========
    public boolean checkUserName(String username) {
        return username != null 
                && username.contains("_") 
                && username.length() <= 5;
    }

    // ========== checkPasswordComplexity() ==========
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }
        return hasCapital && hasNumber && hasSpecial;
    }

    // ========== checkCellPhoneNumber() ==========
    // Regular expression created with the assistance of ChatGPT (OpenAI)
    // Reference: OpenAI. (2023). ChatGPT [Large language model]. https://chat.openai.com
    public boolean checkCellPhoneNumber(String cellNumber) {
        if (cellNumber == null) {
            return false;
        }
        // Must start with +27 followed by 9 or 10 digits
        String regex = "^\\+27\\d{9,10}$";
        return Pattern.matches(regex, cellNumber);
    }

    // ========== registerUser() ==========
    public String registerUser(String username, String password, String cellNumber) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(cellNumber)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        // Store the details
        this.storedUsername = username;
        this.storedPassword = password;
        this.storedCellNumber = cellNumber;

        return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.";
    }

    // Helper methods
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStoredUsername() {
        return storedUsername;
    }

    public String getStoredPassword() {
        return storedPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}