package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.io.Serializable;

@Entity
@Table(name = "mangermasseges")

public class MessageToManeger implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "sender_id", nullable = false)
    private Long sender;

    @Column(name = "recipient_id", nullable = false, length = 9)
    private String recipient;

    @Column(name = "sent_time")
    private LocalDateTime sentTime; // Use the LocalDateTime class

    // Constructor
    public MessageToManeger(String content, Long sender, String recipient, LocalDateTime sentTime) {
        this.content = content;
        this.sender = sender;
        this.recipient = recipient;
        this.sentTime = sentTime; // Initialize the sentTime
    }


    public MessageToManeger() {

    }

    public MessageToManeger(String str, Long sender, Long aLong, LocalDateTime now) {
    }

    public LocalDateTime getSentTime() {
        return sentTime;
    }

    public void setSentTime(LocalDateTime sentTime) {
        this.sentTime = sentTime;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

}