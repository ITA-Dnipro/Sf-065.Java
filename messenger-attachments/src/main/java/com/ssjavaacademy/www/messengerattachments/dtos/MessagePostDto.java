package com.ssjavaacademy.www.messengerattachments.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class MessagePostDto extends RepresentationModel {
    @Column(name = "subject", nullable = false)
    @JsonProperty("subject")
    @Size(min = 2, max = 255, message = "must be between 2 and 255 characters long")
    private String subject;

    @JsonProperty("text")
    @Column(name = "text", nullable = false)
    @Size(min = 1, max = 5000, message = "must be between 1 and 5000 characters long")
    private String text;

    @Column(name = "to_user", nullable = false)
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
        this.toUsers = toUsers;
        this.attachments = attachments;
    }

    public MessagePostDto() {
        this.subject = "";
        this.text = "";
        this.toUsers = "";
        this.attachments = new HashSet<>();
    }

    @Override
    public String toString() {
        return "MessagePostDto{" +
                "subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", toUsers='" + toUsers + '\'' +
                ", attachments=" + attachments +
                '}';
    }
}
