
package edu.sytoss.model.order;

import edu.sytoss.model.shop.Shop;
import edu.sytoss.model.user.UserAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "`order`")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer", nullable = false)
    private UserAccount userAccount;

   /* @ManyToOne(optional = false)
    @JoinColumn(name = "seller", nullable = false)*/
    @Transient
    private Shop seller;

    @Column(name = "last_change_date", nullable = false)
    private Date lastChangeDate;

    @Column(name = "state", nullable = false, length = 50)
    private String state;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", lastChangeDate=" + lastChangeDate +
                ", state='" + state + '\'' +
                '}';
    }
}
