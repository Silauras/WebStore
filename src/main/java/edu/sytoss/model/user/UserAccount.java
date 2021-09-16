package edu.sytoss.model.user;

import edu.sytoss.model.communication.Message;
import edu.sytoss.model.communication.Reaction;
import edu.sytoss.model.order.Order;
import edu.sytoss.model.shop.Shop;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "user_account")
public class UserAccount {


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


    @OneToMany
    @JoinColumn(name = "user_account")
    private Set<Communication> communication;

    /* @ManyToOne
     @JoinTable(name = "seller_shop")*/
    @Transient
    private Shop shop;

    @OneToMany
    @JoinColumn(name = "author")
    private Set<Message> messages;

    @OneToMany
    @JoinColumn(name = "customer")
    private Set<Order> orders;

    @OneToMany
    @JoinColumn(name = "author")
    private Set<Reaction> reactions;

    @ManyToMany
    @JoinTable(name = "subscription_subscribers",
            joinColumns =
            @JoinColumn(name = "subscriber_id", referencedColumnName = "user_account_id"),
            inverseJoinColumns =
            @JoinColumn(name = "subscription_id", referencedColumnName = "subscription_id")
    )
    private Set<Subscription> subscriptions;

    public UserAccount(Long userAccountId) {
        this.id = userAccountId;
    }

    public UserAccount(String surnameNameLogin) {
        String[] surnameName = surnameNameLogin.split(" ");
        int countWolds = surnameName.length;
        if (countWolds == 2) {
            this.surname = surnameName[0];
            this.name = surnameName[1];
        } else if (surnameNameLogin.startsWith("$")) {
            this.role=surnameNameLogin.substring(1);
        } else if (surnameNameLogin.startsWith("@")) {
            this.login = surnameNameLogin.substring(1);
        } else {
            this.surname = surnameNameLogin;
            this.name = surnameNameLogin;
        }
    }

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
