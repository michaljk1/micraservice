package com.michaljk.micra.repositories;

import com.michaljk.micra.models.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface PeriodRepository extends JpaRepository<Period, Long> {
    Optional<Period> findByMonthAndYear(String month, Integer year);
    Period getByMonthAndYear(String month, Integer year);
}
