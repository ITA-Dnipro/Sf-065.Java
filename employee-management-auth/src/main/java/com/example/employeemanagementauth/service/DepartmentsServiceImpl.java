package com.example.employeemanagementauth.service;

import com.example.employeemanagementauth.dto.DepartmentRequest;
import com.example.employeemanagementauth.dto.DepartmentsDTO;
import com.example.employeemanagementauth.dto.UserDTO;
import com.example.employeemanagementauth.exeptions.DepartmentNotFoundException;
import com.example.employeemanagementauth.mapper.DepartmentsMapper;
import com.example.employeemanagementauth.mapper.UserMapper;
import com.example.employeemanagementauth.model.Department;
import com.example.employeemanagementauth.model.User;
import com.example.employeemanagementauth.repository.DepartmentsRepository;
import com.example.employeemanagementauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DepartmentsServiceImpl  implements DepartmentsService{

    private final UserRepository userRepository;

    private final DepartmentsRepository departmentsRepository;

    @Override
    public List<DepartmentsDTO> getAllDepartments() {
        return departmentsRepository
                .findAll().stream()
                .map(DepartmentsMapper.INSTANCE::mapDepartmentToDTO)
                .collect(Collectors.toList());

    }

    @Override
    public DepartmentsDTO getDepartment(Long id) {
        Department department = departmentsRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department is not found."));
        return DepartmentsMapper.INSTANCE.mapDepartmentToDTO(department);
    }
    @Override
    public List<DepartmentsDTO> findDepartmentsByParent(Long parentId){
       Optional<List<Department>> childDepartments = departmentsRepository.findEmployeeByParentDepartment(parentId);
       if(childDepartments.isPresent()){
         return   childDepartments.get().stream().map(DepartmentsMapper.INSTANCE::mapDepartmentToDTO).collect(Collectors.toList());
       } else {
           throw new DepartmentNotFoundException("Department is not found.");
       }
    }



    @Transactional
    @Override
    public DepartmentsDTO createDepartment(DepartmentRequest departmentRequest) {
        Optional<Department> newDepartment = departmentsRepository.findByDepartmentName(departmentRequest.getDepartmentName());
        if (newDepartment.isPresent()){
            throw new DepartmentNotFoundException("Department already exist");
        }
        User lead = userRepository.findById(departmentRequest.getLeadId())
                .orElseThrow(()->new UsernameNotFoundException("User is not found"));
        Department department = Department.builder()
                .departmentName(departmentRequest.getDepartmentName())
                .departmentLead(lead)
                .build();
      Department savedDepartment =  departmentsRepository.save(department);
      return DepartmentsMapper.INSTANCE.mapDepartmentToDTO(savedDepartment);
    }


    @Transactional
    @Override
    public DepartmentsDTO assignUserTodepartment(Long departmentId, String username) {
        Department department = departmentsRepository.findById(departmentId)
                .orElseThrow(()-> new DepartmentNotFoundException("Department not found with id " + departmentId));
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found" + username));
        user.setDepartment(department);
        User savedUser = userRepository.save(user);
        Department changedDepartment = departmentsRepository.findById(departmentId).orElseThrow();
        return DepartmentsMapper.INSTANCE.mapDepartmentToDTO(department);
    }
}
