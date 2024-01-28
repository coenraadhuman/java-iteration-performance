package io.github.coenraadhuman.iteration.performance.configuration;

import io.github.coenraadhuman.iteration.performance.domain.model.Element;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_COLLECTION_HUNDRED;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_COLLECTION_HUNDRED_THOUSAND;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_COLLECTION_MILLION;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_COLLECTION_TEN;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_COLLECTION_TEN_MILLION;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_COLLECTION_TEN_THOUSAND;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_COLLECTION_THOUSAND;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.STREAM_COLLECTIONS;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.STREAM_COLLECTION_HUNDRED;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.STREAM_COLLECTION_HUNDRED_THOUSAND;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.STREAM_COLLECTION_MILLION;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.STREAM_COLLECTION_TEN;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.STREAM_COLLECTION_TEN_MILLION;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.STREAM_COLLECTION_TEN_THOUSAND;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.STREAM_COLLECTION_THOUSAND;

@Configuration
public class StreamCollectionConfiguration {

    @Bean(STREAM_COLLECTION_TEN)
    public Supplier<Stream<Element>> createStreamCollectionTen(
        @Qualifier(LIST_COLLECTION_TEN) final Supplier<List<Element>> collection
    ) {
        return () -> collection.get().stream();
    }

    @Bean(STREAM_COLLECTION_HUNDRED)
    public Supplier<Stream<Element>> createStreamCollectionHundred(
        @Qualifier(LIST_COLLECTION_HUNDRED) final Supplier<List<Element>> collection
    ) {
        return () -> collection.get().stream();
    }

    @Bean(STREAM_COLLECTION_THOUSAND)
    public Supplier<Stream<Element>> createStreamCollectionThousand(
        @Qualifier(LIST_COLLECTION_THOUSAND) final Supplier<List<Element>> collection
    ) {
        return () -> collection.get().stream();
    }

    @Bean(STREAM_COLLECTION_TEN_THOUSAND)
    public Supplier<Stream<Element>> createStreamCollectionTenThousand(
        @Qualifier(LIST_COLLECTION_TEN_THOUSAND) final Supplier<List<Element>> collection
    ) {
        return () -> collection.get().stream();
    }

    @Bean(STREAM_COLLECTION_HUNDRED_THOUSAND)
    public Supplier<Stream<Element>> createStreamCollectionHundredThousand(
        @Qualifier(LIST_COLLECTION_HUNDRED_THOUSAND) final Supplier<List<Element>> collection
    ) {
        return () -> collection.get().stream();
    }

    @Bean(STREAM_COLLECTION_MILLION)
    public Supplier<Stream<Element>> createStreamCollectionMillion(
        @Qualifier(LIST_COLLECTION_MILLION) final Supplier<List<Element>> collection
    ) {
        return () -> collection.get().stream();
    }

    @Bean(STREAM_COLLECTION_TEN_MILLION)
    public Supplier<Stream<Element>> createStreamCollectionTenMillion(
        @Qualifier(LIST_COLLECTION_TEN_MILLION) final Supplier<List<Element>> collection
    ) {
        return () -> collection.get().stream();
    }

    @Bean(STREAM_COLLECTIONS)
    public List<Supplier<Stream<Element>>> parallelStreamCollections(
        @Qualifier(STREAM_COLLECTION_TEN) final Supplier<Stream<Element>> ten,
        @Qualifier(STREAM_COLLECTION_HUNDRED) final Supplier<Stream<Element>> hundred,
        @Qualifier(STREAM_COLLECTION_THOUSAND) final Supplier<Stream<Element>> thousand,
        @Qualifier(STREAM_COLLECTION_TEN_THOUSAND) final Supplier<Stream<Element>> tenThousand,
        @Qualifier(STREAM_COLLECTION_HUNDRED_THOUSAND) final Supplier<Stream<Element>> hundredThousand,
        @Qualifier(STREAM_COLLECTION_MILLION) final Supplier<Stream<Element>> million,
        @Qualifier(STREAM_COLLECTION_TEN_MILLION) final Supplier<Stream<Element>> tenMillion
    ) {
        return List.of(ten, hundred, thousand, tenThousand, hundredThousand, million, tenMillion);
    }

}