package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class SimpleClient extends AbstractClient {

    private static SimpleClient client = null;

    private SimpleClient(String host, int port) {
        super(host, port);
    }


    protected void handleMessageFromServer(Object msg) throws IOException {
        try {
            if (msg.equals("back to list")) {
                Platform.runLater(() -> {
                    try {
                        App.setRoot("checkRequestService");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                return;
            }if (msg.equals("notFound")) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR); // Use ERROR alert type
                    alert.setTitle("Error"); // Set the window's title
                    alert.setHeaderText(null); // Optional: you can have a header or set it to null
                    alert.setContentText("Task not found"); // Set the main message/content
                    alert.showAndWait(); // Display the alert and wait for the user to close it
                    try {
                        App.setRoot("newTaskData");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                return;
            }
            if (msg.equals("notInYourCommunity")) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR); // Use ERROR alert type
                    alert.setTitle("Not in your community"); // Set the window's title
                    alert.setHeaderText(null); // Optional: you can have a header or set it to null
                    alert.setContentText("This task was uploaded by a user who is not part of our community."); // Set the main message/content
                    alert.showAndWait(); // Display the alert and wait for the user to close it

                });
                App.setRoot("newTaskData");
                return;
            }

            if (msg.equals("saved!")) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION); // Use INFORMATION alert type
                    alert.setTitle("Thank you"); // Set the window's title
                    alert.setHeaderText(null); // Optional: you can have a header or set it to null
                    alert.setContentText("The task has been modified."); // Set the main message/content
                    alert.showAndWait(); // Display the alert and wait for the user to close it
                });
                App.setRoot("updateTaskDetails");
                return;
            }
            if (msg.equals("The task's status isn't 2.")) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR); // Use ERROR alert type
                    alert.setTitle("Error"); // Set the window's title
                    alert.setHeaderText(null); // Optional: you can have a header or set it to null
                    alert.setContentText("The task has not been completed yet."); // Set the main message/content
                    alert.showAndWait(); // Display the alert and wait for the user to close it
                });
                App.setRoot("newTaskData");
            } else if (msg.equals("The task is canceled.")) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR); // Use ERROR alert type
                    alert.setTitle("Error"); // Set the window's title
                    alert.setHeaderText(null); // Optional: you can have a header or set it to null
                    alert.setContentText("The task has been canceled."); // Set the main message/content
                    alert.showAndWait(); // Display the alert and wait for the user to close it
                });
            } else if (msg.equals("the status is illegal")) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR); // Use ERROR alert type
                    alert.setTitle("Error"); // Set the window's title
                    alert.setHeaderText(null); // Optional: you can have a header or set it to null
                    alert.setContentText("The status should be between 0 and 5."); // Set the main message/content
                    alert.showAndWait(); // Display the alert and wait for the user to close it
                });
            } else if (msg instanceof Object[]) {
                Object[] messageParts = (Object[]) msg;
                if (messageParts.length == 2 && messageParts[0] instanceof String && messageParts[1] instanceof List) {
                    if (messageParts[0].equals("request")) {
                        CheckRequestService.requests = (List<Task>) messageParts[1];
                        Platform.runLater(() -> {
                            try {
                                App.setRoot("checkRequestService");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    } else if (messageParts[0].equals("alltasks")) {
                        VolunterControl.tasks = (List<Task>) messageParts[1];
                        App.setRoot("volunter_control");
                    } else if (messageParts[0].equals("ToCancel")) {
                        //EventBus.getDefault().post(messageParts[1]);
                        CancelServiceRequest.getRequestService = (List<Task>) messageParts[1];
                        Platform.runLater(() -> {
                            try {
                                App.setRoot("cancelServiceRequest");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    } else if (messageParts[0].equals("uploaded")) {
                        CommunityTaskControl.getCommunityTask = (List<Task>) messageParts[1];
                        Platform.runLater(() -> {
                            try {
                                App.setRoot("CommunityTasks");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    } else if (messageParts[0].equals("canceled!")) {
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION); // Use INFORMATION alert type
                            alert.setTitle("Thank you"); // Set the window's title
                            alert.setHeaderText(null); // Optional: you can have a header or set it to null
                            alert.setContentText("Your request has been canceled."); // Set the main message/content
                            alert.showAndWait(); // Display the alert and wait for the user to close it
                            EventBus.getDefault().post((List<Task>) messageParts[1]);
                        });
                    } else if (messageParts[0].equals("accept")) {
                        Platform.runLater(() -> {
                            EventBus.getDefault().post(messageParts[1]);
                        });
                    } else if (messageParts[0].equals("Messages")) {
                        MessagesToUser.message = (List<MessageToUser>) messageParts[1];
                        // MessagesToUser.users=(List<User>)messageParts[2];
                        Platform.runLater(() -> {
                            try {
                                App.setRoot("MessagesToUser");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    } else if (messageParts[0].equals("all users send")) {
                        MessagesToUser.users = (List<User>) messageParts[1];
                    }
                }
            } else {
                handleMessageFromServer1(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void handleMessageFromServer1(Object msg) {
        try {
            if (msg instanceof String) {
                String message = (String) msg;
                if ("The key id is true".equals(message)) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Use ERROR alert type
                        alert.setTitle("Request has been sent"); // Set the window's title
                        alert.setHeaderText(null); // Optional: you can have a header or set it to null
                        alert.setContentText("Thanks for contacting us. \nThe request has been sent."); // Set the main message/content
                        alert.showAndWait(); // Display the alert and wait for the user to close it
                    });


                    App.setRoot("primary");

                }
                if ("distresscall added successfully to the database.".equals(message)) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Use ERROR alert type
                        alert.setTitle("Request has been sent"); // Set the window's title
                        alert.setHeaderText(null); // Optional: you can have a header or set it to null
                        alert.setContentText("Thanks for contacting us. \nThe request has been sent."); // Set the main message/content
                        alert.showAndWait(); // Display the alert and wait for the user to close it
                    });
                    Platform.runLater(() -> {


                        try {
                            App.setRoot("secondary");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

                }
                if ("The key id is false".equals(message)) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR); // Use ERROR alert type
                        alert.setTitle("Error"); // Set the window's title
                        alert.setHeaderText(null); // Optional: you can have a header or set it to null
                        alert.setContentText("Unidentified Code. Please try again."); // Set the main message/content
                        alert.showAndWait(); // Display the alert and wait for the user to close it
                    });


                }
                // Handling LOGIN_FAIL message
                if ("LOGIN_FAIL".equals(message)) {
                    System.out.println("Login failed. Please try again.");

                    // Ensure this runs on the JavaFX Application Thread
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR); // Use ERROR alert type
                        alert.setTitle("Login Error"); // Set the window's title
                        alert.setHeaderText(null); // Optional: you can have a header or set it to null
                        alert.setContentText("Login failed. Please try again."); // Set the main message/content
                        alert.showAndWait(); // Display the alert and wait for the user to close it
                    });
                } else if ("LOGIN_FAIL2".equals(message)) {
                    System.out.println("Login failed. Please try again.");
                    // Ensure this runs on the JavaFX Application Thread
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR); // Use ERROR alert type
                        alert.setTitle("Login Error"); // Set the window's title
                        alert.setHeaderText(null); // Optional: you can have a header or set it to null
                        alert.setContentText("Error: You are currently logged in from another device. Please log out from that device before attempting to log in here."); // Set the main message/content
                        alert.showAndWait(); // Display the alert and wait for the user to close it
                    });
                    return;
                } else {
                    Platform.runLater(() -> {
                        if ("LOGIN_SUCCESS".equals(message)) {
                            try {
                                App.setRoot("secondary");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }  else if("Manager_LOGIN_SUCCESS".equals(message)){
                            try {
                                App.setRoot("manager_control");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    });
                }
            }
            if (msg.getClass().equals(Warning.class)) {
                EventBus.getDefault().post(new WarningEvent((Warning) msg));
                return;
            } if (msg.getClass().equals(Message.class)) {
                System.out.println("In simple client");
                EventBus.getDefault().post(((Message) msg).getObj());
                return;
            }
            if (msg.getClass().equals(Task.class)) {
                EventBus.getDefault().post(new TaskCancellationEvent((Task) msg));
                return;
            }
            if (msg instanceof List) {
                List<?> list = (List<?>) msg; // Cast to a list of unknown type
                if (!list.isEmpty()) {
                    Object firstElement = list.get(0);
                    if (firstElement instanceof User) {
                        System.out.println("User members received");
                        CommunityMembers.users = (List<User>) msg;
                        Platform.runLater(() -> {

                            try {
                                App.setRoot("communityMembers");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                }
            }
            if (msg instanceof byte[]) {
                byte[] receivedUserBytes = (byte[]) msg;

                try (ByteArrayInputStream bis = new ByteArrayInputStream(receivedUserBytes);
                     ObjectInputStream in = new ObjectInputStream(bis)) {

                    // Deserialize the User object received from the server
                    User user = (User) in.readObject();
                    UserControl.addUser(user);
                    UserControl.addUser(user);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SimpleClient getClient() {
        if (client == null) {
            client = new SimpleClient("localhost", 3000);
        }
        return client;
    }
}