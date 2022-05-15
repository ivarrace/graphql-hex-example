package com.ivarrace.graphqlhex.application.command.user;

import com.ivarrace.graphqlhex.domain.model.User;
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

    private User user;

}
