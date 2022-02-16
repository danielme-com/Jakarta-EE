package com.danielme.jakartaee.jpa;

import com.danielme.jakartaee.jpa.dto.ExpenseSummaryDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public final class ExpenseSummaryAssert {

    private ExpenseSummaryAssert() {
    }

    public static void assertSummary(List<ExpenseSummaryDTO> summaries) {
        assertThat(summaries)
                .extracting("concept")
                .containsExactly("Lunch menu", "movies", "Full tank",
                        "Netflix", "HBO", "vegetables");
    }
}
