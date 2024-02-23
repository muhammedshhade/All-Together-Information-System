package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.User;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class stringSubscriber {
    private String receivedMSG;
    @Subscribe
    public void handleMSG(String msg) {
        // Handle the received student list
        this.receivedMSG = msg;
    }
    public stringSubscriber() {
        this.receivedMSG = "null";
    }
    public String getReceivedMSG(){return this.receivedMSG;}
}