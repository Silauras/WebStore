package edu.sytoss.model.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Table(name = "characteristic")
@Entity
public class Characteristic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "characteristic_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "value", nullable = false, length = 50)
    private String value;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product", nullable = false)
    private ProductCard productCard;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "template", nullable = false)
    private CharacteristicTemplate template;

}