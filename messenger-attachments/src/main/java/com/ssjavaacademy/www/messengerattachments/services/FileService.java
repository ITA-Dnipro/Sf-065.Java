package com.ssjavaacademy.www.messengerattachments.services;

import com.ssjavaacademy.www.messengerattachments.entities.File;
import com.ssjavaacademy.www.messengerattachments.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<File> findAll() {
        return fileRepository.findAll();
    }

    public void save(File file) {
        fileRepository.save(file);
    }

    public void uploadFiles(List<File> files) {
        for (File file : files) {
            save(file);
        }
    }

    public Optional<File> findById(long id) {
        return fileRepository.findById(id);
    }

    public List<File> findByFileName(String filename) {
        return fileRepository.findByFileName(filename);
    }

    public void delete(File file) {
        fileRepository.delete(file);
    }
}
