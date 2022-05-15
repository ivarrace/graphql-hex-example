package com.ivarrace.graphqlhex.application.command.dummy;

import com.ivarrace.graphqlhex.infrastructure.validations.SafeHtml;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveCommand {

    @NotEmpty
    @Size(min = 5, max = 100)
    @SafeHtml
    private String name;

}
