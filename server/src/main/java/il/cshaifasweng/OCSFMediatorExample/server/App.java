package il.cshaifasweng.OCSFMediatorExample.server;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class App {
    private static SimpleServer server;


    public static void main(String[] args) throws IOException {
        try {
            System.out.println("In App.server");
            ConnectToDataBase.initializeDatabase();
            server = new SimpleServer(3000);
            server.listen();
            System.out.print("Listening");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

