package com.ivarrace.graphqlhex.ui.rest;

import com.ivarrace.graphqlhex.domain.model.Dummy;
import com.ivarrace.graphqlhex.application.command.DummyCommandHandler;
import com.ivarrace.graphqlhex.application.command.dummy.ListCommand;
import com.ivarrace.graphqlhex.application.command.dummy.SaveCommand;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/dummy")
@Validated
public class DummyRestController {

    private final DummyCommandHandler commandHandler;

    public DummyRestController(DummyCommandHandler dummyCommandHandler){
        this.commandHandler = dummyCommandHandler;
    }

    @GetMapping
    public Page<Dummy> get(@Valid ListCommand command) {
        return commandHandler.handle(command);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dummy post(@RequestBody @Valid SaveCommand command) {
        return commandHandler.handle(command);
    }

}