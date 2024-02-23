package il.cshaifasweng.OCSFMediatorExample.server;

import com.mysql.cj.xdevapi.Client;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import il.cshaifasweng.OCSFMediatorExample.entities.UserControl;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.annotations.Parent;


public class SimpleServer extends AbstractServer {
	public SimpleServer(int port) {
		super(port);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client)  {
		String msgString = msg.toString();
		if (msgString.startsWith("#LogInAttempt")) {
            try {
                handleLoginAttempt(msgString, client);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
		// Add more message handling as needed
	}

	private void handleLoginAttempt(String message, ConnectionToClient client) throws Exception {
		String[] parts = message.split(",");
		if (parts.length == 2) {
			String[] credentials = parts[1].split("@");
			if (credentials.length == 2) {
				String username = credentials[0];
				String password = credentials[1];

				// Perform password validation here by querying the database
				List<UserControl> userControls = new ArrayList<>();
				List<User> allUsers = ConnectToDataBase.getAllUsers();
				for (User user : allUsers) {
					UserControl userControl = new UserControl(user.getID(), user.getFirstName(), user.getLasttName(), user.getisConnected(), user.getCommentary(), user.getUsername(), "0", user.getAddress(), user.getEmail(), user.getRole());
					userControl.setSalt(user.getSalt());
					userControl.setPasswordHash(user.getPasswordHash());
					userControls.add(userControl);
				}

				boolean isValidLogin = false;
				for (UserControl user : userControls) {
					isValidLogin = user.login(username, password);
					if (isValidLogin) {
						// Send a success response back to the client
						try {
							client.sendToClient("LOGIN_SUCCESS");
						} catch (IOException e) {
							e.printStackTrace();
						}
						return; // exit the loop since login is valid
					}
				}

				// If the loop ends and the login is not valid, send a failure response back to the client
				try {
					System.out.println("LOGIN_FAIL");
					client.sendToClient("LOGIN_FAIL");

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
}




//	private boolean validateLoginFromDatabase(String username, String password) {
//		List<User> users = null;
//		try {
//			users = ConnectToDataBase.getAllUsers();
//			// Proceed with handling users
//		} catch (Exception e) {
//			e.printStackTrace(); // or handle the exception appropriately
//		}
//
//		// Check if the username exists in the list of users
//		Optional<User> userOptional = users.stream()
//				.filter(user -> user.getUsername().equals(username))
//				.findFirst();
//
//		if (userOptional.isPresent()) {
//			// User with the provided username found
//			User user = userOptional.get();
//
//			// Hash the provided password with the user's salt
//			String hashedPassword = hashPassword(password, user.getSalt());
//
//			// Compare the hashed passwords
//			return hashedPassword.equals(user.getPasswordHash());
//		}
//
//		return false; // Username not found
//	}
//	String hashPassword(String password, String salt) {
//		try {
//			String passwordWithSalt = password + salt;
//			MessageDigest md = MessageDigest.getInstance("SHA-512");
//			byte[] hashedPassword = md.digest(passwordWithSalt.getBytes());
//			return bytesToHex(hashedPassword);
//		} catch (NoSuchAlgorithmException e) {
//			// Handle the exception
//			return null;
//		}
//	}
//
//	private String bytesToHex(byte[] bytes) {
//		StringBuilder result = new StringBuilder();
//		for (byte b : bytes) {
//			result.append(String.format("%02x", b));
//		}
//		return result.toString();
//	}

