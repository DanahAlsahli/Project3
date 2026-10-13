package org.example.project3.DTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeDTO {

    @NotEmpty(message = "position is required")
    @Size(max = 50)
    private String position;

    @NotNull(message = "salary is required")
    @PositiveOrZero(message = "salary should be positive or zero")
    private Double salary;
}