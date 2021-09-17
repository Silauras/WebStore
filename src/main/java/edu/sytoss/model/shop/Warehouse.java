package edu.sytoss.model.shop;

import javax.persistence.*;

import edu.sytoss.model.product.ProductCard;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

@Table(name = "warehouse")
@Entity
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id", nullable = false)
    private Long id;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "street", nullable = false, length = 50)
    private String street;

    @Column(name = "number", nullable = false, length = 50)
    private String number;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", nullable = false)
    private Shop owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "warehouse_product",
            joinColumns =
            @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id"),
            inverseJoinColumns =
            @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    )
    Set<ProductCard> productCards;
}