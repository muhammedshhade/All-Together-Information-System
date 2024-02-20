/**
 * Sample Skeleton for 'primary.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PrimaryController {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="Canceldistressbt1"
	private Button Canceldistressbt1; // Value injected by FXMLLoader

	@FXML // fx:id="Log_In_button"
	private Button Log_In_button; // Value injected by FXMLLoader

	@FXML // fx:id="Password_button"
	private PasswordField Password_button; // Value injected by FXMLLoader

	@FXML // fx:id="Password_tx"
	private TextField Password_tx; // Value injected by FXMLLoader

	@FXML // fx:id="distressbut"
	private Button distressbut; // Value injected by FXMLLoader

	@FXML // fx:id="user_namd_tx"
	private TextField user_namd_tx; // Value injected by FXMLLoader

	@FXML // fx:id="user_name_button"
	private Button user_name_button; // Value injected by FXMLLoader

	@FXML
	void CancelDistress1(ActionEvent event) {

	}

	@FXML
	void Log_In(ActionEvent event) {
	}

	@FXML
	void Password(ActionEvent event) {

	}

	@FXML
	void User_Name(ActionEvent event) {


	}

	@FXML
	void User_name(ActionEvent event) {

	}

	@FXML
	void distress(ActionEvent event) {

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert Canceldistressbt1 != null : "fx:id=\"Canceldistressbt1\" was not injected: check your FXML file 'primary.fxml'.";
		assert Log_In_button != null : "fx:id=\"Log_In_button\" was not injected: check your FXML file 'primary.fxml'.";
		assert Password_button != null : "fx:id=\"Password_button\" was not injected: check your FXML file 'primary.fxml'.";
		assert Password_tx != null : "fx:id=\"Password_tx\" was not injected: check your FXML file 'primary.fxml'.";
		assert distressbut != null : "fx:id=\"distressbut\" was not injected: check your FXML file 'primary.fxml'.";
		assert user_namd_tx != null : "fx:id=\"user_namd_tx\" was not injected: check your FXML file 'primary.fxml'.";
		assert user_name_button != null : "fx:id=\"user_name_button\" was not injected: check your FXML file 'primary.fxml'.";

	}

}
