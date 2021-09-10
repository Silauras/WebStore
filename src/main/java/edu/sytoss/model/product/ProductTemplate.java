package edu.sytoss.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "product_template")
@Entity
public class ProductTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_template_id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category", nullable = false)
    private Category category;

}