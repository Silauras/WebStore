package edu.sytoss.model.product;

import edu.sytoss.model.shop.Shop;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "Kit")


@Entity
public class Kit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "kit_id", nullable = false)
    private Long id;

    @Column(name = "`name`")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "shop")
    private Shop shop;

    @ManyToMany
    @JoinTable(name = "kit_product_card",
            joinColumns =
            @JoinColumn(name = "kit", referencedColumnName = "kit_id"),
            inverseJoinColumns =
            @JoinColumn(name = "product_card", referencedColumnName = "product_id")
    )
    private Set<ProductCard> productCard;

}
