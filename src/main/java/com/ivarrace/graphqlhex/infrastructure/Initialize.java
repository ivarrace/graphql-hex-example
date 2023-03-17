package com.ivarrace.graphqlhex.infrastructure;

import com.ivarrace.graphqlhex.domain.model.Dummy;
import com.ivarrace.graphqlhex.domain.repository.DummyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class Initialize {

    @Autowired private DummyRepository repository;

    @PostConstruct
    public void execute(){
        repository.save(new Dummy(UUID.fromString("d8110ac0-4651-41e7-9aaa-05d02fddb2fa"), "test-dummy"));
        repository.save(new Dummy(null, UUID.randomUUID().toString()));
        repository.save(new Dummy(null, UUID.randomUUID().toString()));
        repository.save(new Dummy(null, UUID.randomUUID().toString()));
        repository.save(new Dummy(null, UUID.randomUUID().toString()));
        repository.save(new Dummy(null, UUID.randomUUID().toString()));
    }

}
