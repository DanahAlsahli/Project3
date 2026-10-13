package org.example.project3.Controller;
import jakarta.validation.Valid;
import org.example.project3.Api.ApiResponse;
import org.example.project3.DTO.EmployeeRegisterDTO;
import org.example.project3.DTO.UpdateEmployeeDTO;
import org.example.project3.Model.Employee;
import org.example.project3.Service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;


    @GetMapping("/getEmployees")
    public ResponseEntity<?> getEmployees(){
        return ResponseEntity.status(200).body(employeeService.getEmployees());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@RequestBody @Valid EmployeeRegisterDTO dto) {
        employeeService.registerEmployee(dto);

        return ResponseEntity.status(200).body(new ApiResponse("The Employee registered successfully"));
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable Integer employeeId, @RequestBody @Valid UpdateEmployeeDTO dto) {
        employeeService.updateEmployee(employeeId, dto);

        return ResponseEntity.status(200).body(new ApiResponse("The Employee updated successfully"));
    }

    @GetMapping("/getById/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Integer employeeId) {

        return ResponseEntity.status(200).body(employeeService.getEmployeeById(employeeId));
    }




    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer employeeId) {
        employeeService.deleteEmployee(employeeId);

        return ResponseEntity.status(200).body(new ApiResponse(" The Employee deleted successfully"));
    }
}