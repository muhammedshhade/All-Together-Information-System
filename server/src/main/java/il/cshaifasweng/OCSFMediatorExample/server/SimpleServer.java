package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.User;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;


public class SimpleServer extends AbstractServer {
	public SimpleServer(int port) {
		super(port);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String msgString = msg.toString();
		if (msgString.startsWith("#LogInAttempt")) {
			handleLoginAttempt(msgString, client);
		}
		// Add more message handling as needed
	}

	private void handleLoginAttempt(String message, ConnectionToClient client) {
		String[] parts = message.split(",");
		if (parts.length == 2) {
			String[] credentials = parts[1].split("@");
			if (credentials.length == 2) {
				String username = credentials[0];
				String password = credentials[1];
				// Perform password validation here by querying the database
				boolean isValidLogin = validateLoginFromDatabase(username, password);
				if (isValidLogin) {
					// Send a success response back to the client
					try {
						client.sendToClient("LOGIN_SUCCESS");
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					// Send a failure response back to the client
					try {
						client.sendToClient("LOGIN_FAIL");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private boolean validateLoginFromDatabase(String username, String password) {
		List<User> users = null;
		try {
			users = ConnectToDataBase.getAllUsers();
			// Proceed with handling users
		} catch (Exception e) {
			e.printStackTrace(); // or handle the exception appropriately
		}

		// Check if the username exists in the list of users
		Optional<User> userOptional = users.stream()
				.filter(user -> user.getUsername().equals(username))
				.findFirst();

		if (userOptional.isPresent()) {
			// User with the provided username found
			User user = userOptional.get();

			// Hash the provided password with the user's salt
			String hashedPassword = hashPassword(password, user.getSalt());

			// Compare the hashed passwords
			return hashedPassword.equals(user.getPasswordHash());
		}

		return false; // Username not found
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
