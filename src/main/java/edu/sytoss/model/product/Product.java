package edu.sytoss.model.product;

import edu.sytoss.model.shop.Warehouse;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "product")
@Entity
public class Product {
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