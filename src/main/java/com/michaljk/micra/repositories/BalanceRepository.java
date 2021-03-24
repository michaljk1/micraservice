package com.michaljk.micra.repositories;

import com.michaljk.micra.models.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

}
