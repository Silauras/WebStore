package edu.sytoss.model.communication;

import edu.sytoss.model.user.UserAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Table(name = "claim")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_id", nullable = false)
    private Long id;

    @Column(name = "claim_type", nullable = false, length = 50)
    private String claimType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author", nullable = false)
    private UserAccount author;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "close_date")
    private Date closeDate;

    @Column(name = "claim_object", nullable = false)
    private Long claimObject;
}
