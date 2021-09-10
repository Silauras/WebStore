package edu.sytoss.model.shop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor

@Table(name = "promotion")
@Entity
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id", nullable = false)
    private Long id;

    @Column(name = "`name`", nullable = false, length = 50)
    private String name;

    @Column(name = "discount", precision = 10)
    private BigDecimal discount;

    @Column(name = "unit", length = 50)
    private String unit;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "shop", nullable = false)
    private Shop shop;

    @Column(name = "promotion_type", nullable = false, length = 50)
    private String promotionType;
}