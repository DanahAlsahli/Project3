package org.example.project3.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "account number is required")
    @Pattern(regexp = "^[0-9]{4}(-[0-9]{4}){3}$")
    @Column(columnDefinition = "varchar(50) not null unique")
    private String accountNumber;

    @NotNull(message = "balance is required")
    @PositiveOrZero(message = "balance should be positive")
    @Column(columnDefinition = "double not null")
    private Double balance;


    @Column(columnDefinition = "boolean not null")
    private Boolean isActive = false;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
