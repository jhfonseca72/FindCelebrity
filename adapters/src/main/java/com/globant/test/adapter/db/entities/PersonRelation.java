package com.globant.test.adapter.db.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "PERSON_RELATION")
public class PersonRelation implements Serializable {

    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long ratingId;

    @Column(name = "ID_PERSON")
    private Long idPerson;

    @Column(name = "ID_KNOW")
    private Long idPersonKnow;
}
