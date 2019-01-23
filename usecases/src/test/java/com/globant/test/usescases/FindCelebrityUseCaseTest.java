package com.globant.test.usescases;

import com.globant.findcelebrities.entities.Person;
import com.globant.test.exceptions.CelebrityNotFound;
import com.globant.test.gateways.IPeopleGateway;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
@DisplayName("Find celebrity algorithm")
public class FindCelebrityUseCaseTest {

    @InjectMocks
    FindCelebrityUseCase useCase;

    @Mock
    IPeopleGateway repository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("room where nobody is known")
    public void shouldReturnAException() {
        when(repository.getPeople()).thenReturn(roomWhereAnyKnow());
        Assertions.assertThatThrownBy(() -> useCase.findCelebrity())
                .hasMessage(CelebrityNotFound.CelebrityMessage.CELEBRITY_NOT_FOUND.getMessage());
    }

    /**
     * @param people
     * @param expeted
     */
    @DisplayName("room with one celebrity")
    @ParameterizedTest(name = "\"{0}\" should be {1}")
    @MethodSource({"roomWithOneCelebrity"})
    public void shouldReturnACelebrity(List<Person> people, Long expeted) throws CelebrityNotFound {
        when(repository.getPeople()).thenReturn(people);
        Assertions.assertThat(useCase.findCelebrity().getId()).isEqualTo(expeted);
    }

    /**
     * @param people
     */
    @DisplayName("room where everybody knows but nobody is a celebrity")
    @ParameterizedTest(name = "\"{0}\" should throw an exception")
    @MethodSource("everyBodyKnows")
    public void shouldThrowAnException(final List<Person> people) {
        when(repository.getPeople()).thenReturn(people);
        Assertions.assertThatThrownBy(() -> useCase.findCelebrity())
                .hasMessage(CelebrityNotFound.CelebrityMessage.CELEBRITY_NOT_FOUND.getMessage());
    }

    /**
     * @param people
     */
    @DisplayName("Functional Find Celebrity")
    @ParameterizedTest(name = "\"{0}\" should throw an Exception CelebrityNotFound")
    @CsvSource(delimiter = ';', value = {"1_2_3_4_5", "6_7_8", "0_2", "5_9_3"})
    public void shouldReturnAnException(@ConvertWith(StringToGraph.class) List<Person> people) {
        when(repository.getPeople()).thenReturn(people);
        Assertions.assertThatThrownBy(() -> useCase.findCelebrity())
                .hasMessage(CelebrityNotFound.CelebrityMessage.CELEBRITY_NOT_FOUND.getMessage());
    }


    /**
     * @return
     */
    private static Stream<Arguments> everyBodyKnows() {

        Person p1 = Person.buildPerson(1L);
        Person p2 = Person.buildPerson(2L);
        Person p3 = Person.buildPerson(3L);
        Person p4 = Person.buildPerson(4L);

        p1.setPeopleKnown(new HashSet<>(Arrays.asList(p2)));
        p2.setPeopleKnown(new HashSet<>(Arrays.asList(p4)));
        p3.setPeopleKnown(new HashSet<>(Arrays.asList(p1)));
        p4.setPeopleKnown(new HashSet<>(Arrays.asList(p3)));

        return Stream.of(
                Arguments.of(Arrays.asList(p1, p2, p3, p4))
        );
    }

    /**
     * @return
     */
    private static Stream<Arguments> roomWithOneCelebrityTwo() {

        Person p1 = Person.buildPerson(1L);
        Person p2 = Person.buildPerson(2L);
        Person p3 = Person.buildPerson(3L, Collections.emptySet());
        Person p4 = Person.buildPerson(4L, Collections.emptySet());

        p1.setPeopleKnown(new HashSet<>(Arrays.asList(p2, p4, p3)));
        p2.setPeopleKnown(new HashSet<>(Arrays.asList(p1, p4, p3)));

        return Stream.of(
                Arguments.of(Arrays.asList(p1, p2, p3, p4), null)
        );
    }

    /**
     * @return
     */
    private static Stream<Arguments> roomWithOneCelebrity() {

        Person p1 = Person.buildPerson(1L);
        Person p2 = Person.buildPerson(2L);
        Person p3 = Person.buildPerson(3L);
        Person p4 = Person.buildPerson(4L);

        p1.setPeopleKnown(new HashSet<>(Arrays.asList(p3, p3)));
        p2.setPeopleKnown(new HashSet<>(Arrays.asList(p3)));
        p3.setPeopleKnown(Collections.emptySet());
        p4.setPeopleKnown(new HashSet<>(Arrays.asList(p3)));

        return Stream.of(
                Arguments.of(Arrays.asList(p1, p2, p3, p4), "3")
        );
    }

    /**
     * @return
     */
    private static List<Person> roomWhereAnyKnow() {
        Person p1 = Person.buildPerson(1L);
        Person p2 = Person.buildPerson(2L);
        Person p3 = Person.buildPerson(3L);
        Person p4 = Person.buildPerson(4L);

        return Arrays.asList(p1, p2, p3, p4);
    }

    /**
     *
     */
    private static class StringToGraph implements ArgumentConverter {
        @Override
        public Object convert(Object source, ParameterContext context)
                throws ArgumentConversionException {
            return Arrays.stream(source.toString().split("_"))
                    .map(name -> Person.buildPerson(Long.parseLong(name))).collect(Collectors.toList());
        }
    }
}
