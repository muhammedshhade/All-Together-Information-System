//package il.cshaifasweng.OCSFMediatorExample.entities;
//
//import il.cshaifasweng.OCSFMediatorExample.entities.Task;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//@Table(name = "uploaded_task_lists")
//public class UploadedTaskList {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToMany(mappedBy = "uploadedTaskList", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Task> tasks;
//
//    public UploadedTaskList() {
//        // Default constructor
//    }
//
//    // Getters and setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public List<Task> getTasks() {
//        return tasks;
//    }
//
//    public void setTasks(List<Task> tasks) {
//        this.tasks = tasks;
//    }
//}
