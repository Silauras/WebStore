
package edu.sytoss.model.order;

import edu.sytoss.model.product.Product;
import edu.sytoss.model.shop.Shop;
import edu.sytoss.model.user.UserAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "`order`")
@Entity
public class Order {

    public Order(UserAccount customer, Shop seller) {
        this.userAccount = customer;
        this.seller = seller;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer", nullable = false)
    private UserAccount userAccount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "seller", nullable = false)
    private Shop seller;

    @Column(name = "last_change_date", nullable = false)
    private Date lastChangeDate;

    @Column(name = "state", nullable = false, length = 50)
    private String state;

    @OneToMany()
    @JoinColumn(name = "order")
    private List<Product> products;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", lastChangeDate=" + lastChangeDate +
                ", state='" + state + '\'' +
                '}';
    }
}
