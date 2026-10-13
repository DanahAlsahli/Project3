package org.example.project3.DTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
    @NotEmpty(message = "Username is required")
    @Size(min = 4, max = 10)
    private String username;

    @NotEmpty(message = "Name is required")
    @Size(min = 2, max = 20)
    private String name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email not valid")
    private String email;
}