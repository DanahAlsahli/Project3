package org.example.project3.Controller;
import jakarta.validation.Valid;
import org.example.project3.Api.ApiResponse;
import org.example.project3.DTO.CustomerRegisterDTO;
import org.example.project3.Model.User;
import org.example.project3.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/getCustomers")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<?> getCustomers() {
        return ResponseEntity.status(200).body(customerService.getCustomers());
    }

    @GetMapping("/getById/{customerId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer customerId) {
        return ResponseEntity.status(200).body(customerService.getCustomerById(customerId));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody @Valid CustomerRegisterDTO dto) {
        customerService.registerCustomer(dto);

        return ResponseEntity.status(200).body(new ApiResponse("The Customer registered successfully"));
    }

    @PutMapping("/update/{phoneNumber}")
    public ResponseEntity<?> updateCustomer(@AuthenticationPrincipal User user, @PathVariable String phoneNumber) {
        customerService.updateCustomer(user.getId(), phoneNumber);

        return ResponseEntity.status(200).body(new ApiResponse("The Customer updated successfully"));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomer(@AuthenticationPrincipal User user) {
        customerService.deleteCustomer(user.getId());

        return ResponseEntity.status(200).body(new ApiResponse("The Customer deleted successfully"));
    }

}
