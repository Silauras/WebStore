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

@Table(name = "message")
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author", nullable = false)
    private UserAccount author;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "sent_date", nullable = false)
    private Date sentDate;

    @Column(name = "read_date")
    private Date readDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "dialog", nullable = false)
    private Dialog dialog;
}