/**
 * Sample Skeleton for 'manager_control.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.IOException;

public class Managercontrol {

    @FXML // fx:id="distress"
    private Button distress; // Value injected by FXMLLoader

    @FXML // fx:id="doneTasks"
    private Button doneTasks; // Value injected by FXMLLoader

    @FXML // fx:id="members"
    private Button members; // Value injected by FXMLLoader

    @FXML // fx:id="requests"
    private Button requests; // Value injected by FXMLLoader

    @FXML // fx:id="serviceRequest"
    private Button serviceRequest; // Value injected by FXMLLoader

    @FXML // fx:id="updateTask"
    private Button updateTask; // Value injected by FXMLLoader

    @FXML // fx:id="volunteer"
    private Button volunteer; // Value injected by FXMLLoader

    @FXML
    void communityRequests(ActionEvent event) {
        try {
            SimpleClient.getClient().sendToServer("Get uploaded tasks by community members");
        } catch (IOException e) {
            showAlert("Error", "Failed to get uploaded community tasks: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void getCommunityMembers(ActionEvent event) throws IOException {
        try {
            SimpleClient.getClient().sendToServer("Get community members");
        } catch (IOException e) {
            showAlert("Error", "Failed to get community members: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void getDistressCalls(ActionEvent event) {

    }

    @FXML
    void getDoneTasks(ActionEvent event) {
    }

    @FXML
    void showServiceRequests(ActionEvent event) {
        //to check the requests.
        try {
            SimpleClient.getClient().sendToServer("check requests");
        } catch (IOException e) {
            showAlert("Error", "Failed to get community help requests: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void showVolunteerRequests(ActionEvent event) {
        //manager check
    }

    @FXML
    void updateTaskDetails(ActionEvent event) throws IOException {
        App.setRoot("updateTaskDetails");
    }
    @FXML
    void logOut(ActionEvent event) throws IOException {
        SimpleClient.getClient().sendToServer("log out");
        App.setRoot("primary");

    }

}
