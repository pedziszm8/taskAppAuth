package com.example.taskApp.statistics;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatisticsRepository extends JpaRepository<Statistics,Long> {
    Optional<Statistics> findByEmail(String email);//email do znalezien

}
