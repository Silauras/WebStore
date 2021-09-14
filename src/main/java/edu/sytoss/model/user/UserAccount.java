package edu.sytoss.model.user;

import edu.sytoss.model.communication.Claim;
import edu.sytoss.model.communication.Reaction;
import edu.sytoss.model.order.Order;
import edu.sytoss.model.shop.Shop;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sun.misc.resources.Messages;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "user_account")
public class UserAccount {
    public UserAccount(String name, String surname, String patronymic, String login, String password, Date registrationDate, Date lastActivityDate, String role) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.login = login;
        this.password = password;
        this.registrationDate = registrationDate;
        this.lastActivityDate = lastActivityDate;
        this.role = role;
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
    @ManyToOne
    @JoinColumn(name = "communication_communication_id")
    private Communication communication;
    @ManyToOne
    @JoinColumn(name = "shop_shop_id")
    private Shop shop;
    private Set<Messages> messages;
    private Set<Order> orders;
    private Set<Reaction> reactions;
    private Set<Subscription> subscriptions;
    private Set<Claim> complaintsSent;
    //private Set<UserAccountClaim> complaints;

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
