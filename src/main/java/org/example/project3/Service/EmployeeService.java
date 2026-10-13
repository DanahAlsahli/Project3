package org.example.project3.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project3.Api.ApiException;
import org.example.project3.DTO.EmployeeRegisterDTO;
import org.example.project3.DTO.UpdateEmployeeDTO;
import org.example.project3.Model.Employee;
import org.example.project3.Model.User;
import org.example.project3.Repository.AuthRepository;
import org.example.project3.Repository.EmployeeRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final AuthRepository authRepository;

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }


    public Employee getEmployeeById(Integer employeeId) {

        Employee employee = employeeRepository.findEmployeeById(employeeId);

        if (employee == null) {
            throw new ApiException("employee not found");
        }

        return employee;
    }

    @Transactional
    public void registerEmployee(EmployeeRegisterDTO dto) {
        if (authRepository.existsByUsername(dto.getUsername())) {
            throw new ApiException("username already exists");
        }

        if (authRepository.existsByEmail(dto.getEmail())) {
            throw new ApiException("email already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        String hash = new BCryptPasswordEncoder().encode(dto.getPassword());
        user.setPassword(hash);
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole("EMPLOYEE");

        authRepository.save(user);

        Employee employee = new Employee();
        employee.setPosition(dto.getPosition());
        employee.setSalary(dto.getSalary());
        employee.setUser(user);

        employeeRepository.save(employee);
    }


    public void updateEmployee(Integer employeeId, UpdateEmployeeDTO dto) {

        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if (employee == null) {
            throw new ApiException("employee not found");
        }

        employee.setPosition(dto.getPosition());
        employee.setSalary(dto.getSalary());

        employeeRepository.save(employee);
    }


    @Transactional
    public void deleteEmployee(Integer employeeId) {

        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if (employee == null) {
            throw new ApiException("employee not found");
        }

        employeeRepository.delete(employee);
    }
}