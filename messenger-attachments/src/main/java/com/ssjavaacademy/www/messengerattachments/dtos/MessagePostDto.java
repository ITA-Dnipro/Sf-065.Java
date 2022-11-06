package com.ssjavaacademy.www.messengerattachments.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class MessagePostDto {
    @Column(name = "subject", nullable = false)
    @NotNull
    @JsonProperty("subject")
    @Size(min = 2, max = 255, message = "must be between 2 and 255 characters long")
    private String subject;

    @JsonProperty("text")
    @Column(name = "text", nullable = false)
    @NotNull
    @Size(min = 1, max = 5000, message = "must be between 1 and 5000 characters long")
    private String text;

    @Column(name = "from_user", nullable = false)
    @NotNull
    @JsonProperty("fromUser")
    private String fromUser;

    @Column(name = "to_user", nullable = false)
    @NotNull
    @JsonProperty("toUsers")
    private String toUsers;

    @JsonProperty("files")
    private Set<String> attachments;

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

    public Set<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<String> attachments) {
        this.attachments = attachments;
    }

    public MessagePostDto(String subject, String text, String fromUser, String toUsers, Set<String> attachments) {
        this.subject = subject;
        this.text = text;
        this.fromUser = fromUser;
        this.toUsers = toUsers;
        this.attachments = attachments;
    }

    public MessagePostDto() {
        this.subject = "";
        this.text = "";
        this.fromUser = "";
        this.toUsers = "";
        this.attachments = new HashSet<>();
    }

    @Override
    public String toString() {
        return "MessagePostDto{" +
                "subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", fromUser='" + fromUser + '\'' +
                ", toUsers='" + toUsers + '\'' +
                ", attachments=" + attachments +
                '}';
    }
}
