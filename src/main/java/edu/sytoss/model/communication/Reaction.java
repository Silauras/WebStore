package edu.sytoss.model.communication;

import edu.sytoss.model.user.UserAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor

@Table(name = "reaction")
@Entity
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reaction_id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author", nullable = false)
    private UserAccount author;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "commentary", nullable = false)
    private Commentary commentary;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Override
    public String toString() {
        return "Reaction{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", type='" + type + '\'' +
                '}';
    }
}