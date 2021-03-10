package io.dealhub.demo.repository.dao;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table
public class Chain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chainId;
    @ElementCollection(targetClass = Verifier.class)
    private List<Verifier> verifiers;
}
