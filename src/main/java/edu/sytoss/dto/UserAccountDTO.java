package edu.sytoss.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDTO {
    private Long id;
    private String fullName;
    private String login;
    private String role;

    public UserAccountDTO(Long id) {
        this.id = id;
    }

    public UserAccountDTO(String fullName) {
        String[] NameSurname = fullName.trim().split(" ");
        this.fullName = NameSurname[0];
    }
}

