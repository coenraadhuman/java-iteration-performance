package io.github.coenraadhuman.iteration.performance.service;

import io.github.coenraadhuman.iteration.performance.domain.model.Element;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface IterationService {

    void loop(BiConsumer<Supplier<Long>, Supplier<String>> executeMainRun, Function<Element, Long> executeOnElement);

}