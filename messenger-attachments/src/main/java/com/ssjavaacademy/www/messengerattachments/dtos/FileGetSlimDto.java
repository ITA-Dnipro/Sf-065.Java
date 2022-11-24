package com.ssjavaacademy.www.messengerattachments.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

public class FileGetSlimDto extends RepresentationModel {
    @JsonProperty("fileId")
    private Long fileId;

    @JsonProperty("fileName")
    private String fileName;

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileId() {
        return fileId;
    }

    public FileGetSlimDto() {
        this.fileId = null;
        this.fileName = "";
    }
    public FileGetSlimDto(long fileId, String fileName) {
        this.fileId = fileId;
        this.fileName = fileName;
    }
}
