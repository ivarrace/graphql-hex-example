package com.ivarrace.graphqlhex.application.command;

import com.ivarrace.graphqlhex.application.command.dummy.GetCommand;
import com.ivarrace.graphqlhex.application.command.dummy.ListCommand;
import com.ivarrace.graphqlhex.application.command.dummy.SaveCommand;
import com.ivarrace.graphqlhex.application.service.DummyService;
import com.ivarrace.graphqlhex.domain.model.Dummy;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DummyCommandHandler {

    private final DummyService service;

    public DummyCommandHandler(DummyService dummyService){
        this.service = dummyService;
    }

    public Page<Dummy> handle(final ListCommand command) {
        return service.findAll(command.getPageNumber(), command.getPageSize(), command.getOrderBy(), command.getAsc(),
                command.getDummy() == null ? new Dummy() : command.getDummy());
    }

    public Dummy handle(GetCommand command) {
        return service.findOne(command.getId());
    }

    public Dummy handle(final SaveCommand command) {
        return service.save(new Dummy(command.getName()));
    }
}
