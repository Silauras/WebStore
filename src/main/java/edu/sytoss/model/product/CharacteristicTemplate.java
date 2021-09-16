package edu.sytoss.model.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor


@Table(name = "characteristic_template")
@Entity
public class CharacteristicTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "characteristic_template_id", nullable = false)
    private Long id;

    @Column(name = "`name`")
    private String name;

    @ManyToMany
    @JoinTable(name = "product_template_characteristic_template",
            joinColumns =
            @JoinColumn(name = "characteristic_template", referencedColumnName = "characteristic_template_id"),
            inverseJoinColumns =
            @JoinColumn(name = "product_template", referencedColumnName = "product_template_id")
    )
    private Set<ProductTemplate> productTemplates;

    @OneToMany
    @JoinColumn(name = "template")
    List<Characteristic> characteristics;
}