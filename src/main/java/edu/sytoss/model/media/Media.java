package edu.sytoss.model.media;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "media_id")
    private Long id;

    @Column(name = "path")
    private String path;

    @Column(name = "name")
    private String name;

    @Column(name = "index")
    private Integer index;

    @Column(name = "media_object", nullable = false)
    private Long mediaObject;
}
