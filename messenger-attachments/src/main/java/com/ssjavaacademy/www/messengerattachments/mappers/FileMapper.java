package com.ssjavaacademy.www.messengerattachments.mappers;

import com.ssjavaacademy.www.messengerattachments.dtos.FileGetDto;
import com.ssjavaacademy.www.messengerattachments.dtos.FileGetSlimDto;
import com.ssjavaacademy.www.messengerattachments.entities.File;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component("fileMapper")
public class FileMapper {
    public static FileGetSlimDto fileToFileGetSlimDto(File file) {
        FileGetSlimDto fileGetSlimDto = new FileGetSlimDto();
        fileGetSlimDto.setFileId(file.getFileId());
        fileGetSlimDto.setFileName(file.getFileName());

        return fileGetSlimDto;
    }

    public  FileGetDto fileToFileGetDto(File file) {
        FileGetDto fileGetDto = new FileGetDto();
        fileGetDto.setFileId(file.getFileId());
        fileGetDto.setMessageId(file.getMessageId().getMessageId());
        fileGetDto.setFilePath(file.getFilePath());
        fileGetDto.setFileName(file.getFileName());

        return fileGetDto;
    }

    public List<FileGetDto> fileSetToFileGetDtoSet(List<File> files) {
        List<FileGetDto> res = new ArrayList<>();

         for (File f : files) {
             res.add(fileToFileGetDto(f));
        }

        return res;
    }

    public static List<FileGetSlimDto> fileSetToFileGetSlimDtoSet(List<File> files) {
        List<FileGetSlimDto> slimDtoSet = new ArrayList<>();

        for (File file : files) {
            FileGetSlimDto fileGetSlimDto = new FileGetSlimDto();
            fileGetSlimDto.setFileId(file.getFileId());
            fileGetSlimDto.setFileName(file.getFileName());
            slimDtoSet.add(fileGetSlimDto);
        }

        return slimDtoSet;
    }
}
