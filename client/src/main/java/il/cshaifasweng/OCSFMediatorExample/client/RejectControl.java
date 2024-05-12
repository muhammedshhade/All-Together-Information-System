package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Task;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class RejectControl {

    @FXML
    private Button Submit;

    @FXML
    private Button back;

    @FXML
    private ChoiceBox<String> services;

    @FXML
    private ImageView im;


   /* private InputStream stream;

    {
        try {
            stream = new FileInputStream("C:\\Users\\IMOE001\\Pictures\\say-no.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    Image myImage1 = new Image(stream);*/
    @FXML
    void initialize() {
       // im.setImage(myImage1);
        // Initialize the ChoiceBox with choices
        services.getItems().add("Beyond Scope of Service or Expertise");
        services.getItems().add("Resource Constraints");
        services.getItems().add("Unrealistic Expectations or Demands");
        services.getItems().add("Violation of Policy or Law");
        services.getItems().add("Potential for Misuse or Harm");
    }

    @FXML
    void back(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                App.setRoot("checkRequestService");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    void submit(ActionEvent event) throws IOException {
        if (services.getValue() == null) {
            showAlert("Error", "Please select one reason.");
            return;
        }
        try {
            showCompletionMessage("Request rejected", "request has been rejected, a message will be sent to the user about it.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        CheckRequestService service = new CheckRequestService();
        Task t = service.getRequestedTask();
        int id = t.getIdNum();
        System.out.println(id);
        String message = "Task is rejected@" + id;
        SimpleClient.getClient().sendToServer(message);

        User loggedInUser = Managercontrol.getManagerLogIn();
        Long manger_id = loggedInUser.getkeyId();
        System.out.println(manger_id);
        String user_id = t.getUser().getID();

        SimpleClient.getClient().sendToServer("The reason of rejected is@" + "Your Request: " + service.getRequestedTask().getServiceType() + "is Rejected by your manager,the reason is: " + services.getValue() + "@" + t.getUser().getCommunity() + "@" + (user_id));
        System.out.println(services.getValue());
    }

    private void showAlert(String title, String message) {
        // Display an alert dialog to the user
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showCompletionMessage(String title, String message) {
        // Display an alert dialog to the user
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Use INFORMATION type for completion message
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}