package com.ivarrace.graphqlhex.ui.graphql.mapper;

import com.ivarrace.graphqlhex.application.command.DummyCommandHandler;
import com.ivarrace.graphqlhex.application.command.dummy.ListCommand;
import com.ivarrace.graphqlhex.application.command.dummy.SaveCommand;
import com.ivarrace.graphqlhex.domain.model.Dummy;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DummyMapper {

    private final DummyCommandHandler commandHandler;

    public DummyMapper(DummyCommandHandler dummyCommandHandler){
        this.commandHandler = dummyCommandHandler;
    }

    @GraphQLQuery(name = "findAll")
    public Page<Dummy> findAll(@GraphQLNonNull final Integer pageNumber,
                               @GraphQLNonNull final Integer pageSize,
                               final String orderBy,
                               @GraphQLNonNull final Boolean asc,
                               final Dummy dummy) {
        return commandHandler.handle(new ListCommand(pageNumber, pageSize, Optional.ofNullable(orderBy), asc, dummy));
    }

    @GraphQLQuery(name = "save")
    public Dummy save(@GraphQLNonNull final String name) {
        return commandHandler.handle(new SaveCommand(name));
    }
}