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

    @ManyToOne(optional = false)
    @JoinColumn(name = "product", nullable = false)
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "template", nullable = false)
    private CharacteristicTemplate template;

}