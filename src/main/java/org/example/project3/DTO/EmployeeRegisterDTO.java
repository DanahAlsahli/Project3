package org.example.project3.DTO;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRegisterDTO {

    @NotEmpty(message = "username is required")
    @Size(min = 4, max = 10)
    private String username;

    @NotEmpty(message = "password is required")
    @Size(min = 8, max = 72)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "password must contain uppercase, lowercase, number and special character")
    private String password;

    @NotEmpty(message = "name is required")
    @Size(min = 2, max = 20)
    private String name;

    @NotEmpty(message = "email is required")
    @Email(message = "email not valid")
    private String email;

    @NotEmpty(message = "position is required")
    private String position;

    @NotNull(message = "salary is required")
    @PositiveOrZero(message = "salary should be positive")
    private Double salary;
}
