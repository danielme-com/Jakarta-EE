package com.danielme.jakartaee.test;

import org.junit.jupiter.api.*;

import java.util.logging.Logger;

public class JupiterLifeCycleAnnotationsTest {

    private final static Logger log = Logger.getLogger(JupiterLifeCycleAnnotationsTest.class.getName());

    @BeforeAll
    static void beforeAll() {
        log.info("se ejecuta una única vez y antes de ejecutarse el primer test de la clase");
    }

    @AfterAll
    static void afterAll() {
        log.info("se ejecuta después de haberse ejecutado todos los tests");
    }

    @BeforeEach
    void beforeEach() {
        log.info("    se ejecuta antes de ejecutarse cada test");
    }

    @AfterEach
    void afterEach() {
        log.info("    se ejecuta despues de cada test");
    }

    @Test
    void testFake1() {
        log.info("        test");
    }

    @Test
    void testFake2() {
        log.info("        test 2");
    }

}
