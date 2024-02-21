
package il.cshaifasweng.OCSFMediatorExample.client;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PrimaryController {

	@FXML
	private Button Canceldistressbt1;

	@FXML
	private Label LabelUser;

	@FXML
	private Button Log_In_button;

	@FXML
	private PasswordField Password_button;

	@FXML
	private Button distressbut;

	@FXML
	private Label passwordlabel;

	@FXML
	private TextField txt_usrn;
	@FXML
	void sendWarning(ActionEvent event) {
		try {
			SimpleClient.getClient().sendToServer("#warning");
		} catch (IOException e) {
			showAlert("Error", "Failed to send warning: " + e.getMessage());
		}
	}
	@FXML
	void User_Name(ActionEvent event) {

	}

	private void showAlert(String title, String message) {
		// Display an alert dialog to the user
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}


	@FXML
	void ClientProfileLoad(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("clientHomePage.fxml"));
			AnchorPane newScene = loader.load();

			Stage currentStage = App.getStage();
			Scene scene = new Scene(newScene);
			currentStage.setTitle("Client Home Page");
			currentStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@FXML
	void CancelDistress1(ActionEvent event) {

	}

	@FXML
	void Log_In(ActionEvent event) {
		String username = txt_usrn.getText();
		String password = Password_button.getText();

		// Check username and password locally
			// Username and password are valid, sending a message to the server for further validation
			try {
				String messageToSend = "#LogInAttempt," + username + "@" + password;
				// Send message to the server for additional validation
				SimpleClient.getClient().sendToServer(messageToSend);
				// You might want to add further logic here based on the server response
			} catch (IOException e) {
				showAlert("Error", "Failed to Get Login message!" + e.getMessage());
				e.printStackTrace();
			}
	}
	@FXML
	void Password(ActionEvent event) {

	}


	@FXML
	void distress(ActionEvent event) {

	}
}