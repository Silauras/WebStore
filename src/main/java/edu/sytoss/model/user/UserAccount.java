package edu.sytoss.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "user_account")
public class UserAccount {
    public UserAccount(String name, String surname, String patronymic, String login, String password, Date registrationDate,Date lastActivityDate, String role) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.login = login;
        this.password = password;
        this.registrationDate  = registrationDate;
        this.lastActivityDate  = lastActivityDate;
        this.role  = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_account_id", nullable = false)
    private Long id;

    @Column(name = "`name`", nullable = false, length = 50)
    private String name;

    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    @Column(name = "patronymic", length = 50)
    private String patronymic;

    @Column(name = "login", nullable = false, length = 50)
    private String login;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "registration_date", nullable = false)
    private Date registrationDate;

    @Column(name = "last_activity_date", nullable = false)
    private Date lastActivityDate;

    @Column(name = "`role`", nullable = false, length = 50)
    private String role;

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", registrationDate=" + registrationDate +
                ", lastActivityDate=" + lastActivityDate +
                ", role='" + role + '\'' +
                '}';
    }
}
