package com.ssjavaacademy.www.messengerattachments.services;

import com.ssjavaacademy.www.messengerattachments.entities.File;
import com.ssjavaacademy.www.messengerattachments.entities.Message;
import com.ssjavaacademy.www.messengerattachments.repositories.FileRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {
    @Mock
    FileRepository fileRepository;

    @InjectMocks
    FileService fileService;

    @Test
    @DisplayName("Positive test - find all files")

    void findAll() {
        File expectedFile = new File();
        expectedFile.setFileId(1L);
        expectedFile.setFilePath("/dwsd/fwf");
        expectedFile.setMessageId(new Message());
        expectedFile.setFileName("fwfw");

        File expectedFile2 = new File();
        expectedFile2.setFileId(2L);
        expectedFile2.setFilePath("/dwsd/fwf");
        expectedFile2.setMessageId(new Message());
        expectedFile2.setFileName("fwfw");

        List<File> list = new ArrayList<>();
        list.add(expectedFile);
        list.add(expectedFile2);

        when(fileRepository.findAll()).thenReturn(list);
        List<File> files = fileService.findAll();
        assertEquals(2, files.size());
        assertNotNull(files);
    }

    @Test
    @DisplayName("Positive test - save file")
    void save() {
        File expectedFile = new File();
        expectedFile.setFileId(1L);
        expectedFile.setFilePath("/dwsd/fwf");
        expectedFile.setMessageId(new Message());
        expectedFile.setFileName("fwfw");

        when(fileRepository.save(any(File.class))).thenReturn(expectedFile);

         fileService.save(expectedFile);

        assertNotNull(expectedFile);
        assertNotNull(expectedFile.getFileId());

        assertThat(expectedFile.getFilePath()).isEqualTo("/dwsd/fwf");
    }

    @Test
    @DisplayName("Positive test - get file by id")
    void findById() {
        File expectedFile = new File();
        expectedFile.setFileId(1L);
        expectedFile.setFilePath("/dwsd/fwf");
        expectedFile.setMessageId(new Message());
        expectedFile.setFileName("fwfw");

        when(fileRepository.findById(1L)).thenReturn(Optional.of(expectedFile));

        File result = fileService.findById(1L).orElse(new File());
        assertEquals(expectedFile.getFileId(), result.getFileId());
        assertEquals(expectedFile.getMessageId(), result.getMessageId());
        assertEquals(expectedFile.getFileName(), result.getFileName());
        assertEquals(expectedFile.getFilePath(), result.getFilePath());

        verify(fileRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Positive test - delete file")
    void delete() {
        File expectedFile = new File();
        expectedFile.setFileId(1L);
        expectedFile.setFilePath("/dwsd/fwf");
        expectedFile.setMessageId(new Message());
        expectedFile.setFileName("fwfw");

        File expectedFile2 = new File();
        expectedFile2.setFileId(2L);
        expectedFile2.setFilePath("/dwsd/fwf");
        expectedFile2.setMessageId(new Message());
        expectedFile2.setFileName("fwfw");

        List<File> list = new ArrayList<>();
        list.add(expectedFile);
        list.add(expectedFile2);

        when(fileRepository.findAll()).thenReturn(list);
        List<File> files = fileService.findAll();
        assertEquals(2, files.size());

        doNothing().when(fileRepository).delete(expectedFile2);
        fileService.delete(expectedFile2);

        verify(fileRepository, times(1)).delete(expectedFile2);
    }
}