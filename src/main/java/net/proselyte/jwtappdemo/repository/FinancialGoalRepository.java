package net.proselyte.jwtappdemo.repository;

import net.proselyte.jwtappdemo.model.FinancialGoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinancialGoalRepository extends JpaRepository<FinancialGoal, Long> {
    List<FinancialGoal> findByUserId(Long id);

    List<FinancialGoal> findByUserUsername(String username);
}
