package io.dealhub.demo.controller.dto;

import io.dealhub.demo.repository.dao.Verifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ChainDTO {
    private Long chainId;
    private List<Verifier> verifiers;
}
