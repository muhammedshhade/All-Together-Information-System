package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.*;

public class TaskCancellationEvent {
    private Task canceledTask;

    public TaskCancellationEvent(Task canceledTask) {
        this.canceledTask = canceledTask;
    }

    public Task getCanceledTask() {
        return canceledTask;
    }
}