/**
 * Sample Skeleton for 'CommunityTasks.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ListView;

//class for the community submitted requests.
public class CommunityTaskControl {
    @FXML
    private TextField title;

    @FXML
    private Button submitter;

    @FXML // fx:id="back"
    private Button back; // Value injected by FXMLLoader

    @FXML // fx:id="communityTasks"
    private ListView<String> communityTasks; // Value injected by FXMLLoader

    @FXML // fx:id="tasks"
    private Button tasks; // Value injected by FXMLLoader

    public static List<Task> getCommunityTask =new ArrayList<>();
    private Task requestedTask = null;
  /* public void initialize(){
        while (getCommunityTask.isEmpty()){
            try {
                Thread.currentThread().sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for(Task task : getCommunityTask){
            this.communityTasks.getItems().addAll(task.getServiceType());
        }
        this.communityTasks.setOnMouseClicked(event -> {
            String selectedTaskName = this.communityTasks.getSelectionModel().getSelectedItem();
            if(selectedTaskName!=null){
                for(Task task : getCommunityTask){
                    if(task.getServiceType().equals(selectedTaskName)){
                        requestedTask = task;
                        break;
                    }
                }
            }
        });
    }*/
  public void initialize() {
      if (getCommunityTask.isEmpty()) {
          // If getCommunityTask list is empty, do nothing
          return;
      }
      // Add items to the ListView
      for (Task task : getCommunityTask) {
          this.communityTasks.getItems().add(task.getServiceType());
      }
      // Set event handler for mouse click on ListView
      this.communityTasks.setOnMouseClicked(event -> {
          String selectedTaskName = this.communityTasks.getSelectionModel().getSelectedItem();
          if (selectedTaskName != null) {
              // Find the selected task in the getCommunityTask list
              for (Task task : getCommunityTask) {
                  if (task.getServiceType().equals(selectedTaskName)) {
                      requestedTask = task;
                      break;
                  }
              }
          }
      });
  }


    private void showAlert(String task){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task details");
        alert.setHeaderText("Task Details: " );
        alert.setContentText(task);
        alert.showAndWait();
    }
    private void showAlert2(String task) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User details");
        alert.setHeaderText("User Details: ");
        alert.setContentText(task);
        alert.showAndWait();
    }

    @FXML
    void submitterDetails(ActionEvent event) {
        if (requestedTask != null) {
            String id = requestedTask.getUser().getID();
            String name = requestedTask.getUser().getFirstName() + " " + requestedTask.getUser().getLastName();
            String community = requestedTask.getUser().getCommunity();
            String address = requestedTask.getUser().getAddress();
            String email = requestedTask.getUser().getEmail();
            String x = String.format("User ID: %s\nUser Name: %s\nCommunity: %s\nAddress: %s\nEmail: %s", id, name, community, address, email);
            showAlert2(x);
        }
    }

    @FXML
    void previous(ActionEvent event) throws IOException {
        App.setRoot("manager_control");
    }

    @FXML
    void tasksDetails(ActionEvent event) {
        if(requestedTask != null){
            int id= requestedTask.getIdNum();
            String serviceType= requestedTask.getServiceType();
            String fitst=requestedTask.getUser().getFirstName();
            String userid=requestedTask.getUser().getID();
            int status=requestedTask.getStatus();
            String x = String.format("Task ID: %d\nTask Description: %s\nUser Name: %s\nUser ID: %s\nState: %d", id, serviceType, fitst, userid, status);
            showAlert(x);
        }
    }
}
