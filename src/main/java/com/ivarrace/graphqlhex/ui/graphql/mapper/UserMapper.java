package com.ivarrace.graphqlhex.ui.graphql.mapper;

import com.ivarrace.graphqlhex.application.command.UserCommandHandler;
import com.ivarrace.graphqlhex.application.command.user.ListCommand;
import com.ivarrace.graphqlhex.application.command.user.SaveCommand;
import com.ivarrace.graphqlhex.domain.model.User;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserMapper {

    private final UserCommandHandler commandHandler;

    public UserMapper(UserCommandHandler userCommandHandler){
        this.commandHandler = userCommandHandler;
    }

    @GraphQLQuery(name = "findAll")
    public Page<User> findAll(@GraphQLNonNull final Integer pageNumber,
                              @GraphQLNonNull final Integer pageSize,
                              final String orderBy,
                              @GraphQLNonNull final Boolean asc,
                              final User user) {
        return commandHandler.handle(new ListCommand(pageNumber, pageSize, Optional.ofNullable(orderBy), asc, user));
    }

    @GraphQLQuery(name = "save")
    public User save(@GraphQLNonNull final String name) {
        return commandHandler.handle(new SaveCommand(name));
    }
}