package com.example.employeemanagementauth.service;

import com.example.employeemanagementauth.dto.DepartmentRequest;
import com.example.employeemanagementauth.dto.DepartmentsDTO;
import com.example.employeemanagementauth.dto.UserDTO;
import com.example.employeemanagementauth.dto.UserRequest;
import com.example.employeemanagementauth.exeptions.DepartmentNotFoundException;
import com.example.employeemanagementauth.model.Department;
import com.example.employeemanagementauth.model.User;
import com.example.employeemanagementauth.repository.DepartmentsRepository;
import com.example.employeemanagementauth.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DepartmentsServiceImpl  implements DepartmentsService{

    private final UserRepository userRepository;

    private final DepartmentsRepository departmentsRepository;

    @Override
    public List<Department> getAllDepartments() {
        return departmentsRepository
                .findAll();

    }

    @Override
    public DepartmentsDTO getAllDetails(Long id) {
        return departmentsRepository.findById(id).map(mapToDepartmentsDTO)
                .orElseThrow(() -> new DepartmentNotFoundException("Department is not found."));
    }

    @Override
    public Set<DepartmentsDTO> getAllSiblings(Long id) {
        return departmentsRepository.findById(id).map(findSiblings)
                .orElseThrow(() -> new DepartmentNotFoundException("Department is not found."));
    }

    @Transactional
    @Override
    public DepartmentsDTO createDepartment(DepartmentRequest departmentRequest) {
        Optional<Department> newDepartment = departmentsRepository.findByDepartmentName(departmentRequest.getDepartmentName());
        if (newDepartment.isPresent()){
            throw new DepartmentNotFoundException("Department already exist");
        }
        Department department = Department.builder()
                .departmentName(departmentRequest.getDepartmentName())
                .build();
      Department savedDepartment =  departmentsRepository.save(department);
      return DepartmentsDTO.builder()
              .enabled(savedDepartment.isEnabled())
              .departmentName(savedDepartment.getDepartmentName())
              .subDepartments(savedDepartment.getSubDepartments())
              .id(savedDepartment.getId())
              .build();
    }

    @Transactional
    @Override
    public  DepartmentsDTO  createsubDepartment(String name, DepartmentRequest departmentRequest){
       Department parentDepartment = departmentsRepository
               .findByDepartmentName(name)
               .orElseThrow(() -> new DepartmentNotFoundException ("Department not found"));

    Department savedDepartment = departmentsRepository.save(parentDepartment.addSubDepartment(departmentRequest.getDepartmentName()));
        return DepartmentsDTO.builder()
                .enabled(savedDepartment.isEnabled())
                .departmentName(savedDepartment.getDepartmentName())
                .subDepartments(savedDepartment.getSubDepartments())
                .id(savedDepartment.getId())
                .build();
    }

    @Transactional
    @Override
    public UserDTO assignUserTodepartment(Long departmentId, String username) {
        Department department = departmentsRepository.findById(departmentId)
                .orElseThrow(()-> new DepartmentNotFoundException("Department not found with id " + departmentId));
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found" + username));
        user.setDepartment(department);
        userRepository.save(user);
        return UserDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .position(user.getPosition())
                .department(user.getDepartment())
                .id(user.getId())
                .build();
    }

    private Function<Department, DepartmentsDTO> mapToDepartmentsDTO = d -> DepartmentsDTO.builder()
            .id(d.getId())
            .departmentName(d.getDepartmentName())
            .employees(d.getEmployees())
            .parentDepartment(d.getParentDepartment())
            .subDepartments(d.getSubDepartments()).build();

    private Function<Department, Set<DepartmentsDTO>> findSiblings = d -> d.getParentDepartment().getSubDepartments().stream()
            .map(p -> DepartmentsDTO.builder().id(d.getId()).departmentName(d.getDepartmentName()).build()).collect(Collectors.toSet());


}
