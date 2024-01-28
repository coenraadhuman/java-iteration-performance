package io.github.coenraadhuman.iteration.performance.service.impl;

import io.github.coenraadhuman.iteration.performance.domain.model.Element;
import io.github.coenraadhuman.iteration.performance.service.IterationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.STREAM_COLLECTIONS;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.STREAM_SERVICE;

@Service(STREAM_SERVICE)
@RequiredArgsConstructor
public class StreamService implements IterationService {

    @Qualifier(STREAM_COLLECTIONS)
    private final List<Supplier<Stream<Element>>> streamCollections;

    @Override
    public void loop(
        final BiConsumer<Supplier<Long>, Supplier<String>> executeMainRun,
        final Function<Element, Long> executeOnElement
    ) {
        streamCollections.forEach((collection) ->
            executeMainRun.accept(
                () -> collection.get().map(executeOnElement).reduce(0L, Long::sum),
                () -> "Stream<Element>"
            )
        );
    }

}