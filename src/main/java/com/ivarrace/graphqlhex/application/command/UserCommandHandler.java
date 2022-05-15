package com.ivarrace.graphqlhex.application.command;

import com.ivarrace.graphqlhex.application.command.user.ListCommand;
import com.ivarrace.graphqlhex.application.command.user.SaveCommand;
import com.ivarrace.graphqlhex.application.service.UserService;
import com.ivarrace.graphqlhex.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class UserCommandHandler {

    private final UserService service;

    public UserCommandHandler(UserService userService){
        this.service = userService;
    }

    public Page<User> handle(final ListCommand command) {
        return service.findAll(command.getPageNumber(), command.getPageSize(), command.getOrderBy(), command.getAsc(),
                command.getUser() == null ? new User() : command.getUser());
    }

    public User handle(final SaveCommand command) {
        return service.save(new User(command.getName()));
    }
}
