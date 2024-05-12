package il.cshaifasweng.OCSFMediatorExample.server;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class App {
    private static SimpleServer server;


    public static void main(String[] args) throws IOException {
        try {
            System.out.println("In App.server");
            ConnectToDataBase.initializeDatabase();
            server = new SimpleServer(3000);
            server.listen();
            System.out.print("Listening");

            /* ********here *********** */
            TaskChecker taskChecker = new TaskChecker();
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(taskChecker, 0, 10, TimeUnit.MINUTES);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

