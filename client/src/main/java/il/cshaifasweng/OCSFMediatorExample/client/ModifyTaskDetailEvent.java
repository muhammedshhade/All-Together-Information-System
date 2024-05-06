package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Task;
import il.cshaifasweng.OCSFMediatorExample.entities.*;

public class ModifyTaskDetailEvent {
    private Task modifyTask;

    public ModifyTaskDetailEvent(Task modifyTask) {
        this.modifyTask = modifyTask;
    }

    public Task getModifyTask() {
        return modifyTask;
    }
}