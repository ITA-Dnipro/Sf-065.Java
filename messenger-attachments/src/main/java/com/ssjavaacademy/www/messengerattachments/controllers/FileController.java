package com.ssjavaacademy.www.messengerattachments.controllers;

import com.ssjavaacademy.www.messengerattachments.dtos.FileGetSlimDto;
import com.ssjavaacademy.www.messengerattachments.entities.File;
import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.EmptyTokenException;
import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.FileNotFoundException;
import com.ssjavaacademy.www.messengerattachments.mappers.FileMapper;
import com.ssjavaacademy.www.messengerattachments.services.FileService;
import com.ssjavaacademy.www.messengerattachments.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static com.ssjavaacademy.www.messengerattachments.exceptionHandlers.EmptyTokenException.isTokenEmpty;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@SecurityRequirement(name = "Authorization")
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
    public ResponseEntity<CollectionModel<FileGetSlimDto>> getAllFiles(@RequestHeader String authorization) throws EmptyTokenException, FileNotFoundException {
        userService.validateToken(authorization);
        HttpStatus httpStatus = HttpStatus.OK;
        List<File> files = fileService.findAll();
        List<FileGetSlimDto> body = FileMapper.fileSetToFileGetSlimDtoSet(new ArrayList<>(files));

        for (FileGetSlimDto f : body) {
            Link singular = linkTo(methodOn(FileController.class).getById(f.getFileId(), authorization)).withSelfRel();
            f.add(singular);
        }

        Link link = linkTo(methodOn(FileController.class).getAllFiles(authorization)).withSelfRel();

        return new ResponseEntity<>(CollectionModel.of(body, link), httpStatus);

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
