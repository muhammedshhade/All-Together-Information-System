package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Task;


import il.cshaifasweng.OCSFMediatorExample.entities.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;

import java.io.IOException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private SimpleClient client;
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {

        if (stage != null && stage.isShowing()) {
            // Close the existing window
            stage.close();
        }
        primaryStage = stage;
        EventBus.getDefault().register(this);
        client = SimpleClient.getClient();
        client.openConnection();
        scene = new Scene(loadFXML("primary"), 600, 600);
        stage.setScene(scene);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    User user;
                    if (SecondaryController.getUserLogIn() == null)
                        user = Managercontrol.getManagerLogIn();
                    else user = SecondaryController.getUserLogIn();

                    if (user != null)
                        SimpleClient.getClient().sendToServer("log out " + user.getID());
                } catch (IOException e) {
                    primaryStage.close();
                    throw new RuntimeException(e);
                }
                stage.close();
            }
        });
        stage.show();
    }

    public static void displayTaskDetails(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("displayTaskDetails.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            il.cshaifasweng.OCSFMediatorExample.client.displayTaskController controller = loader.getController();

            // Call the initData method to pass the task
            controller.initData(task);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Task Details");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        primaryStage.setTitle(fxml);

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
    public void stop() throws Exception {
        // TODO Auto-generated method stub
        EventBus.getDefault().unregister(this);
        super.stop();
    }


//    @Override
//    public void stop() throws Exception {
//        EventBus.getDefault().unregister(this);
//        try {
//            User user;
//            if (SecondaryController.getUserLogIn() == null)
//                user = Managercontrol.getManagerLogIn();
//            else user = SecondaryController.getUserLogIn();
//            if (user != null)
//                SimpleClient.getClient().sendToServer("log out " + user.getID());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        super.stop();
//        Platform.exit();
//        System.exit(0);
//    }

    @Subscribe
    public void onWarningEvent(WarningEvent event) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.WARNING,
                    String.format("Message: %s\nTimestamp: %s\n",
                            event.getWarning().getMessage(),
                            event.getWarning().getTime().toString())
            );
            alert.show();
        });
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION,
                    String.format("%s\n%s\n",
                            event.getMessage().getMsg(), event.getMessage().getTime().toString()));
            alert.show();
        });
    }


    @Subscribe
    public void onCancel(TaskCancellationEvent event) {
        Platform.runLater(() -> {
            try {
                if (getStage() != null && getStage().getTitle().equals("checkRequestService")) {
                    if (Managercontrol.getManagerLogIn() != null &&
                            Managercontrol.getManagerLogIn().getCommunityManager().equals(event.getCanceledTask().getUser().getCommunity())) {
                        client.sendToServer("check requests@" + Managercontrol.getManagerLogIn().getCommunityManager());
                        System.out.println("in app");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Subscribe // when the manager reject request, the user can't cancel it.
    public void onReject(Task event) {
        Platform.runLater(() -> {
            try {
                if (getStage() != null && getStage().getTitle().equals("cancelServiceRequest")) {

                    if (SecondaryController.getUserLogIn() != null &&
                            SecondaryController.getUserLogIn().getID().equals(event.getUser().getID())) {
                        Object[] array = new Object[2];
                        array[0] = "Cancel request"; // Assign a String object to the first index
                        array[1] = SecondaryController.getUserLogIn();
                        SimpleClient.getClient().sendToServer(array);
                        App.setRoot("cancelServiceRequest");
                        System.out.println("in reject");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Scene getScene() {
        return scene;
    }

    public static Stage getStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }

}