package io.github.coenraadhuman.iteration.performance.configuration;

import io.github.coenraadhuman.iteration.performance.domain.model.Element;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_COLLECTION_HUNDRED;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_COLLECTION_HUNDRED_THOUSAND;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_COLLECTION_MILLION;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_COLLECTION_TEN;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_COLLECTION_TEN_MILLION;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_COLLECTION_TEN_THOUSAND;
import static io.github.coenraadhuman.iteration.performance.common.constant.BeanConstant.LIST_COLLECTION_THOUSAND;

@Configuration
public class ListCollectionConfiguration {

    private static final String ELEMENT_UUID = "f745a38c-8dc9-11ee-b9d1-0242ac120002";


    @Bean(LIST_COLLECTION_TEN)
    public Supplier<List<Element>> createListCollectionTen() {
        return createList(10);
    }

    @Bean(LIST_COLLECTION_HUNDRED)
    public Supplier<List<Element>> createListCollectionHundred() {
        return createList(100);
    }

    @Bean(LIST_COLLECTION_THOUSAND)
    public Supplier<List<Element>> createListCollectionThousand() {
        return createList(1000);
    }

    @Bean(LIST_COLLECTION_TEN_THOUSAND)
    public Supplier<List<Element>> createListCollectionTenThousand() {
        return createList(10000);
    }

    @Bean(LIST_COLLECTION_HUNDRED_THOUSAND)
    public Supplier<List<Element>> createListCollectionHundredThousand() {
        return createList(100000);
    }

    @Bean(LIST_COLLECTION_MILLION)
    public Supplier<List<Element>> createListCollectionMillion() {
        return createList(1000000);
    }

    @Bean(LIST_COLLECTION_TEN_MILLION)
    public Supplier<List<Element>> createListCollectionTenMillion() {
        return createList(10000000);
    }

    @Bean
    public List<Supplier<List<Element>>> listCollections(
        @Qualifier(LIST_COLLECTION_TEN) final Supplier<List<Element>> ten,
        @Qualifier(LIST_COLLECTION_HUNDRED) final Supplier<List<Element>> hundred,
        @Qualifier(LIST_COLLECTION_THOUSAND) final Supplier<List<Element>> thousand,
        @Qualifier(LIST_COLLECTION_TEN_THOUSAND) final Supplier<List<Element>> tenThousand,
        @Qualifier(LIST_COLLECTION_HUNDRED_THOUSAND) final Supplier<List<Element>> hundredThousand,
        @Qualifier(LIST_COLLECTION_HUNDRED_THOUSAND) final Supplier<List<Element>> million,
        @Qualifier(LIST_COLLECTION_TEN_MILLION) final Supplier<List<Element>> tenMillion
    ) {
        return List.of(ten, hundred, thousand, tenThousand, hundredThousand, million, tenMillion);
    }

    private Supplier<List<Element>> createList(int amount) {
        return () -> {
            var list = new ArrayList<Element>();
            for (int i = 0; i < amount; i++) {
                list.add(new Element(UUID.fromString(ELEMENT_UUID), null));
            }
            return list;
        };
    }

}