package com.ssjavaacademy.www.messengerattachments.mappers;

import com.ssjavaacademy.www.messengerattachments.dtos.FileGetSlimDto;
import com.ssjavaacademy.www.messengerattachments.entities.File;

import java.util.HashSet;
import java.util.Set;

public class FileMapper {
    public static FileGetSlimDto fileToFileGetSlimDto(File file) {
        FileGetSlimDto fileGetSlimDto = new FileGetSlimDto();

        fileGetSlimDto.setFileId(file.getFileId());
        fileGetSlimDto.setFileName(file.getFileName());

        return fileGetSlimDto;
    }

    public static Set<FileGetSlimDto> fileSetToFileGetSlimDtoSet(Set<File> files) {
        Set<FileGetSlimDto> slimDtoSet = new HashSet<>();

        for (File file : files) {
            FileGetSlimDto fileGetSlimDto = new FileGetSlimDto();

            fileGetSlimDto.setFileId(file.getFileId());
            fileGetSlimDto.setFileName(file.getFileName());
            slimDtoSet.add(fileGetSlimDto);
        }
        return slimDtoSet;
    }
}
