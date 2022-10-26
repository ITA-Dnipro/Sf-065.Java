package com.ssjavaacademy.www.messengerattachments.controllers;

import com.ssjavaacademy.www.messengerattachments.entities.File;
import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.EmptyTokenException;
import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.FileNotFoundException;
import com.ssjavaacademy.www.messengerattachments.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ssjavaacademy.www.messengerattachments.exceptionHandlers.EmptyTokenException.isTokenEmpty;

@RestController
@RequestMapping("api/v1/files")
public class FileController {
    @Autowired
    FileService fileService;

    @GetMapping
    public ResponseEntity<List<File>> getAllFiles(@RequestHeader String authorization) throws EmptyTokenException {
        HttpStatus httpStatus = HttpStatus.OK;

        List<File> files = fileService.findAll();
        isTokenEmpty(authorization);

        return new ResponseEntity<>(files, httpStatus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<File> getById(
            @PathVariable(value = "id") long id,
            @RequestHeader String authorization) throws EmptyTokenException, FileNotFoundException {
        HttpStatus httpStatus = HttpStatus.OK;

        isTokenEmpty(authorization);
        File body = fileService.findById(id).orElseThrow(() -> new FileNotFoundException("File Not Found"));

        return new ResponseEntity<>(body, httpStatus);
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<File> downloadFile(@PathVariable String filename) {
        File file = fileService.findByFileName(filename).get(0);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(file);
    }
}
