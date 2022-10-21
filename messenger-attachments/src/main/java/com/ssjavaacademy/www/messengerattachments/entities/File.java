package com.ssjavaacademy.www.messengerattachments.entities;

import javax.persistence.*;
@Entity
@Table(name = "files")
public class File {
    @Column(name = "file_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false)
    private Message messageId;

    @Column(name = "file_name", nullable= true)
    private String bookPicture;

    @Column(name = "file_size", nullable= true)
    private long fileSize;

    @Column(name = "file_content", nullable= false)
    private byte[] fileContent;

    public File(Long fileId, Message messageId, String bookPicture, long fileSize, byte[] fileContent) {
        this.fileId = fileId;
        this.messageId = messageId;
        this.bookPicture = bookPicture;
        this.fileSize = fileSize;
        this.fileContent = fileContent;
    }
    public File() {
        this.fileId = null;
        this.messageId = null;
        this.bookPicture = null;
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

    public String getBookPicture() {
        return bookPicture;
    }

    public void setBookPicture(String bookPicture) {
        this.bookPicture = bookPicture;
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
}
