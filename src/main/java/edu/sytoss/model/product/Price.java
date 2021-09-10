package edu.sytoss.model.product;

import edu.sytoss.model.shop.Promotion;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "price")


@Entity
public class Price{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_id", nullable = false)
    private Long id;

    @Column(name = "price_type", nullable = false)
    private String priceType;

    @Column(name = "unit", nullable = false, length = 20)
    private String unit;

    @Column(name = "value", nullable = false, precision = 10)
    private BigDecimal value;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product", nullable = false)
    private Product product;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion")
    private Promotion promotion;
}