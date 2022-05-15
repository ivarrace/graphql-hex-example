package com.ivarrace.graphqlhex.application.service;

import com.ivarrace.graphqlhex.domain.model.Dummy;
import com.ivarrace.graphqlhex.domain.repository.DummyRepository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DummyService {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final DummyRepository repository;

    public DummyService(DummyRepository dummyRepository) {
        this.repository = dummyRepository;
    }

    public Page<Dummy> findAll(final Integer pageNumber, final Integer pageSize, final Optional<String> orderBy, final boolean asc, final Dummy dummy) {
        final var direction = asc ? Sort.Direction.ASC : Sort.Direction.DESC;
        final Sort sort = orderBy.isPresent() && orderBy.get() != null ? Sort.by(direction, orderBy.get()) : Sort.unsorted();
        final var pageable = PageRequest.of(pageNumber == null ? DEFAULT_PAGE_NUMBER : pageNumber, pageSize == null ? DEFAULT_PAGE_SIZE : pageSize, sort);
        //TODO
        final var matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase();
        final var example = Example.of(dummy, matcher);
        return repository.findAll(example, pageable);
    }

    public Dummy save(final Dummy dummy) {
        return repository.save(dummy);
    }
}
