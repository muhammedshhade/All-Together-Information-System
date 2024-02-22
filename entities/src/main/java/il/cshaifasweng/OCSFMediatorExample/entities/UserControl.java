package il.cshaifasweng.OCSFMediatorExample.entities;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        String hashedPassword = hashPassword(inputPassword, getSalt());
        return hashedPassword.equals(getPasswordHash());
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
    String hashPassword(String password, String salt) {
		try {
			String passwordWithSalt = password + salt;
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] hashedPassword = md.digest(passwordWithSalt.getBytes());
			return bytesToHex(hashedPassword);
		} catch (NoSuchAlgorithmException e) {
			// Handle the exception
			return null;
		}
	}

	private String bytesToHex(byte[] bytes) {
		StringBuilder result = new StringBuilder();
		for (byte b : bytes) {
			result.append(String.format("%02x", b));
		}
		return result.toString();
	}
}
