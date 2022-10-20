package com.officemap.dto;

import com.officemap.repositories.EmployeeAllocationRepository;

public class DeskDto {
    private final EmployeeAllocationRepository employeeAllocationRepository;

    private Long deskId;

    private int deskNumber;

    private String deskStatus;

    private Long occupiedByEmployeeId;

    public DeskDto(EmployeeAllocationRepository employeeAllocationRepository) {
        this.employeeAllocationRepository = employeeAllocationRepository;
    }

}
