package edu.sytoss.model.product;

import edu.sytoss.model.shop.Shop;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Composition of product cards that has cost less than all product cards prices separately.
 */

@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "Kit")
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
            joinColumns = @JoinColumn(name = "kit", referencedColumnName = "kit_id"),
            inverseJoinColumns = @JoinColumn(name = "product_card", referencedColumnName = "product_id"))
    private Set<ProductCard> productCards;


    /**
     * Returns product card with maximum price (without any sale) in kit
     *
     * @return most expensive product card in kit
     * @author Oleg Pensov
     */
    public ProductCard getMostExpensiveProduct() {
        BigDecimal maxPrice = BigDecimal.ZERO;
        ProductCard mostExpensive = new ProductCard();
        for (ProductCard productCard : this.productCards) {
            if (maxPrice.compareTo(productCard.getPrice()) < 0) {
                maxPrice = productCard.getPrice();
                mostExpensive = productCard;
            }
        }
        return mostExpensive;
    }
}
