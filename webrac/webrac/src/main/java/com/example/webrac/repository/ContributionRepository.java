package com.example.webrac.repository;

import com.example.webrac.model.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface ContributionRepository extends JpaRepository<Contribution, Long> {

    @Query("select coalesce(sum(c.amount), 0) from Contribution c")
    BigDecimal sumAmount();
}
