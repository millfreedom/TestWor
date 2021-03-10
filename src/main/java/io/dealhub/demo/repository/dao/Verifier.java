package io.dealhub.demo.repository.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Verifier {
    private Long userId;
    @JsonInclude(Include.NON_NULL)
    private LocalDateTime dateNotification;
    @JsonInclude(Include.NON_NULL)
    private LocalDateTime dateVerification;
    private boolean notified;
    private VerificationState verified;
    @JsonInclude(Include.NON_NULL)
    private String reason;
}
