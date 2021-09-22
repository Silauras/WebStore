package edu.sytoss.model.product;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.shop.Warehouse;
import edu.sytoss.model.user.Communication;
import edu.sytoss.model.user.Subsciptable;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "product")
@Entity
@Embeddable// Определяет класс, экземпляры которого хранятся как неотъемлемая часть исходного объекта
public class ProductCard implements Subsciptable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Lob
    @Column(name = "shortDescription")
    private String shortDescription;

    @Lob
    @Column(name = "fullDescription")
    private String fullDescription;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_template", nullable = false)
    private ProductTemplate productTemplate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "warehouse_product",
            joinColumns =
            @JoinColumn(name = "product_id", referencedColumnName = "product_id"),
            inverseJoinColumns =
            @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id")
    )
    private Set<Warehouse> warehouseSet;

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
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", fullDescription='" + fullDescription + '\'' +
                ", status='" + status + '\'' +
                ", productTemplate=" + productTemplate +
                ", warehouseSet=" + warehouseSet +
                '}';
    }
}