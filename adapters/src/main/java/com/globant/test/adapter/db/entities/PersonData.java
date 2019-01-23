package com.globant.test.adapter.db.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "PERSON")
public class PersonData implements Serializable {

    @Id
    @Column(name = "ID")
    private Long id;
}
