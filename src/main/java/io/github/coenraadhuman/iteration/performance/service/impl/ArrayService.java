package io.github.coenraadhuman.iteration.performance.service.impl;

import io.github.coenraadhuman.iteration.performance.domain.model.Element;
import io.github.coenraadhuman.iteration.performance.service.IterationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.ARRAY_SERVICE;

@Service(ARRAY_SERVICE)
@RequiredArgsConstructor
public class ArrayService implements IterationService {

    private final List<Supplier<Element[]>> arrayCollections;

    /**
     * Observing how the loop performance is of an array of elements:
     *
     * @param executeOnElement same execution passed to all collections to iterate over.
     */
    @Override
    public void loop(BiConsumer<Supplier<Long>, Supplier<String>> executeMainRun,
                     Function<Element, Long> executeOnElement
    ) {
        arrayCollections.forEach((collection) ->
            executeMainRun.accept(() -> {
                    var total = 0L;

                    for (Element element : collection.get()) {
                        total += executeOnElement.apply(element);
                    }

                    return total;
                },
                () -> "Element[]"
            )
        );
    }

}