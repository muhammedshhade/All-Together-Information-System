package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity

@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idNum")
    private int idNum;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "status")
    private int status;

    @Column(name = "serviceType")
    private String serviceType;

    @Column(name = "note")
    private String note;

    @Column(name = "executionTime")
    private float executionTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY) // Assuming you want to use lazy loading
    @JoinColumn(name = "uploaded_task_list_id") // ForeignKey column in 'tasks' table
    private UploadedTaskList uploadedTaskList;

    // ... existing constructors, getters, and setters ...

    public UploadedTaskList getUploadedTaskList() {
        return uploadedTaskList;
    }

    public void setUploadedTaskList(UploadedTaskList uploadedTaskList) {
        this.uploadedTaskList = uploadedTaskList;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Constructors
    public Task() {
        // Default constructor required by Hibernate
    }

    public Task(LocalDate date, LocalTime time, int status, String serviceType, String note, float executionTime) {
        this.date = date;
        this.time = time;
        this.status = status;
        this.serviceType = serviceType;
        this.note = note;
        this.executionTime = executionTime;
    }

    // Getters and Setters
    public int getIdNum() {
        return idNum;
    }
    public void setIdNum(int idNum) {
        this.idNum = idNum;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public float getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(float executionTime) {
        this.executionTime = executionTime;
    }
}
