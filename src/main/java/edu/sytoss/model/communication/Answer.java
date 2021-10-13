package edu.sytoss.model.communication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor

@Entity
@DiscriminatorValue("answer")
public class Answer extends Commentary{
    @ManyToOne
    @JoinColumn(name = "root_commentary", nullable = false)
    private Commentary rootCommentary;
}
