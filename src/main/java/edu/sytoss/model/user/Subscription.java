package edu.sytoss.model.user;

import edu.sytoss.model.product.Product;
import edu.sytoss.model.shop.Shop;
import lombok.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

}
