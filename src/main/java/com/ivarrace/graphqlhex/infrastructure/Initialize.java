package com.ivarrace.graphqlhex.infrastructure;

import com.ivarrace.graphqlhex.domain.model.User;
import com.ivarrace.graphqlhex.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class Initialize {

    @Autowired private UserRepository repository;

    @PostConstruct
    public void execute(){
        repository.save(new User(null, UUID.randomUUID().toString()));
        repository.save(new User(null, UUID.randomUUID().toString()));
        repository.save(new User(null, UUID.randomUUID().toString()));
        repository.save(new User(null, UUID.randomUUID().toString()));
        repository.save(new User(null, UUID.randomUUID().toString()));
        repository.save(new User(null, UUID.randomUUID().toString()));
    }

}
