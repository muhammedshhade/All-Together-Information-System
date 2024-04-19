package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.User;
import il.cshaifasweng.OCSFMediatorExample.entities.UserControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SecondaryController implements Initializable {


    @FXML
    private Button CANCEL_VOLUNTERINGbt;

    @FXML
    private Button Canceldistressbt;

    @FXML
    private Button DistressButtonControl;

    @FXML
    private Button LOGOUTBUTTON;

    @FXML
    private Button Requesthelpbutton;

    @FXML
    private Button VOLUNTERBUTTON;

    @FXML
    private Button cansel_REQUESTBT;

    @FXML
    private Button message;
    @FXML
    private ImageView im;
    @FXML
    private TextField userId;

    @FXML
    private TextField userName;

    private static User userLogIn;
    InputStream stream1;

    {
        try {
            stream1 = new FileInputStream("C:\\Users\\IMOE001\\Pictures\\siren.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    InputStream stream;

    {
        try {
            stream = new FileInputStream("C:\\Users\\IMOE001\\Pictures\\introduction.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    Image myImage = new Image(stream);

    Image myImage1 = new Image(stream1);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageView imageView = new ImageView(myImage1);
        double desiredWidth = 50;
        double desiredHeight = 50;
        imageView.setFitWidth(desiredWidth);
        imageView.setFitHeight(desiredHeight);
        DistressButtonControl.setGraphic(imageView);
        im.setImage(myImage);
        ArrayList<User> loggedInList = UserControl.getLoggedInList();
        if (!loggedInList.isEmpty()) {
            User lastUser = loggedInList.get(loggedInList.size() - 1);
            userName.setText(lastUser.getFirstName() + " " + lastUser.getLastName());
            userId.setText(lastUser.getID());
            userLogIn = lastUser;
        }
        userId.setEditable(false);
        userName.setEditable(false);
    }

    public static User getUserLogIn() {
        return userLogIn;
    }

    @FXML
    void message(ActionEvent event) throws IOException {
        try {
            Object[] array = new Object[2];
            array[0] = "Get uploaded messages"; // Assign a String object to the first index
            array[1] = SecondaryController.getUserLogIn();
            SimpleClient.getClient().sendToServer(array);

        } catch (IOException e) {
            showAlert("Error", "Failed to get uploaded community tasks: " + e.getMessage());
            e.printStackTrace();
        }
        try {
            SimpleClient.getClient().sendToServer("Get all users");
        } catch (IOException e) {
            showAlert("Error", "Failed to get uploaded community tasks: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    void CANCELREQUST(ActionEvent event) {
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

    @FXML
    void CancelDistress(ActionEvent event) {

    }

    @FXML
    void Cancelvoluntering(ActionEvent event) {

    }

    @FXML
    void LOGOUT(ActionEvent event) throws IOException {
        SimpleClient.getClient().sendToServer("log out " + getUserLogIn().getID());
        App.setRoot("primary");
    }

    @FXML
    void Requesthelp(ActionEvent event) throws IOException {
        App.setRoot("request_control");
        SimpleClient.getClient().sendToServer("Create task");
    }

    @FXML
    void distress(ActionEvent event) {
    }

    @FXML
    void volunter(ActionEvent event) throws IOException {
        // Retrieving the username and password
//        Preferences prefs = Preferences.userNodeForPackage(SecondaryController.class);
//        String savedUsername = prefs.get("username", "");
//        String savedPassword = prefs.get("password", "");
//        System.out.println(savedPassword);
//        System.out.println(savedUsername);
        try {
            SimpleClient.getClient().sendToServer("get tasks");
        } catch (IOException e) {
            showAlert("Error", "Failed to Get Login message!" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        // Display an alert dialog to the user
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
