package com.michaljk.micra.repositories;

import com.michaljk.micra.models.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

}
