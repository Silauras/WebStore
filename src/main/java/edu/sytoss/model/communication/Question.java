package edu.sytoss.model.communication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor

@Entity
@DiscriminatorValue("question")
public class Question extends Commentary{
}
