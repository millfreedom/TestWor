package io.dealhub.demo.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.dealhub.demo.repository.dao.VerificationState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifierStateDTO {
    private boolean notified;
    private VerificationState verified;
    @JsonInclude(Include.NON_NULL)
    private String reason;
}
