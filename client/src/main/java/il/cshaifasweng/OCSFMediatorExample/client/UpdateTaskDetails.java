

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UpdateTaskDetails {

    @FXML // fx:id="back"
    private Button back; // Value injected by FXMLLoader

    @FXML // fx:id="status"
    private Button status; // Value injected by FXMLLoader
    @FXML
    private TextArea text;
    @FXML // fx:id="time"
    private Button time; // Value injected by FXMLLoader

    @FXML
    private ImageView im;
    private static String updateVale;
    InputStream stream;

    {
        try {
            stream = new FileInputStream("C:\\Users\\IMOE001\\Pictures\\update.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    Image myImage = new Image(stream);
    public static String getUpdateVale() {
        return updateVale;
    }
    public void initialize() {
        text.setEditable(false);
        im.setImage(myImage);

    }

    public static void setUpdateVale(String updateVale) {
        UpdateTaskDetails.updateVale = updateVale;
    }

    @FXML
    void UpdateExecutionTime(ActionEvent event) {
        try {
            setUpdateVale("execution time");
            Platform.runLater(() -> {
                try {

                    App.setRoot("newTaskData");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            SimpleClient.getClient().sendToServer(updateVale);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void homePage(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                App.setRoot("manager_control");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    void updateStatus(ActionEvent event) {
        try {
            setUpdateVale("status");
            SimpleClient.getClient().sendToServer(updateVale);
            Platform.runLater(() -> {
                try {

                    App.setRoot("newTaskData");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
