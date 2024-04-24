package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.client.NewTaskDataControl;
import il.cshaifasweng.OCSFMediatorExample.client.TaskCancellationEvent;
import il.cshaifasweng.OCSFMediatorExample.client.UpdateTaskDetails;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
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
                if (messageParts.length == 2 && messageParts[0] instanceof String && messageParts[0].equals("add task to database.")
                        && messageParts[1] instanceof Task) {
                    ConnectToDataBase.addTask((Task) messageParts[1]);
                    sendToAllClients((Task) messageParts[1]);// to update the requests that the client can cancel.
                } else if (messageParts.length == 2 && messageParts[0] instanceof String &&
                        messageParts[0].equals("Cancel request") && messageParts[1] instanceof User) {
                    List<Task> requests = ConnectToDataBase.getTasksWithStatusAndUser(((User) messageParts[1]).getID());
                    Object[] array = new Object[2];
                    array[0] = "ToCancel"; // Assign a String object to the first index
                    array[1] = requests;
                    client.sendToClient(array);
                } else if (messageParts.length == 2 && messageParts[0] instanceof String &&
                        messageParts[0].equals("Get uploaded messages") && messageParts[1] instanceof User) {
                    List<MessageToUser> requests = ConnectToDataBase.getMessagesBySender(((User) messageParts[1]).getID());
                    Object[] array = new Object[2];
                    array[0] = "Messages"; // Assign a String object to the first index
                    array[1] = requests;
                    client.sendToClient(array);
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
            } else if (message.startsWith("Get community members")) {
                String[] parts = message.split("@");
                if (parts.length == 2 && parts[1] != null) {
                    String communityManager = parts[1];
                    // Get the community members based on the community manager's ID
                    List<User> members = ConnectToDataBase.getCommunityMembers(communityManager);
                    // Send the community members list to the client
                    client.sendToClient(members);
                    System.out.println("hi");
                }
            } else if (message.startsWith("Get uploaded tasks by community members")) {
                String[] parts = message.split("@");
                if (parts.length == 2 && parts[1] != null) {
                    String communityManager = parts[1];
                    // Get the community members based on the community manager's ID
                    List<Task> requests = ConnectToDataBase.getTasksUploadedByCommunityMembers(communityManager);
                    Object[] array = new Object[2];
                    array[0] = "uploaded"; // Assign a String object to the first index
                    array[1] = requests;
                    client.sendToClient(array);
                    // Send the community members list to the client
                }
            } else if (message.startsWith("check requests@")) {
                String[] parts = message.split("@");
                if (parts.length == 2 && parts[1] != null) {
                    String communityManager = parts[1];
                    // Get the community members based on the community manager's ID
                    List<Task> requests = ConnectToDataBase.getTasksWithStatus(communityManager, 3);
                    Object[] array = new Object[2];
                    array[0] = "request"; // Assign a String object to the first index
                    array[1] = requests;
                    client.sendToClient(array);
                }

            } else if (message.startsWith("check requests@")) {
                String[] parts = message.split("@");
                if (parts.length == 2 && parts[1] != null) {
                    String communityManager = parts[1];
                    // Get the community members based on the community manager's ID
                    List<Task> requests = ConnectToDataBase.getTasksWithStatus(communityManager, 3);
                    Object[] array = new Object[2];
                    array[0] = "request"; // Assign a String object to the first index
                    array[1] = requests;
                    client.sendToClient(array);
                }

            } else if (message.startsWith("modify")) {
                String taskid = message.split(" ")[1];
                modifyTask(Integer.parseInt(taskid));
                client.sendToClient(ConnectToDataBase.getAllTasks());
            } else if (message.startsWith("update data")) {
                System.out.println("server " + UpdateTaskDetails.getUpdateVale());
                String[] parts = message.split("@");
                if (parts.length >= 5 && parts[0].equals("update data")) {
                    String taskId = parts[1];
                    String newData = parts[2];
                    String updateVale = parts[3];
                    String communityManager = parts[4];

                    try {
                        int taskIdInt = Integer.parseInt(taskId);
                        Task task = ConnectToDataBase.getTaskById(taskIdInt);
                        if (task != null) {
                            boolean k = task.getUser().getCommunity().equals(communityManager);
                            if (!k) {
                                client.sendToClient("notInYourCommunity");
                            } else if (task.getStatus() == 5) {
                                client.sendToClient("The task is canceled.");
                            } else if (task.getStatus() != 2 && !updateVale.equals("status")) {
                                client.sendToClient("The task's status isn't 2.");
                            } else if (k) {
                                if ((Integer.parseInt(newData) < 0 || Integer.parseInt(newData) > 5) && updateVale.equals("status")) {
                                    client.sendToClient("the status is illegal");
                                    return;
                                }
                                if (updateVale.equals("status") && (task.getStatus() == 3 || task.getStatus() == 0) && (Integer.parseInt(newData)!=0 || Integer.parseInt(newData)!=1)) {
                                    Message update = new Message("update cancel list");
                                    update.setObj(task);
                                    sendToAllClients(update);// to update the requests that the client can cancel.
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
                String[] parts = message.split(" ");
                if (parts.length == 3 && parts[2] != null) {
                    String id = parts[2];
                    User temp = null;
                    // Update the database with the extracted ID
                    try {
                        ArrayList<User> loggedInList = UserControl.getLoggedInList();
                        for (User user : loggedInList) {
                            if (user.getID().equals(id))
                                temp = user;
                        }
                        ConnectToDataBase.updateIsConnect(false, temp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // Handle invalid message format
                    System.err.println("Invalid 'log out' message format: " + message);
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
                            List<Task> requests = ConnectToDataBase.getTasksWithStatus(task.getUser().getCommunity(), 3);
                            Object[] array = new Object[2];
                            array[0] = "accept"; // Assign a String object to the first index
                            array[1] = requests;
                            client.sendToClient(array);
                        } else {
                            System.out.println("Task with ID " + taskId + " not found.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid task ID: " + taskId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (message.startsWith("cancel request")) {
                String[] parts = message.split("@");
                if (parts.length >= 2 && parts[0].equals("cancel request")) {
                    String taskId = parts[1];
                    try {
                        int taskIdInt = Integer.parseInt(taskId);
                        Task task = ConnectToDataBase.getTaskById(taskIdInt);
                        if (task != null) {
                            ConnectToDataBase.updateTaskData("5", task, "status");
                            System.out.println("user id " + task.getUser().getID());
                            List<Task> requests = ConnectToDataBase.getTasksWithStatusAndUser(task.getUser().getID());
                            Object[] array = new Object[2];
                            array[0] = "canceled!"; // Assign a String object to the first index
                            array[1] = requests;
                            client.sendToClient(array);
                            String notify = "Task (Id number: " + task.getIdNum() + ") has been canceled";
                            sendToAllClients(task);
                            Warning warning = new Warning(notify);
                            sendToAllClients(warning);
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
                String recipientId = news;
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
                client.sendToClient("back to list");
            } else if (message.startsWith("Task is rejected")) {
                String[] parts = message.split("@");
                if (parts.length >= 2 && parts[0].equals("Task is rejected")) {
                    String taskId = parts[1];
                    try {
                        int taskIdInt = Integer.parseInt(taskId);
                        Task task = ConnectToDataBase.getTaskById(taskIdInt);
                        if (task != null) {
                            ConnectToDataBase.updateTaskData(String.valueOf(4), task, "status");
                            List<Task> requests = ConnectToDataBase.getTasksWithStatus(task.getUser().getCommunity(), 3);
                            Object[] array = new Object[2];
                            array[0] = "accept"; // Assign a String object to the first index
                            array[1] = requests;
                            client.sendToClient(array);
                            Message update = new Message("update cancel list");
                            update.setObj(task);
                            sendToAllClients(update);// to update the requests that the client can cancel.
                        } else {
                            System.out.println("Task with ID " + taskId + " not found.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid task ID: " + taskId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
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
                        UserControl.addUser(user);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        try (ObjectOutputStream out = new ObjectOutputStream(bos)) {
                            out.writeObject(user);  // assuming 'user' is your User object
                            out.flush();
                        } catch (IOException e) {
                            // Handle serialization exception
                            e.printStackTrace();
                        }
                        if (user.getisConnected()) {
                            client.sendToClient("LOGIN_FAIL2");
                            return;
                        }
                        ConnectToDataBase.updateIsConnect(true, user);

                        byte[] userBytes = bos.toByteArray();
                        client.sendToClient(userBytes);
                        // Send a success response back to the client
                        try {
                            if (username.startsWith("*")) {
                                client.sendToClient("Manager_LOGIN_SUCCESS");
                            } else {
                                client.sendToClient("LOGIN_SUCCESS");
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
