package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;


@Entity
@Table(name = "managers")
public class Manager extends User implements Serializable {
    @Column(name = "community")
    private String communityManager;

    // Default constructor
    public Manager() {
        super(); // Call the constructor of the superclass (User)
    }

    // Constructor with parameters
    public Manager(String id, String firstName, String lastName, boolean isConnected, String commentary, String username, String password, String address, String email, User.Role role, String communityManager) {
        super(id, firstName, lastName, isConnected, commentary, username, password, address, email, role);
        this.communityManager = communityManager;
    }

    public Manager(User user1, String s) {
        // Call the superclass constructor to set properties inherited from User
        super(user1.getID(), user1.getFirstName(), user1.getLastName(), user1.getisConnected(), user1.getCommunity(), user1.getUsername(), user1.getPassword(), user1.getAddress(), user1.getEmail(), user1.getRole());
        // Set the communityManager property
        this.communityManager = s;
    }

    // Getters and Setters
    public String getCommunityManager() {
        return communityManager;
    }

    public void setCommunityManager(String communityManager) {
        this.communityManager = communityManager;
    }
}