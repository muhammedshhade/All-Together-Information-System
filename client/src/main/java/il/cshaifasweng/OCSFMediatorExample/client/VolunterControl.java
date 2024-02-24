package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class VolunterControl {

    @FXML
    private Button PICK_TASK_BTN;

    @FXML
    private Button Task_Details_btn;

    @FXML
    private ListView<String> TasksList;

    @FXML
    private Button homepagebtn;
    public static List<Task> tasks =new ArrayList<>();
    private Task selectedTask = null;
    public void initialize(){
        while (tasks.isEmpty()){
            try {
                Thread.currentThread().sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for(Task task : tasks){
            this.TasksList.getItems().addAll(task.getServiceType());
        }
        this.TasksList.setOnMouseClicked(event -> {
            String selectedTaskName = this.TasksList.getSelectionModel().getSelectedItem();
            if(selectedTaskName!=null){
                for(Task task : tasks){
                    if(task.getServiceType().equals(selectedTaskName)){
                        selectedTask = task;
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

    @FXML
    void PICK_TASK(ActionEvent event) {

    }

    @FXML
    void TASKDETAILS(ActionEvent event) {

    }

    @FXML
    void homepage(ActionEvent event) {

    }

}
