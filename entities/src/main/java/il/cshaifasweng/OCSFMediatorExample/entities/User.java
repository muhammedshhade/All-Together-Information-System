package il.cshaifasweng.OCSFMediatorExample.entities;
import javax.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ID", nullable = false)
    private String Id;
    @Column(name = " First Name", nullable = false)
    private String first_name;
    @Column(name = " Last Name", nullable = false)
    private String last_name;

    @Column(name = "is_connected")
    private boolean isConnected;

    @Column(name = "commentary")
    private String commentary;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "salt")
    private String salt;


    @Column(name = "address")
    private String address;
    @Column(name = "Email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Task> tasks;

    // Constructor, getters, and setters...

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public User(String id,String first_name,String
                Last_name,boolean isConnected, String commentary,
                String username, String Password, String address,String emil, Role role) {
        this.Id=id;
        this.first_name = first_name;
        this.last_name=Last_name;
        this.isConnected = isConnected;
        this.commentary = commentary;
        this.username = username;
        this.salt = generateSalt();
        this.passwordHash = hashPassword(Password, this.salt);
        this.address = address;
        this.email=emil;
        this.role = role;
    }
    public User(){

    }
    public enum Role {
        Manager, USER
        // Define other roles as needed
    }

    // Generate a salt
    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return bytesToHex(saltBytes);
    }

     String hashPassword(String password, String salt) {
        try {
            String passwordWithSalt = password + salt;
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashedPassword = md.digest(passwordWithSalt.getBytes());
            return bytesToHex(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception
            return null;
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
    public Long getkeyId() {
        return id;
    }
    public String getID() {
        return Id;
    }
    public void setId(String ID){
        this.Id=ID;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setfirstName(String name) {
        this.first_name = name;
    }
    public String getLasttName() {
        return last_name;
    }
    public void setemail(String email) {
        this.email =email;
    }
    public String getEmail() {
        return email;
    }

    public void setlastName(String name) {
        this.last_name = name;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.salt = generateSalt();
        this.passwordHash = hashPassword(password, this.salt);
    }


    // Getters and setters for other fields, and any other necessary code would go here
}

