package com.ssjavaacademy.www.messengerattachments.controllers;

import com.ssjavaacademy.www.messengerattachments.dtos.FileGetSlimDto;
import com.ssjavaacademy.www.messengerattachments.entities.File;
import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.EmptyTokenException;
import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.FileNotFoundException;
import com.ssjavaacademy.www.messengerattachments.mappers.FileMapper;
import com.ssjavaacademy.www.messengerattachments.services.FileService;
import com.ssjavaacademy.www.messengerattachments.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ssjavaacademy.www.messengerattachments.exceptionHandlers.EmptyTokenException.isTokenEmpty;

@RestController
@RequestMapping("api/v1/files")
public class FileController {
    private final FileService fileService;
    private final UserService userService;

    @Autowired
    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Set<FileGetSlimDto>> getAllFiles(@RequestHeader String authorization) throws EmptyTokenException {
        userService.validateToken(authorization);
        HttpStatus httpStatus = HttpStatus.OK;
        List<File> files = fileService.findAll();
        Set<FileGetSlimDto> body = FileMapper.fileSetToFileGetSlimDtoSet(new HashSet<>(files));

        return new ResponseEntity<>(body, httpStatus);
    }

    @GetMapping("/{id}")
    public ModelAndView getById(
            @PathVariable(value = "id") long id,
            @RequestHeader String authorization) throws EmptyTokenException, FileNotFoundException {
        isTokenEmpty(authorization);
        File file = fileService.findById(id).orElseThrow(() -> new FileNotFoundException("File Not Found"));
        String linkToSource = file.getFilePath();

        return new ModelAndView("redirect:" + linkToSource);
    }
}
