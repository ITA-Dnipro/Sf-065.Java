package com.ssjavaacademy.www.messengerattachments.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileGetDto {
    @JsonProperty("fileId")
    private Long fileId;

    @JsonProperty("messageId")
    private Long messageId;

    @JsonProperty("fileName")
    private String fileName;

    @JsonProperty("filePath")
    private String filePath;

    public FileGetDto(Long fileId, Long messageId, String bookPicture, String fileContent) {
        this.fileId = fileId;
        this.messageId = messageId;
        this.fileName = bookPicture;
        this.filePath = fileContent;
    }

    public FileGetDto() {
        this.fileId = null;
        this.messageId = null;
        this.fileName = null;
        this.filePath = null;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
