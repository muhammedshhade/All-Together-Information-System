package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Task;
import javafx.scene.control.Alert;
import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;

import java.io.IOException;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class SimpleClient extends AbstractClient {
	
	private static SimpleClient client = null;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) throws IOException {
		if (msg instanceof String) {
			String message = (String) msg;

			// Handling LOGIN_FAIL message
			if ("LOGIN_FAIL".equals(message)) {
				System.out.println("Login failed. Please try again.");

				// Ensure this runs on the JavaFX Application Thread
				Platform.runLater(() -> {
					Alert alert = new Alert(Alert.AlertType.ERROR); // Use ERROR alert type
					alert.setTitle("Login Error"); // Set the window's title
					alert.setHeaderText(null); // Optional: you can have a header or set it to null
					alert.setContentText("Login failed. Please try again."); // Set the main message/content
					alert.showAndWait(); // Display the alert and wait for the user to close it
				});
			}



			else{
				App.setRoot("secondary");
			}


		}
		if(msg.getClass().equals(Warning.class))
		{
			EventBus.getDefault().post(new WarningEvent((Warning)msg));
		}
		if (msg instanceof List)
		{
			System.out.println("x");
			VolunterControl.tasks = (List<Task>) msg;
			System.out.println("x");
			App.setRoot("volunter_control");
		}
	}
	
	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}



}
