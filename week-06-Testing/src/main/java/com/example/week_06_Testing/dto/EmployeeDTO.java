package com.example.week_06_Testing.dto;

import com.example.week_06_Testing.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "Name of employee can not be blank")
    @Size(min = 3, max = 20, message = "Number of characters should be in range: [3,20]")
    private String name;

    @Email(message = "Please enter a valid email")
    private String email;

    @NotBlank(message = "role can't be blank")
   //@Pattern(regexp = "^(ADMIN|USER)$")
    @EmployeeRoleValidation
    private String role; //USER, ADMIN

    @Max(value=80, message = "Age can't be greater than 80")
    @Min(value=18, message = "Age should at-least greater than 18")
    private Integer age;

    @NotNull @Positive(message = "salary of the employee should be more than 0")
    @Digits(integer = 6, fraction = 2, message = "salary: XXXXX.YY")
    private Double salary;

    @PastOrPresent(message = "DOJ can't be future date")
    private LocalDate dateOfJoining;

    @AssertTrue(message = "Employee should be active")
    private Boolean isActive;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EmployeeDTO that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(role, that.role) && Objects.equals(age, that.age) && Objects.equals(salary, that.salary) && Objects.equals(dateOfJoining, that.dateOfJoining) && Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, role, age, salary, dateOfJoining, isActive);
    }
}


