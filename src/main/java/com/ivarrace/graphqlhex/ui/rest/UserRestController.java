package com.ivarrace.graphqlhex.ui.rest;

import com.ivarrace.graphqlhex.domain.model.User;
import com.ivarrace.graphqlhex.application.command.UserCommandHandler;
import com.ivarrace.graphqlhex.application.command.user.ListCommand;
import com.ivarrace.graphqlhex.application.command.user.SaveCommand;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/user")
@Validated
public class UserRestController {

    private final UserCommandHandler commandHandler;

    public UserRestController(UserCommandHandler userCommandHandler){
        this.commandHandler = userCommandHandler;
    }

    @GetMapping
    public Page<User> get(@Valid ListCommand command) {
        return commandHandler.handle(command);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User post(@RequestBody @Valid SaveCommand command) {
        return commandHandler.handle(command);
    }

}