package com.codingshuttle.yash.modules.module1.dto;


import com.codingshuttle.yash.modules.module1.annotations.PasswordValidation;
import com.codingshuttle.yash.modules.module1.annotations.PrimeNumberValidation;
import jakarta.persistence.Id;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

    @Id
    private Long id;

    @Size(min = 3, max = 15, message = "Title should range between 3 to 15")
    private String title;

    @PrimeNumberValidation
    private Long TotalEmp;

    @PasswordValidation
    private String uniquePass;

    @AssertTrue(message = "Department should be active")
    private Boolean isActive;

    @PastOrPresent(message = "created date can not be future date")
    private LocalDate createdAt;
}
