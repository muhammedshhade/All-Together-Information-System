package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "EmergencyCenter")
public class EmergencyCenter implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idNum")
    private int idNum;

    @Column(name = "phone")
    private String phoneNumber;

    @Column(name = "openhour")
    private float openHour;

    @Column(name = "close_hour") // Modified column name to 'close_hour'
    private float closeHour;

    @Column(name = "location")
    private String location;

    @Column(name = "service")
    private String service;



    public EmergencyCenter(String phoneNumber, float openHour, float closeHour, String location, String service) {
        this.phoneNumber = phoneNumber;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.location = location;
        this.service = service;
    }

    public EmergencyCenter() {
    }

    // Getters and Setters

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public float getOpenHour() {
        return openHour;
    }

    public void setOpenHour(float openHour) {
        this.openHour = openHour;
    }

    public float getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(float closeHour) {
        this.closeHour = closeHour;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "EmergencyCenter{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", openHour=" + openHour +
                ", closeHour=" + closeHour +
                ", location='" + location + '\'' +
                ", service='" + service + '\'' +
                '}';
    }


}
