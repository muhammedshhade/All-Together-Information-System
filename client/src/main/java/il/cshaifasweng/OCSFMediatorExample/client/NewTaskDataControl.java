/**
 * Sample Skeleton for 'newTaskData.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewTaskDataControl {

    @FXML // fx:id="back"
    private Button back; // Value injected by FXMLLoader

    @FXML // fx:id="newData"
    private TextField newData; // Value injected by FXMLLoader

    @FXML // fx:id="save"
    private Button save; // Value injected by FXMLLoader

    @FXML // fx:id="taskId"
    private TextField taskId; // Value injected by FXMLLoader
    @FXML
    private TextField title;

   /* public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize method called when the controller is loaded
        title.setEditable(false);


    }*/
    public void initialize() {
        title.setText("Please enter the value of "+ UpdateTaskDetails.getUpdateVale()+".");
    }
    @FXML
    void homePage(ActionEvent event) throws IOException {
        App.setRoot("updateTaskDetails");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void saveData(ActionEvent event) {

        // Check if the new data and task ID fields are empty
        if (newData.getText().isEmpty() || taskId.getText().isEmpty()) {
            showAlert("Error", "Please fill in all required fields.");
            return;
        }
       // if(UpdateTaskDetails.updateVale.equals("status"))

        // Check if the new data contains only digits and/or semicolons
        if (!newData.getText().matches("[0-9.]+")) {
            showAlert("Error", "New data should contain only numbers and/or semicolons.");
            return;
        }
        try {
            // Construct the message to send to the server
            String message = "update data@" + taskId.getText() + "@" + newData.getText();
            // Send the message to the server
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            // Show an error alert if failed to update task status
            showAlert("Error", "Failed to update task status: " + e.getMessage());
            e.printStackTrace();
        }
        try {
            App.setRoot("updateTaskDetails");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void showCompletionMessage(String title, String message) {
        // Display an alert dialog to the user
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Use INFORMATION type for completion message
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


