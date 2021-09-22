package edu.sytoss.model.product;

import edu.sytoss.model.shop.Warehouse;
import edu.sytoss.model.user.Subsciptable;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "product")
@Entity
@Embeddable// Определяет класс, экземпляры которого хранятся как неотъемлемая часть исходного объекта
public class Product implements Subsciptable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;

    @Column(name = "serial_number", nullable = false, length = 50)
    private long serialNumber;

    @Column(name = "price")
    private Price price;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", serialNumber=" + serialNumber +
                ", price=" + price +
                '}';
    }
}