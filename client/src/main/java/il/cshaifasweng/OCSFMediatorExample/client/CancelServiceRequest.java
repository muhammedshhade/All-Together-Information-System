/**
 * Sample Skeleton for 'cancelServiceRequest.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CancelServiceRequest {

    @FXML // fx:id="back"
    private Button back; // Value injected by FXMLLoader

    @FXML // fx:id="cancel"
    private Button cancel; // Value injected by FXMLLoader

    @FXML // fx:id="details"
    private Button details; // Value injected by FXMLLoader

    @FXML // fx:id="note"
    private TextArea note; // Value injected by FXMLLoader

    @FXML // fx:id="requestsList"
    private ListView<String> requestsList; // Value injected by FXMLLoader
    public static List<Task> getRequestService = new ArrayList<>();
    private Task requestedTask = null;

  /*  public void initialize() {
        note.setEditable(false);
        if (getRequestService.isEmpty()) {
            // If getCommunityTask list is empty, do nothing
            return;
        }
        // Add items to the ListView
        for (Task task : getRequestService) {
            this.requestsList.getItems().add(task.getServiceType());
        }
        // Set event handler for mouse click on ListView
        this.requestsList.setOnMouseClicked(event -> {
            String selectedTaskName = this.requestsList.getSelectionModel().getSelectedItem();
            if (selectedTaskName != null) {
                // Find the selected task in the getCommunityTask list
                for (Task task : getRequestService) {
                    if (task.getServiceType().equals(selectedTaskName)) {
                        requestedTask = task;
                        break;
                    }
                }
            }
        });
    }*/

    public void initialize() {
        // im.setImage(myImage1);
        note.setEditable(false);
        if (getRequestService.isEmpty()) {
            // If requests list is empty, do nothing
            return;
        }
        // Add items to the ListView
        for (Task task : getRequestService) {
            this.requestsList.getItems().add("Task id:" + task.getIdNum() + " - " + task.getServiceType());
        }
        // Set event handler for mouse click on ListView
        this.requestsList.setOnMouseClicked(event -> {

            String selectedTaskName = this.requestsList.getSelectionModel().getSelectedItem();
            if (selectedTaskName != null) {
                // Find the selected task in the getCommunityTask list
                for (Task task : getRequestService) {
                    String[] parts1 = selectedTaskName.split("-");
                    String[] parts2 = parts1[0].split(":");
                    // Trim the string before parsing it as an integer
                    int number = Integer.parseInt(parts2[1].trim());
                    if (task.getIdNum() == number) {
                        requestedTask = task;
                        break;
                    }
                }
            }
        });
    }

    @FXML
    void CancelRequest(ActionEvent event) throws IOException {
        if (requestedTask != null) {
            int id = requestedTask.getIdNum();
            System.out.println(id);
            String message = "cancel request@" + id + "@" + "0";
            SimpleClient.getClient().sendToServer(message);
        }
        try {
            Object[] array = new Object[2];
            array[0] = "Cancel request"; // Assign a String object to the first index
            array[1] = SecondaryController.getUserLogIn();
            SimpleClient.getClient().sendToServer(array);
        } catch (IOException e) {
            showAlert("Error", "Failed to get your service requests: " + e.getMessage());
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
    void back(ActionEvent event) throws IOException {
        App.setRoot("secondary");
    }

    private void showAlert(String task) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task details");
        alert.setHeaderText("Task Details: ");
        alert.setContentText(task);
        alert.showAndWait();
    }

    @FXML
    void taskDetails(ActionEvent event) {
        if (requestedTask != null) {
            LocalTime time = requestedTask.getTime();
            LocalDate date = requestedTask.getDate();
            String serviceType = requestedTask.getServiceType();
            String note = requestedTask.getNote();
            String formattedDetails = String.format("Date: %s\nTime: %s\nTask Description: %s\nNote: %s",
                    date, time, serviceType, note);
            showAlert(formattedDetails);
        }
    }

}
