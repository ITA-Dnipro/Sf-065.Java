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

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    public File(Long fileId, Message messageId, String bookPicture, String fileContent) {
        this.fileId = fileId;
        this.messageId = messageId;
        this.fileName = bookPicture;
        this.filePath = fileContent;
    }

    public File() {
        this.fileId = null;
        this.messageId = null;
        this.fileName = null;
        this.filePath = null;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileId=" + fileId +
                ", fileName='" + fileName + '\'' +
                ", fileContent=" + filePath +
                '}';
    }
}
