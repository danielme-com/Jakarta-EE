package com.danielme.jakartaee.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ComparatorsTest {

    @Test
    @DisplayName("probando naturalOrder con enteros en JUnit 5 y assert de Jupiter")
    void testComparatorLessThanAssertJupiter() {
        Comparator<Integer> integerComparator = Comparator.naturalOrder();

        int result = integerComparator.compare(1, 2);

        assertTrue(result < 0, "el resultado no es menor que cero");
    }

    @Test
    @DisplayName("probando naturalOrder con enteros en JUnit 5 y AssertJ")
    void testComparatorLessThanAssertJ() {
        Comparator<Integer> integerComparator = Comparator.naturalOrder();

        int result = integerComparator.compare(1, 2);

        assertThat(result)
                .isLessThan(0);
    }

    @Test
    @DisplayName("ejemplo de lambdas en assertAll")
    void testOrderStringAssertJupiter() {
        Comparator<String> stringComparator = Comparator.naturalOrder();
        List<String> names = new ArrayList<>(3);
        names.add("Daniel");
        names.add("Juan");
        names.add("Antonio");

        names.sort(stringComparator);

        assertAll("el orden de los nombres es incorrecto",
                () -> assertEquals("Antonio", names.get(0)),
                () -> assertEquals("Daniel", names.get(1)),
                () -> assertEquals("Juan", names.get(2)));
    }

    @Test
    @DisplayName("ejemplo de colección con AssertJ")
    void testOrderStringAssertJ() {
        Comparator<String> stringComparator = Comparator.naturalOrder();
        List<String> names = new ArrayList<>(3);
        names.add("Daniel");
        names.add("Juan");
        names.add("Antonio");

        names.sort(stringComparator);

        assertThat(names)
                .containsExactly("Antonio", "Daniel", "Juan");
    }

}
