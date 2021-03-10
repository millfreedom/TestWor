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
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ruleId;
    private String condition;
    private Long min;
    private Long max;
    @ElementCollection(targetClass = Long.class)
    private List<Long> verifiersIds;
}
