package chatapp.pt1.pkg2;   // change package name if needed

public class Login {

    private final Registration registration;   // we need access to the registered data

    public Login(Registration registration) {
        this.registration = registration;
    }

    // ========== loginUser() ==========
    public boolean loginUser(String username, String password) {
        if (registration.getStoredUsername() == null || registration.getStoredPassword() == null) {
            return false;
        }
        return registration.getStoredUsername().equals(username) 
                && registration.getStoredPassword().equals(password);
    }

    // ========== returnLoginStatus() ==========
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + registration.getFirstName() + " , " + registration.getLastName() + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
