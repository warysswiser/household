package com.warys.app.household.domain.shopping.repository;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface SearchByPredicate<T> {

    List<T> list();

    default Set<T> search(Predicate<T> predicate) {
        return list().stream().filter(predicate).collect(Collectors.toSet());
    }
}
