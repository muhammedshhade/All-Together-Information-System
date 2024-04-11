/**
 * Sample Skeleton for 'updateTaskDetails.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import org.hibernate.query.criteria.internal.path.MapKeyHelpers;
import javafx.scene.control.TextArea;
import java.io.IOException;

public class UpdateTaskDetails {

    @FXML // fx:id="back"
    private Button back; // Value injected by FXMLLoader

    @FXML // fx:id="status"
    private Button status; // Value injected by FXMLLoader
    @FXML
    private TextArea text;
    @FXML // fx:id="time"
    private Button time; // Value injected by FXMLLoader
    private static String updateVale;

    public static String getUpdateVale() {
        return updateVale;
    }
    public void initialize() {
        text.setEditable(false);
    }

    public static void setUpdateVale(String updateVale) {
        UpdateTaskDetails.updateVale = updateVale;
    }

    @FXML
    void UpdateExecutionTime(ActionEvent event) {
        try {
            setUpdateVale("execution time");
            App.setRoot("newTaskData");
            SimpleClient.getClient().sendToServer(updateVale);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void homePage(ActionEvent event) throws IOException {
        App.setRoot("manager_control");
    }

    @FXML
    void updateStatus(ActionEvent event) {
        try {
            System.out.println("Hii  ");
            setUpdateVale("status");
            SimpleClient.getClient().sendToServer(updateVale);
            App.setRoot("newTaskData");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
