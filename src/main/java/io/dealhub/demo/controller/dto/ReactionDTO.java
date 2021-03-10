package io.dealhub.demo.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@NoArgsConstructor
@Getter
public class ReactionDTO {
    private Long chainId;
    private Long userId;
    private Action action;
    @JsonInclude(Include.NON_NULL)
    private String comment;
}
