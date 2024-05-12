
package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.*;

public class CompleteEvent {
    private Task completedTask;

    public CompleteEvent(Task canceledTask) {
        this.completedTask = canceledTask;
    }

    public Task getCompletedTask() {
        return completedTask;
    }
}