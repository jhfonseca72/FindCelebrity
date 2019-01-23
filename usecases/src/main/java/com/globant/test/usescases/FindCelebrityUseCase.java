package com.globant.test.usescases;

import com.globant.findcelebrities.entities.Person;
import com.globant.test.annotations.UseCase;
import com.globant.test.exceptions.CelebrityNotFound;
import com.globant.test.gateways.IPeopleGateway;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Use case to apply the algorithm to find a celebrity in a group of people
 */
@UseCase
public final class FindCelebrityUseCase {

    /**
     * Injects
     */
    private final IPeopleGateway repository;
    private final String approach;

    private static final String FUNCTIONAL_APPROACH = "Functional";

    /**
     * Constructor with class parameter which it will be injected by Spring
     *
     * @param repository
     */
    public FindCelebrityUseCase(IPeopleGateway repository, String approach) {
        this.repository = repository;
        this.approach = approach;
    }

    /**
     * Determine which approach can use depending the value on the variable
     * approach that is in the configuration file
     *
     * @return if no exist a celebirty in the team the vale
     * will be {@code null} else the value is {@link Person}
     */
    public final Person findCelebrity() throws CelebrityNotFound {

        List<Person> people = repository.getPeople();
        Optional<Person> person;

        if (FUNCTIONAL_APPROACH.equals(this.approach)) {
            person = this.functionalFindCelebrity(people);
        } else {
            person = this.imperativeFindCelebrity(people);
        }

        return person.orElseThrow(() -> CelebrityNotFound.CelebrityMessage.CELEBRITY_NOT_FOUND.build());
    }

    /**
     * Functional Approach to resolve the celebrity problem
     *
     * @return {@link Person} Person found that fulfill the celebrity condition
     */
    private static final Optional<Person> functionalFindCelebrity(final List<Person> people) {

        List<Person> candidates = people.stream().filter(person ->
                person.getPeopleKnown().isEmpty()).collect(Collectors.toList());

        if (candidates.size() == people.size()) {
            if (candidates.size() > 1) {
                return Optional.empty();
            } else {
                return Optional.of(candidates.get(0));
            }
        }

        List<Person> rest = people.stream().filter(person ->
                !person.getPeopleKnown().isEmpty()).collect(Collectors.toList());

        return candidates.stream().filter(candidate ->
                rest.stream().allMatch(person -> person.getPeopleKnown().contains(candidate))).findAny();
    }

    /**
     * If A knows B, then A can’t be celebrity. Discard A, and B may be celebrity.
     * If A doesn’t know B, then B can’t be celebrity. Discard B, and A may be celebrity.
     * Repeat above two steps till we left with only one person.
     * Ensure the remained person is celebrity. (Why do we need this step?)
     *
     * @param people
     * @return
     */
    private static final Optional<Person> imperativeFindCelebrity(final List<Person> people) {

        Person a = people.get(0);

        int i = 1;
        while (i < people.size()) {
            a = knows(a, people.get(i++));
        }

        if (!a.getPeopleKnown().isEmpty()) {
            return Optional.empty();
        }

        for (Person person : people) {
            if (person != a && !person.getPeopleKnown().contains(a)) {
                return Optional.empty();
            }
        }

        return Optional.of(a);
    }

    /**
     * Function to validate if a {@link Person} A knows to another {@link Person} B
     * this validation is achieved searching into {@link Person#peopleKnown}
     *
     * @param a Represents the person in which will be done the search
     * @param b Represents the reference to search into a
     * @return {@link Person} If A knows B, then A can’t be celebrity. Discard A, and B may be celebrity.
     * If A doesn’t know B, then B can’t be celebrity. Discard B, and A may be celebrity.
     */
    private static Person knows(Person a, Person b) {
        if (a.getPeopleKnown().contains(b)) {
            return b;
        } else {
            return a;
        }
    }
}
