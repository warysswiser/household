package com.warys.app.household;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class DefaultFixture {
    public static final String DEFAULT_INDEX = String.valueOf(Integer.MAX_VALUE);

    protected static <T> List<T> lists(int count, Function<String, T> mapper) {
        return IntStream
                .range(0, count)
                .mapToObj(String::valueOf)
                .map(mapper)
                .collect(Collectors.toList());
    }
}
