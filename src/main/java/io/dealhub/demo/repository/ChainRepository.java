package io.dealhub.demo.repository;

import io.dealhub.demo.repository.dao.Chain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChainRepository extends JpaRepository<Chain, Long> {
}
