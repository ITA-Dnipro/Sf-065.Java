package com.ssjavaacademy.www.messengerattachments.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MessageGetDto {
    @Column(name = "message_id", nullable = false)
    @Id
    @JsonProperty("messageId")
    private Long messageId;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("text")
    private String text;

    @JsonProperty("createdAt")
    private Date createdAt;

    @JsonProperty("fromUser")
    private String fromUser;

    @JsonProperty("toUsers")
    private String toUsers;

    private Boolean isRead;

    @JsonProperty("files")
    private Set<FileGetSlimDto> files;

    public Set<FileGetSlimDto> getFiles() {
        return files;
    }

    public void setFiles(Set<FileGetSlimDto> files) {
        this.files = files;
    }

    public Long getMessageId() {
        return messageId;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getFromUser() {
        return fromUser;
    }

    public String getToUsers() {
        return toUsers;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public void setToUsers(String toUsers) {
        this.toUsers = toUsers;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public MessageGetDto() {
        this.messageId = null;
        this.subject = "";
        this.text = "";
        this.fromUser = "";
        this.toUsers = "";
        this.isRead = false;
        this.createdAt = null;
        this.files = new HashSet<>();
    }
}
