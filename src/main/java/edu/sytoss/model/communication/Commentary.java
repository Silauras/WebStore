package edu.sytoss.model.communication;

import edu.sytoss.model.product.Product;
import edu.sytoss.model.user.UserAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "commentary")
@Entity
public class Commentary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentary_id", nullable = false)
    private Long id;

    @Column(name = "commentary_type", nullable = false, length = 50)
    private String commentaryType;

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

    @ManyToOne
    @JoinColumn(name = "root_commentary")
    private Commentary rootCommentary;

    @Column(name = "rating")
    private Integer rating;


}