package com.warys.app.household.domain.shopping.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

class SearchByPredicateShould {

    private final SearchByPredicate<String> tested = () -> List.of("first", "second", "third", "fourth", "fifth", "last");

    @Test
    void retrieve_value_according_to_given_predicate() {
        Predicate<String> predicate = s -> s.startsWith("f");

        Set<String> actual = tested.search(predicate);

        assertThat(actual).containsOnly("first", "fourth", "fifth");
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second", "third", "fourth", "fifth", "last"})
    void retrieve_value_that_is_equal_to(String value) {
        Predicate<String> predicate = s -> s.equals(value);

        Set<String> actual = tested.search(predicate);

        assertThat(actual).containsOnly(value);
    }

}