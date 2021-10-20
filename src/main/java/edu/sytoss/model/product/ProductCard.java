package edu.sytoss.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class ProductCard {
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
    private Set<Sale> sales;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product")
    List<Characteristic> characteristics;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_card")
    private List<Product> products;

    @ManyToMany
    @JoinTable(name = "kit_product_card",
            joinColumns = @JoinColumn(name = "product_card", referencedColumnName = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "kit", referencedColumnName = "kit_id"))
    private Set<Kit> kits;
    @Override
    public String toString() {
        return "ProductCards{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCard that = (ProductCard) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}