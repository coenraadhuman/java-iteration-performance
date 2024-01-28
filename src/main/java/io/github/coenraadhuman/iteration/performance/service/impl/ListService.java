package io.github.coenraadhuman.iteration.performance.service.impl;

import io.github.coenraadhuman.iteration.performance.domain.model.Element;
import io.github.coenraadhuman.iteration.performance.service.IterationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_SERVICE;

@Service(LIST_SERVICE)
@RequiredArgsConstructor
public class ListService implements IterationService {

    private final List<Supplier<List<Element>>> listCollections;

    /**
     * Observing the effects of the List data structure on loop performance:
     *
     * @param executeOnElement same execution passed to all collections to iterate over.
     */
    @Override
    public void loop(BiConsumer<Supplier<Long>, Supplier<String>> executeMainRun,
                     Function<Element, Long> executeOnElement
    ) {
        listCollections.forEach((collection) -> {
            executeMainRun.accept(() -> {
                    var total = 0L;

                    for (Element element : collection.get()) {
                        total += executeOnElement.apply(element);
                    }

                    return total;
                },
                () -> "ArrayList<Element>"
            );
        });
    }

}