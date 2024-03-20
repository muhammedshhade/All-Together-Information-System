/**
 * Sample Skeleton for 'updateTaskDetails.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class UpdateTaskDetails {

    @FXML // fx:id="back"
    private Button back; // Value injected by FXMLLoader

    @FXML // fx:id="status"
    private Button status; // Value injected by FXMLLoader

    @FXML // fx:id="time"
    private Button time; // Value injected by FXMLLoader

    @FXML
    void UpdateExecutionTime(ActionEvent event) {

    }

    @FXML
    void homePage(ActionEvent event) throws IOException {
        App.setRoot("manager_control");
    }

    @FXML
    void updateStatus(ActionEvent event) {

    }
}
