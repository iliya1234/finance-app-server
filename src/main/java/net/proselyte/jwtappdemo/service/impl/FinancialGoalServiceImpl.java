package net.proselyte.jwtappdemo.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.proselyte.jwtappdemo.model.FinancialGoal;
import net.proselyte.jwtappdemo.model.User;
import net.proselyte.jwtappdemo.repository.FinancialGoalRepository;
import net.proselyte.jwtappdemo.repository.UserRepository;
import net.proselyte.jwtappdemo.service.FinancialGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class FinancialGoalServiceImpl implements FinancialGoalService {
    private FinancialGoalRepository financialGoalRepository;
    private UserRepository userRepository;

    @Autowired
    public FinancialGoalServiceImpl(FinancialGoalRepository financialGoalRepository, UserRepository userRepository) {
        this.financialGoalRepository = financialGoalRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<FinancialGoal> getAll() {
        List<FinancialGoal> result = financialGoalRepository.findAll();
        log.info("IN getAll - {} financial goals found", result.size());
        return result;
    }

    @Override
    public FinancialGoal findById(Long id) {
        FinancialGoal result = financialGoalRepository.findById(id).orElse(null);
        if (result == null){
            log.warn("IN findById - no financial goal found by id: {}", id);
            return null;
        }
        log.info("IN findById - financial goal: {} found by id: {}", result);
        return result;

    }

    @Override
    public List<FinancialGoal> findByUserId(Long id) {
        List<FinancialGoal> result = financialGoalRepository.findByUserId(id);
        log.info("IN getByUserId - {} financial goal found with id: {}", result.size(),id);
        return result;
    }

    @Override
    public FinancialGoal create(FinancialGoal financialGoal,Long idUser) {
        Date dateStart = new Date();
        if(dateStart.compareTo(financialGoal.getDateEnd())==1){
            log.info("In create - the goal cannot be achieved in the past");
            return null;
        }
        financialGoal.setDateStart(dateStart);
        User user = userRepository.findById(idUser).orElse(null);
        if(user == null){
            log.warn("IN findById - no user found by id: {}", idUser);
            return null;
        }
        financialGoal.setUser(user);
        financialGoalRepository.save(financialGoal);
        log.info("IN create - financial goal: {} successfully created", financialGoal);
        return financialGoal;
    }

    @Override
    public FinancialGoal update(FinancialGoal financialGoal, Long id, User user,Date dateStart) {
        Date dateNow = new Date();
        if(dateNow.compareTo(financialGoal.getDateEnd())==1){
            log.info("In create - the goal cannot be achieved in the past");
            return null;
        }
        financialGoal.setId(id);
        financialGoal.setUser(user);
        financialGoal.setDateStart(dateStart);
        financialGoalRepository.save(financialGoal);
        log.info("IN update - category: {} successfully updated", financialGoal);
        return financialGoal;
    }

    @Override
    public void delete(Long id) {
        financialGoalRepository.deleteById(id);
    }
}
