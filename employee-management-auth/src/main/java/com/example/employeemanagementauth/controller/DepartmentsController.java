package com.example.employeemanagementauth.controller;


import com.example.employeemanagementauth.dto.DepartmentRequest;
import com.example.employeemanagementauth.dto.DepartmentsDTO;
import com.example.employeemanagementauth.dto.UserDTO;
import com.example.employeemanagementauth.service.DepartmentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/departments")
@SecurityRequirement(name = "Bearer Authentication")
public class DepartmentsController {


    private final DepartmentsService departmentsService;

    @Operation(summary = "Returns a list of departments.")
    @ApiResponse(responseCode = "200",  content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = DepartmentsDTO.class))})
    @GetMapping
    public List<DepartmentsDTO> getAllDepartments() {
        List<DepartmentsDTO> departments = departmentsService.getAllDepartments();
        departments.forEach(departmentsDTO ->
                departmentsDTO.add(linkTo(methodOn(DepartmentsController.class).getDepartment(departmentsDTO.getId())).withSelfRel()))  ;
        return  departments;
    }

    @Operation(summary = "Get an department by its id.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the department", content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = DepartmentsDTO.class))}),
           @ApiResponse(responseCode = "404", description = "Department not found", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentsDTO> getDepartment(@PathVariable("id") Long id) {
        DepartmentsDTO departmentsDTO = departmentsService.getDepartment(id);
        departmentsDTO.add(linkTo(methodOn(DepartmentsController.class).getDepartment(id)).withSelfRel());
        return  ResponseEntity.status(HttpStatus.OK)
                .body(departmentsDTO);

    }
    @Operation(summary = "Get list of departments by parent.")
   @ApiResponse(responseCode = "200",  content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = DepartmentsDTO.class))})
    @GetMapping("{id}/departments")
   List<DepartmentsDTO> getDepartmentsByParent(@PathVariable Long id){
        List<DepartmentsDTO> departments = departmentsService.findDepartmentsByParent(id);
        departments.forEach(departmentsDTO ->
                departmentsDTO.add(linkTo(methodOn(DepartmentsController.class).getDepartment(departmentsDTO.getId())).withSelfRel()));
        return departments;
    }
    @Operation(summary = "Crate a new department.")
    @ApiResponse(responseCode = "201", description = "Department is created", content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = DepartmentsDTO.class))})
    @PostMapping
    public ResponseEntity<DepartmentsDTO> createDepartment(@RequestBody DepartmentRequest departmentRequest ) {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(departmentsService.createDepartment(departmentRequest));
    }
    @Operation(summary = "Assign user to department.")
    @PutMapping("{departmentId}/{username}")
    public ResponseEntity<DepartmentsDTO> assignUserToDepartment(@PathVariable Long departmentId, @PathVariable String username ) {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(departmentsService.assignUserTodepartment(departmentId, username));
    }


}
