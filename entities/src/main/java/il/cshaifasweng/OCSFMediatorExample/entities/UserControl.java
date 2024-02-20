package il.cshaifasweng.OCSFMediatorExample.entities;
import java.util.Base64;

public class UserControl extends User {

    public UserControl(String ID,String first_name,String
            Last_name,boolean isConnected, String commentary,
                       String username, String Password, String address,String email, Role role){
        super( ID,first_name,
                Last_name, isConnected,  commentary,username,  Password,  address,email, role);
    }

    public boolean login(String username, String password) {
        // Implement your login logic here
        // For example, check if the provided username and password match the stored credentials
        if (username.equals(getUsername()) && verifyPassword(password)) {
            return true;
        }
        return false;
    }

    public boolean verifyPassword(String inputPassword) {
        // Implement password verification logic here
        // This should compare the input password with the stored password hash
        String inputPasswordHash = hashPassword(inputPassword, getSalt());
        return inputPasswordHash.equals(getPasswordHash());
    }

    public boolean checkInput() {
        // Implement input validation logic here
        // For example, check if the input is valid based on your criteria
        // Return true if input is valid, otherwise return false
        return true;  // Replace with actual validation logic
    }

    public void ReviewSubmittedRequests() {
        // Implement review submission logic here
        // This method could store the review submission in a database, for instance
    }

    public void CancelRequest(int IdNUM) {
        // Implement cancel request logic here
        // This method could handle the cancellation of a particular request
    }
}
