package edu.sytoss.model.product;

import edu.sytoss.model.shop.Shop;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

/**
 * Composition of product cards that has cost less than all product cards prices separately.
 */

@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "Kit")
public class Kit implements Purchase {
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

    @Override
    public String toString() {
        return "Kit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kit kit = (Kit) o;
        return Objects.equals(id, kit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

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
