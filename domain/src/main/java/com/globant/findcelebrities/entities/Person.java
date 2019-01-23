package com.globant.findcelebrities.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "peopleKnown")
public class Person {

    private Long id;
    private Set<Person> peopleKnown;

    public static Person buildPerson(Long id) {
        Person person = new Person();
        person.setId(id);
        person.setPeopleKnown(new HashSet<>());
        return person;
    }

    public static Person buildPerson(Long id, Set<Person> peopleKnown) {
        Person person = new Person();
        person.setId(id);
        person.setPeopleKnown(peopleKnown);
        return person;
    }

    @Override
    public String toString() {
        return String.format("%s", this.id);
    }
}
