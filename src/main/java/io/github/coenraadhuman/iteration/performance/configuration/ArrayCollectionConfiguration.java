package io.github.coenraadhuman.iteration.performance.configuration;

import io.github.coenraadhuman.iteration.performance.domain.model.Element;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Supplier;

import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.ARRAY_COLLECTION_HUNDRED;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.ARRAY_COLLECTION_HUNDRED_THOUSAND;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.ARRAY_COLLECTION_MILLION;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.ARRAY_COLLECTION_TEN;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.ARRAY_COLLECTION_TEN_MILLION;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.ARRAY_COLLECTION_TEN_THOUSAND;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.ARRAY_COLLECTION_THOUSAND;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_COLLECTION_HUNDRED;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_COLLECTION_HUNDRED_THOUSAND;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_COLLECTION_MILLION;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_COLLECTION_TEN;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_COLLECTION_TEN_MILLION;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_COLLECTION_TEN_THOUSAND;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_COLLECTION_THOUSAND;

@Configuration
public class ArrayCollectionConfiguration {

    @Bean(ARRAY_COLLECTION_TEN)
    public Supplier<Element[]> createArrayCollectionHundredMillion(
        @Qualifier(LIST_COLLECTION_TEN) final Supplier<List<Element>> collection
    ) {
        return () -> collection.get().toArray(new Element[]{});
    }

    @Bean(ARRAY_COLLECTION_HUNDRED)
    public Supplier<Element[]> createArrayCollectionHundred(
        @Qualifier(LIST_COLLECTION_HUNDRED) final Supplier<List<Element>> collection
    ) {
        return () -> collection.get().toArray(new Element[]{});
    }

    @Bean(ARRAY_COLLECTION_THOUSAND)
    public Supplier<Element[]> createArrayCollectionThousand(
        @Qualifier(LIST_COLLECTION_THOUSAND) final Supplier<List<Element>> collection
    ) {
        return () -> collection.get().toArray(new Element[]{});
    }

    @Bean(ARRAY_COLLECTION_TEN_THOUSAND)
    public Supplier<Element[]> createArrayCollectionTenThousand(
        @Qualifier(LIST_COLLECTION_TEN_THOUSAND) final Supplier<List<Element>> collection
    ) {
        return () -> collection.get().toArray(new Element[]{});
    }

    @Bean(ARRAY_COLLECTION_HUNDRED_THOUSAND)
    public Supplier<Element[]> createArrayCollectionHundredThousand(
        @Qualifier(LIST_COLLECTION_HUNDRED_THOUSAND) final Supplier<List<Element>> collection
    ) {
        return () -> collection.get().toArray(new Element[]{});
    }

    @Bean(ARRAY_COLLECTION_MILLION)
    public Supplier<Element[]> createArrayCollectionMillion(
        @Qualifier(LIST_COLLECTION_MILLION) final Supplier<List<Element>> collection
    ) {
        return () -> collection.get().toArray(new Element[]{});
    }

    @Bean(ARRAY_COLLECTION_TEN_MILLION)
    public Supplier<Element[]> createArrayCollectionTenMillion(
        @Qualifier(LIST_COLLECTION_TEN_MILLION) final Supplier<List<Element>> collection
    ) {
        return () -> collection.get().toArray(new Element[]{});
    }

    @Bean
    public List<Supplier<Element[]>> arrayCollections(
        @Qualifier(ARRAY_COLLECTION_TEN) final Supplier<Element[]> ten,
        @Qualifier(ARRAY_COLLECTION_HUNDRED) final Supplier<Element[]> hundred,
        @Qualifier(ARRAY_COLLECTION_THOUSAND) final Supplier<Element[]> thousand,
        @Qualifier(ARRAY_COLLECTION_TEN_THOUSAND) final Supplier<Element[]> tenThousand,
        @Qualifier(ARRAY_COLLECTION_HUNDRED_THOUSAND) final Supplier<Element[]> hundredThousand,
        @Qualifier(ARRAY_COLLECTION_MILLION) final Supplier<Element[]> million,
        @Qualifier(ARRAY_COLLECTION_TEN_MILLION) final Supplier<Element[]> tenMillion
    ) {
        return List.of(ten, hundred, thousand, tenThousand, hundredThousand, million, tenMillion);
    }

}