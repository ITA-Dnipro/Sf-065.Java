package com.ssjavaacademy.www.messengerattachments.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "files")
public class File {
    @Column(name = "file_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fileId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = true)
    private Message messageId;

    @Column(name = "file_name", nullable = true)
    private String fileName;

    @Column(name = "file_size", nullable = true)
    private long fileSize;

    @Column(name = "file_content", nullable = false)
    private byte[] fileContent;

    public File(Long fileId, Message messageId, String bookPicture, long fileSize, byte[] fileContent) {
        this.fileId = fileId;
        this.messageId = messageId;
        this.fileName = bookPicture;
        this.fileSize = fileSize;
        this.fileContent = fileContent;
    }

    public File() {
        this.fileId = null;
        this.messageId = null;
        this.fileName = null;
        this.fileSize = 0;
        this.fileContent = null;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Message getMessageId() {
        return messageId;
    }

    public void setMessageId(Message messageId) {
        this.messageId = messageId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileId=" + fileId +
                ", fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                ", fileContent=" + Arrays.toString(fileContent) +
                '}';
    }
}
