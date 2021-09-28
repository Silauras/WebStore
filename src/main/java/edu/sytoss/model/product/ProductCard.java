package edu.sytoss.model.product;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.shop.Warehouse;
import edu.sytoss.model.user.Communication;
import edu.sytoss.model.user.Subsciptable;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "product_card")
@Entity
public class ProductCard implements Subsciptable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "shortDescription")
    private String shortDescription;

    @Column(name = "fullDescription")
    private String fullDescription;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_template", nullable = false)
    private ProductTemplate productTemplate;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product")
    private Set<Price> prices;

    @OneToMany
    @JoinColumn(name = "product")
    List<Characteristic> characteristics;

    @OneToMany()
    @JoinColumn(name = "product_card")
    private List<Product> products;

    @Override
    public String toString() {
        return "ProductCards{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", fullDescription='" + fullDescription + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductCard)) return false;

        ProductCard that = (ProductCard) o;

        if (!name.equals(that.name)) return false;
        if (!price.equals(that.price)) return false;
        if (!Objects.equals(shortDescription, that.shortDescription)) return false;
        if (!Objects.equals(fullDescription, that.fullDescription)) return false;
        if (!status.equals(that.status)) return false;
        if (!productTemplate.equals(that.productTemplate)) return false;
        if (!Objects.equals(prices, that.prices)) return false;
        if (!characteristics.equals(that.characteristics)) return false;
        return Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + (shortDescription != null ? shortDescription.hashCode() : 0);
        result = 31 * result + (fullDescription != null ? fullDescription.hashCode() : 0);
        result = 31 * result + status.hashCode();
        result = 31 * result + productTemplate.hashCode();
        result = 31 * result + (prices != null ? prices.hashCode() : 0);
        result = 31 * result + characteristics.hashCode();
        result = 31 * result + (product != null ? product.hashCode() : 0);
        return result;
    }
}