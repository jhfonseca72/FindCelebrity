package com.globant.test.adapter.db.gateway;

import com.globant.findcelebrities.entities.Person;
import com.globant.test.adapter.db.entities.PersonData;
import com.globant.test.adapter.db.repository.IPersonRelationRepository;
import com.globant.test.adapter.db.repository.IPersonRepository;
import com.globant.test.gateways.IPeopleGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DataBaseMemoryAdapter implements IPeopleGateway {

    private final IPersonRepository personRepository;
    private final IPersonRelationRepository relationRepository;

    /**
     * @param personRepository
     * @param relationRepository
     */
    @Autowired
    public DataBaseMemoryAdapter(IPersonRepository personRepository,
                                 IPersonRelationRepository relationRepository) {
        this.personRepository = personRepository;
        this.relationRepository = relationRepository;
    }

    /**
     * @return
     */
    @Override
    public List<Person> getPeople() {

        List<Person> people = StreamSupport.stream(this.personRepository.findAll().spliterator(), false)
                .map(this::simplePersonMapper).collect(Collectors.toList());

        this.relationRepository.findAll().forEach(personData -> {
            Person personKnow = people.stream()
                    .filter(person -> Objects.equals(person.getId(), personData.getIdPerson())).findFirst().get();
            Person candidate = people.stream()
                    .filter(person -> Objects.equals(person.getId(), personData.getIdPersonKnow())).findFirst().get();
            personKnow.getPeopleKnown().add(candidate);
        });
        return people;
    }

    /**
     * @param data
     * @return
     */
    private Person simplePersonMapper(PersonData data) {
        return Person.buildPerson(data.getId());
    }
}
