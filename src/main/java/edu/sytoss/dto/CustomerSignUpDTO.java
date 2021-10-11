package edu.sytoss.dto;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomerSignUpDTO {
//    @NotEmpty(message = "Name should not be empty")
//    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    String name;
//    @NotEmpty(message = "Surname should not be empty")
//    @Size(min = 2, max = 30, message = "Surname should be between 2 and 30 characters")
    String surname;
//    @NotEmpty(message = "Username should not be empty")
//    @Size(min = 2, max = 30, message = "Username should be between 2 and 30 characters")
    String username;
//    @NotEmpty(message = "Password should not be empty")
//    @Size(min = 2, max = 30, message = "Password should be between 2 and 30 characters")
    String password;
}
