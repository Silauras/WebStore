package edu.sytoss.model.product;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.shop.Warehouse;
import edu.sytoss.model.user.Communication;
import edu.sytoss.model.user.Subsciptable;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
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
    private List<Price> prices;

    @OneToMany
    @JoinColumn(name = "product")
    List<Characteristic> characteristics;

    @OneToMany()
    @JoinColumn(name = "product_card")
    private List<Product> product;

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
}