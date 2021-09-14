package edu.sytoss.model.user;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor

@Entity
@Table(name = "subscription")
public class Subscription {
    @Id
    @Column(name = "subscription_id", nullable = false)
    private Long id;

    @Column(name = "name_subscription")
    private String name_subscription;

    @Column(name = "subscription_type")
    private String subscription_type;

    @Column(name = "subscription_object")
    private String subscription_object;

    @Column(name = "information")
    private String information;

}
