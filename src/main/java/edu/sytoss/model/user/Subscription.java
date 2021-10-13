package edu.sytoss.model.user;

import edu.sytoss.model.product.ProductCard;
import edu.sytoss.model.shop.Shop;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "subscription")
public class
Subscription {
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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductCard productCard;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "subscription_subscribers",
            inverseJoinColumns =
            @JoinColumn(name = "subscriber_id", referencedColumnName = "user_account_id"),
            joinColumns =
            @JoinColumn(name = "subscription_id", referencedColumnName = "subscription_id")
    )
    private Set<UserAccount> userAccount;

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", name_subscription='" + name_subscription + '\'' +
                ", information='" + information + '\'' +
                '}';
    }
}
