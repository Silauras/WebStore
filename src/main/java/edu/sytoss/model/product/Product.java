package edu.sytoss.model.product;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.shop.Warehouse;
import edu.sytoss.model.user.Subsciptable;
import edu.sytoss.model.user.UserAccount;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "product")
@Entity
@Embeddable// Определяет класс, экземпляры которого хранятся как неотъемлемая часть исходного объекта
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;

    @Column(name = "serial_number", nullable = false, length = 50)
    private long serialNumber;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "warehouse", nullable = false)
    Warehouse warehouse;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_card", nullable = false)
    private ProductCard productCard;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order", nullable = false)
    private Order order;

    @Column(name = "status")
    private String status;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", serialNumber=" + serialNumber +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}