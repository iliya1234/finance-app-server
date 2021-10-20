package net.proselyte.jwtappdemo.service;

import net.proselyte.jwtappdemo.model.FinancialGoal;
import net.proselyte.jwtappdemo.model.User;

import java.util.Date;
import java.util.List;

public interface FinancialGoalService {
    List<FinancialGoal> getAll();

    FinancialGoal findById(Long id);

    List<FinancialGoal> findByUserId(Long id);

    FinancialGoal create(FinancialGoal financialGoal, String username);

    FinancialGoal update(FinancialGoal financialGoal, Long id, User user, Date dateStart);

    void delete(Long id);

    List<FinancialGoal> findByUserUsername(String username);
}
