package io.github.coenraadhuman.iteration.performance.runner;

import io.github.coenraadhuman.iteration.performance.common.utility.BubbleSort;
import io.github.coenraadhuman.iteration.performance.domain.model.Element;
import io.github.coenraadhuman.iteration.performance.service.IterationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.ARRAY_SERVICE;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_SERVICE;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.PARALLEL_STREAM_SERVICE;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.STREAM_SERVICE;

@Slf4j
@Component
@RequiredArgsConstructor
public class IterationPerformanceRunner implements CommandLineRunner {

    @Qualifier(ARRAY_SERVICE)
    private final IterationService arrayService;

    @Qualifier(LIST_SERVICE)
    private final IterationService listService;

    @Qualifier(STREAM_SERVICE)
    private final IterationService streamService;

    @Qualifier(PARALLEL_STREAM_SERVICE)
    private final IterationService parallelStreamService;

    @Override
    public void run(final String[] args) {
        var argsList = Arrays.stream(args).toList();
        if (argsList.contains("array")) {
            arrayService.loop(this::run, this::executeOnElement);
        } else if (argsList.contains("list")) {
            listService.loop(this::run, this::executeOnElement);
        } else if (argsList.contains("stream")) {
            streamService.loop(this::run, this::executeOnElement);
        } else if (argsList.contains("parallel")) {
            parallelStreamService.loop(this::run, this::executeOnElement);
        } else {
            log.info("Please indicate which test to run: `array`, `list`, `stream` or `parallel` (stream)");
        }
    }

    private long executeOnElement(final Element element) {
        var characters = element.toString().toCharArray();
        log.debug("Before sorting: {}", characters);
        BubbleSort.sort(characters);
        log.debug("After sorting: {}", characters);
        var total = 0L;
        for (char character : characters) {
            total += character;
        }
        return total;
    }

    private void run(final Supplier<Long> processCollection, final Supplier<String> collectionType) {
        var finishTimes = new ArrayList<Long>();
        var collectionTimer = new StopWatch();

        var runs = 1;
        var runsToComplete = 0;
        var collectionSize = 0L;
        var timeUnit = "μs";

        do {
            collectionTimer.start();
            var calculatedLong = processCollection.get();
            collectionTimer.stop();

            if (runs == 1 && runsToComplete == 0 && collectionSize == 0L) {
                collectionSize = calculatedLong / 2350L;
                runsToComplete = runsPerSize.get(collectionSize);
            }

            if (collectionSize > 10000) {
                finishTimes.add(collectionTimer.getTime(TimeUnit.MILLISECONDS));
                timeUnit = "ms";
            } else {
                finishTimes.add(collectionTimer.getTime(TimeUnit.MICROSECONDS));
                timeUnit = "μs";
            }

            collectionTimer.reset();

            runs++;
        } while (runs <= runsToComplete);

        var averageFinishTime = finishTimes.stream().mapToLong(Long::longValue).average().orElse(0L);
        var averageElementTime = averageFinishTime / collectionSize;

        log.info(
            "{} runs of {}: collection average finish time {} {} with {} elements at an average per item of {} {}",
            collectionType.get(),
            runsToComplete,
            averageFinishTime,
            timeUnit,
            collectionSize,
            averageElementTime,
            timeUnit
        );
    }

    private static final Map<Long, Integer> runsPerSize = Map.of(
        10L, 100000,
        100L, 50000,
        1000L, 5000,
        10000L, 50,
        100000L, 20,
        1000000L, 20,
        10000000L, 10
    );

}