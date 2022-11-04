package com.ssjavaacademy.www.messengerattachments.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileGetSlimDto {
    @JsonProperty("fileId")
    private Long fileId;

    @JsonProperty("fileName")
    private String fileName;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileGetSlimDto() {
        this.fileId = null;
        this.fileName = "";
    }
}
