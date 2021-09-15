package edu.sytoss.model.communication;

import edu.sytoss.model.product.Product;
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

@Entity
@Inheritance
@Table(name = "commentary")
@DiscriminatorColumn(name = "commentary_type")
public class Commentary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentary_id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author", nullable = false)
    private UserAccount author;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "delete_state")
    private Boolean deleteState;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product", nullable = false)
    private Product product;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "root_commentary")
    List<Answer> answers;
}