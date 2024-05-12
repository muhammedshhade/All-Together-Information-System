package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.*;

public class VolunteerEvent {
    private Task volunteer;

    public VolunteerEvent(Task canceledTask) {
        this.volunteer = canceledTask;
    }

    public Task getVolunter() {
        return volunteer;
    }
}