/**
 * Sample Skeleton for 'communityMembers.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommunityMembers {

    @FXML // fx:id="back"
    private Button back; // Value injected by FXMLLoader

    @FXML // fx:id="member"
    private Button member; // Value injected by FXMLLoader

    @FXML // fx:id="memberList"
    private ListView<String> memberList; // Value injected by FXMLLoader

    public static List<User> users = new ArrayList<>();
    private User selectedUser = null;

  public void initialize() {
        System.out.println("communityMember");
        while (users.isEmpty()) {
            try {
                Thread.currentThread().sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for (User user : users) {
            this.memberList.getItems().addAll(user.getFirstName() + " " + user.getLastName());
        }
        this.memberList.setOnMouseClicked(event -> {
            String selectedTaskName = this.memberList.getSelectionModel().getSelectedItem();
            if (selectedTaskName != null) {
                for (User user : users) {
                    String name = user.getFirstName() + " " + user.getLastName();
                    if (name.equals(selectedTaskName)) {
                        selectedUser = user;
                        break;
                    }
                }
            }
        });
    }

    @FXML
    void memberDetails(ActionEvent event) throws IOException {
      if (selectedUser != null) {
            String id = selectedUser.getID();
            String name = selectedUser.getFirstName() + " " + selectedUser.getLastName();
            String community = selectedUser.getCommunity();
            String address = selectedUser.getAddress();
            String email = selectedUser.getEmail();
            String x = String.format("User ID: %s\nUser Name: %s\nCommunity: %s\nAddress: %s\nEmail: %s", id, name, community, address, email);
            showAlert(x);
        }
    }


    @FXML
    void previous(ActionEvent event) throws IOException {
        App.setRoot("manager_control");
    }

    private void showAlert(String task) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task details");
        alert.setHeaderText("Task Details: ");
        alert.setContentText(task);
        alert.showAndWait();
    }

}