package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Task;

public class accepTaskEvent {
    private Task manageraccept;

    public accepTaskEvent(Task accept) {
        this.manageraccept = accept;
    }

    public Task getacceptedTask() {
        return manageraccept;
    }
}
