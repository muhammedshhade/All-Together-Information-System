package il.cshaifasweng.OCSFMediatorExample.entities;

import org.hibernate.Hibernate;


import java.util.ArrayList;
import java.util.List;

public class GlobalDataSaved {
    public static List<Task> requestsToCheck =new ArrayList<>();  //for manager
    public static List<Task> getRequestService = new ArrayList<>(); // for user to cancel request.
    public static List<Task> allTaskFroCommunity = new ArrayList<>(); // for manager to see all the requests.



}
