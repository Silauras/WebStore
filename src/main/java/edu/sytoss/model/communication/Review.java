package edu.sytoss.model.communication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Getter
@Setter
@NoArgsConstructor

@Entity
@DiscriminatorValue("review")
public class Review extends Commentary {
    @Column(name = "rating")
    @Transient
    private Integer rating;
}
