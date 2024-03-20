/**
 * Sample Skeleton for 'updateTaskDetails.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import org.hibernate.query.criteria.internal.path.MapKeyHelpers;

import java.io.IOException;

public class UpdateTaskDetails {

    @FXML // fx:id="back"
    private Button back; // Value injected by FXMLLoader

    @FXML // fx:id="status"
    private Button status; // Value injected by FXMLLoader

    @FXML // fx:id="time"
    private Button time; // Value injected by FXMLLoader
    public static String updateVale;

    @FXML
    void UpdateExecutionTime(ActionEvent event) {
        try {
            updateVale = "execution time";
            App.setRoot("newTaskData");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void homePage(ActionEvent event) throws IOException {
        App.setRoot("manager_control");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void updateStatus(ActionEvent event) {
        try {
            updateVale = "status";
            App.setRoot("newTaskData");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
