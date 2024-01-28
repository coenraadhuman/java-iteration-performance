package io.github.coenraadhuman.iteration.performance.domain.model;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public record Element(
    UUID id,
    Name name
) {

    public String toString() {
        return id.toString();
    }

}