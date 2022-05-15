package com.ivarrace.graphqlhex.application.command.dummy;

import com.ivarrace.graphqlhex.domain.model.Dummy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCommand {

    @Min(0)
    private Integer pageNumber;

    @Min(1)
    private Integer pageSize;

    private Optional<String> orderBy = Optional.empty();

    private Boolean asc = Boolean.TRUE;

    private Dummy dummy;

}
