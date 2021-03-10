package io.dealhub.demo.repository.dao;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Entity
@Table
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long planId;
    private long budget;
    private VerificationState verificationState;
    @ManyToMany
    private List<Chain> chains;
}
