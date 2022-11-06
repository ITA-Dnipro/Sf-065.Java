package com.ssjavaacademy.www.messengerattachments.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "messages")
public class Message {
    @Column(name = "message_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("messageId")
    private Long messageId;

    @Column(name = "subject", nullable = false)
    @Size(min = 2, max = 255, message = "must be between 2 and 255 characters long")
    @JsonProperty("subject")
    private String subject;

    @Column(name = "text", nullable = false)
    @Size(min = 1, max = 5000, message = "must be between 1 and 5000 characters long")
    @JsonProperty("text")
    private String text;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "from_user", nullable = false)
    @JsonProperty("fromUser")
    private String fromUser;

    @Column(name = "to_user", nullable = false)
    @JsonProperty("toUsers")
    private String toUsers;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    @OneToMany(targetEntity = File.class, mappedBy = "messageId", cascade = CascadeType.ALL)
    private Set<File> files;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUsers() {
        return toUsers;
    }

    public void setToUsers(String toUsers) {
        this.toUsers = toUsers;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

    public Message(Long messageId, String subject, String text, Instant createdAt, String fromUser, String toUsers, Boolean isRead, Set<File> files) {
        this.messageId = messageId;
        this.subject = subject;
        this.text = text;
        this.createdAt = createdAt;
        this.fromUser = fromUser;
        this.toUsers = toUsers;
        this.isRead = isRead;
        this.files = files;
    }

    public Message() {
        this.messageId = null;
        this.subject = null;
        this.text = null;
        this.createdAt = null;
        this.fromUser = null;
        this.toUsers = null;
        this.isRead = false;
        this.files = null;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", fromUser='" + fromUser + '\'' +
                ", toUsers='" + toUsers + '\'' +
                ", isRead=" + isRead +
                ", files=" + files +
                '}';
    }
}
