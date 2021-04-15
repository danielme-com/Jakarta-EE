package com.danielme.jakartaee.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Ejemplos de aserciones para excepciones")
public class ExceptionAssertionTest {

    @Test
    @DisplayName("ejemplo de assert de excepción con Jupiter")
    void testExceptionAssertJupiter() {
        List<String> strings = new ArrayList<>(0);

        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> strings.get(0));
        assertEquals("Index 0 out of bounds for length 0", exception.getMessage());
    }

    @Test
    @DisplayName("ejemplo de assert de excepción con AssertJ")
    void testExceptionAssertJ() {
        List<String> strings = new ArrayList<>(0);

        assertThatExceptionOfType(IndexOutOfBoundsException.class)
                .isThrownBy(() -> strings.get(0))
                .withMessage("Index 0 out of bounds for length 0");

    }

}
