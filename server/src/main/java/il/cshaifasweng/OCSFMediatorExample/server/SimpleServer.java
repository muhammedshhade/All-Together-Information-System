package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.client.UpdateTaskDetails;
import il.cshaifasweng.OCSFMediatorExample.entities.MessageToUser;
import il.cshaifasweng.OCSFMediatorExample.entities.Task;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import il.cshaifasweng.OCSFMediatorExample.entities.UserControl;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.*;



public class SimpleServer extends AbstractServer {
    private long key;

    public SimpleServer(int port) {
        super(port);
    }

    @Override
    protected void serverClosed() {
        try {
            ConnectToDataBase.updateIsConnect(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static void modifyTask(int TaskID) {
        try {
            List<Task> tasks = ConnectToDataBase.getAllTasks();
            for (Task task : tasks) {
                if (task.getIdNum() == TaskID)
                    task.setStatus(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override

    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        try {
            if (msg instanceof Object[]) {
                Object[] messageParts = (Object[]) msg;
                if (messageParts.length == 2 && messageParts[0] instanceof String && messageParts[0].equals("add task to database.") && messageParts[1] instanceof Task) {
                    if (messageParts[0].equals("add task to database."))
                        ((Task) messageParts[1]).setUser(UserControl.getLoggedInUser());
                    ConnectToDataBase.addTask((Task) messageParts[1]);
                }
                return;
            }
            String message = (String) msg;
            if (message.equals("get tasks")) {
                List<Task> alltasks = ConnectToDataBase.getAllTasks();
                Object[] array = new Object[2];
                array[0] = "alltasks"; // Assign a String object to the first index
                array[1] = alltasks;
                client.sendToClient(array);
            } else if (message.equals("Get community members")) {
                List<User> members = ConnectToDataBase.getCommunityMembers(UserControl.getLoggedInUser().getCommunityManager());
                client.sendToClient(members);
            } else if (message.equals("Get uploaded tasks by community members")) {
                List<Task> requests = ConnectToDataBase.getTasksUploadedByCommunityMembers(UserControl.getLoggedInUser().getCommunityManager());
                Object[] array = new Object[2];
                array[0] = "uploaded"; // Assign a String object to the first index
                array[1] = requests;
                client.sendToClient(array);
            } else if (message.equals("Cancel request")) {
                List<Task> requests = ConnectToDataBase.getTasksWithStatusAndUser(UserControl.getLoggedInUser().getID());
                Object[] array = new Object[2];
                array[0] = "ToCancel"; // Assign a String object to the first index
                array[1] = requests;
                client.sendToClient(array);
            } else if (message.equals("check requests")) {
                List<Task> requests = ConnectToDataBase.getTasksWithStatus(UserControl.getLoggedInUser().getCommunityManager(), 3);
                Object[] array = new Object[2];
                array[0] = "request"; // Assign a String object to the first index
                array[1] = requests;
                client.sendToClient(array);
            } else if (message.startsWith("modify")) {
                String taskid = message.split(" ")[1];
                modifyTask(Integer.parseInt(taskid));
                client.sendToClient(ConnectToDataBase.getAllTasks());
            } else if (message.startsWith("update data")) {
                System.out.println("server " + UpdateTaskDetails.getUpdateVale());
                String[] parts = message.split("@");
                if (parts.length >= 4 && parts[0].equals("update data")) {
                    String taskId = parts[1];
                    String newData = parts[2];
                    String updateVale = parts[3];
                    try {
                        int taskIdInt = Integer.parseInt(taskId);
                        Task task = ConnectToDataBase.getTaskById(taskIdInt);
                        if (task != null) {
                            if (!task.getUser().getCommunity().equals(UserControl.getLoggedInUser().getCommunityManager())) {
                                client.sendToClient("notInYourCommunity");
                            } else if (task.getStatus() == 5) {
                                client.sendToClient("The task is canceled.");
                            } else if (task.getStatus() != 2 && !updateVale.equals("status")) {
                                client.sendToClient("The task's status isn't 2.");
                            } else if (task.getUser().getCommunity().equals(UserControl.getLoggedInUser().getCommunityManager())) {
                                if ((Integer.parseInt(newData) < 0 || Integer.parseInt(newData) > 5) && updateVale.equals("status")) {
                                    client.sendToClient("the status is illegal");
                                    return;
                                }
                                ConnectToDataBase.updateTaskData(newData, task, updateVale);
                                client.sendToClient("saved!");
                            }
                        } else {
                            client.sendToClient("notFound");
                            System.out.println("Task with ID " + taskId + " not found.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid task ID: " + taskId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (message.startsWith("log out")) {
                try {
                    ConnectToDataBase.updateIsConnect(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (message.startsWith("Task is Accept")) {
                String[] parts = message.split("@");
                if (parts.length >= 3 && parts[0].equals("Task is Accept")) {
                    String taskId = parts[1];
                    String newData = parts[2];
                    try {
                        int taskIdInt = Integer.parseInt(taskId);
                        Task task = ConnectToDataBase.getTaskById(taskIdInt);
                        if (task != null) {
                            ConnectToDataBase.updateTaskData(newData, task, "status");
                        } else {
                            System.out.println("Task with ID " + taskId + " not found.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid task ID: " + taskId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (message.startsWith("Cancel request")) {
                String[] parts = message.split("@");
                if (parts.length >= 3 && parts[0].equals("Cancel request")) {
                    String taskId = parts[1];
                    try {
                        int taskIdInt = Integer.parseInt(taskId);
                        Task task = ConnectToDataBase.getTaskById(taskIdInt);
                        if (task != null) {
                            ConnectToDataBase.updateTaskData("5", task, "status");
                            client.sendToClient("canceld!");

                        } else {
                            System.out.println("Task with ID " + taskId + " not found.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid task ID: " + taskId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (message.startsWith("The reason of rejected is")) {
                String[] parts = message.split("@");
                String reason = parts[1];
                System.out.println(reason);
                String newData = parts[2];
                System.out.println(newData);
                String news = parts[3];
                List<User> allUsers = ConnectToDataBase.getAllUsers();
                User manger;
                MessageToUser Message = new MessageToUser();
                Message.setContent(reason);
                Long recipientId = Long.valueOf(news);
                Message.setRecipient(recipientId);
                for (User user : allUsers) {
                    System.out.println("user.getCommunityManager(): " + user.getCommunityManager());
                    System.out.println("newData: " + newData);
                    if (user.getCommunityManager().equals(newData)) {
                        Message.setSender(user.getkeyId());
                    }
                }
                Message.setSentTime(LocalDateTime.now());
                ConnectToDataBase.Add_message(Message);
            } else if (message.startsWith("Task is rejected")) {
                String[] parts = message.split("@");
                if (parts.length >= 3 && parts[0].equals("Task is rejected")) {
                    String taskId = parts[1];
                    System.out.println(taskId + "*");
                    String newData = parts[2];
                    System.out.println(newData + "*");
                    try {
                        int taskIdInt = Integer.parseInt(taskId);
                        Task task = ConnectToDataBase.getTaskById(taskIdInt);
                        if (task != null) {
                            ConnectToDataBase.updateTaskData(newData, task, "status");
                        } else {
                            System.out.println("Task with ID " + taskId + " not found.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid task ID: " + taskId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            } else if (message.equals("Get uploaded messages")) {
                Long id = 0L;
                List<User> allUsers = ConnectToDataBase.getAllUsers();
                for (User user : allUsers) {
                    if (user.getID().equals(UserControl.getLoggedInUser().getID())) {
                        id = user.getkeyId();
                    }
                }
                List<MessageToUser> requests = ConnectToDataBase.getMessagesBySender(id);
                Object[] array = new Object[2];
                array[0] = "Messages"; // Assign a String object to the first index
                array[1] = requests;
                client.sendToClient(array);
            } else if (message.equals("Get all users")) {
                List<User> allUsers = ConnectToDataBase.getAllUsers();
                Object[] array = new Object[2];
                array[0] = "all users send"; // Assign a String object to the first index
                array[1] = allUsers;
                client.sendToClient(array);

            } else if (message.startsWith("#LogInAttempt")) {
                try {
                    handleLoginAttempt(message, client);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleLoginAttempt(String message, ConnectionToClient client) throws Exception {
        String[] parts = message.split(",");
        if (parts.length == 2) {
            String[] credentials = parts[1].split("@");
            if (credentials.length == 2) {
                String username = credentials[0];
                String password = credentials[1];
                // Perform password validation here by querying the database
                List<UserControl> userControls = new ArrayList<>();
                List<User> allUsers = ConnectToDataBase.getAllUsers();
                for (User user : allUsers) {
                    UserControl userControl = new UserControl(user.getID(), user.getFirstName(), user.getLastName(), user.getisConnected(), user.getCommunity(), user.getUsername(), user.getCommunityManager(), "0", user.getAddress(), user.getEmail(), user.getRole());
                    userControl.setSalt(user.getSalt());
                    userControl.setPasswordHash(user.getPasswordHash());
                    userControls.add(userControl);
                }
                boolean isValidLogin = false;
                for (UserControl user : userControls) {
                    isValidLogin = user.login(username, password);

                    if (isValidLogin) {
                        UserControl.setLoggedInUser(user);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        try (ObjectOutputStream out = new ObjectOutputStream(bos)) {
                            out.writeObject(user);  // assuming 'user' is your User object
                            out.flush();
                        } catch (IOException e) {
                            // Handle serialization exception
                            e.printStackTrace();
                        }
                        if (UserControl.getLoggedInUser().getisConnected() == true) {
                            client.sendToClient("LOGIN_FAIL2");
                            return;
                        }
                        byte[] userBytes = bos.toByteArray();
                        client.sendToClient(userBytes);
                        // Send a success response back to the client
                        try {
                            if (username.startsWith("*")) {
                                client.sendToClient("Manager_LOGIN_SUCCESS");
                                ConnectToDataBase.updateIsConnect(true);
                            } else {
                                client.sendToClient("LOGIN_SUCCESS");
                                ConnectToDataBase.updateIsConnect(true);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return; // exit the loop since login is valid
                    }
                }
                // If the loop ends and the login is not valid, send a failure response back to the client
                try {
                    System.out.println("LOGIN_FAIL");
                    client.sendToClient("LOGIN_FAIL");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
