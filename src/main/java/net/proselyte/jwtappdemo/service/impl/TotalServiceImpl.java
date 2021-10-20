package net.proselyte.jwtappdemo.service.impl;


import lombok.extern.slf4j.Slf4j;
import net.proselyte.jwtappdemo.dto.ObjectToTotalOperations;
import net.proselyte.jwtappdemo.model.User;
import net.proselyte.jwtappdemo.repository.TotalRepository;
import net.proselyte.jwtappdemo.repository.UserRepository;
import net.proselyte.jwtappdemo.service.TotalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TotalServiceImpl implements TotalService {
    private final UserRepository userRepository;
    private final TotalRepository totalRepository;


    public TotalServiceImpl(UserRepository userRepository, TotalRepository totalRepository) {
        this.userRepository = userRepository;
        this.totalRepository = totalRepository;
    }

    @Override
    public List<ObjectToTotalOperations> readPurchasesTotalToday(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        List<ObjectToTotalOperations> result = totalRepository.todayTotalPurchases(user.getId());
        if (result.isEmpty())
            return null;
        return result;
    }

    @Override
    public List<ObjectToTotalOperations> readIncomesTotalToday(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        List<ObjectToTotalOperations> result = totalRepository.todayTotalIncomes(user.getId());
        if (result.isEmpty())
            return null;
        return result;
    }

    @Override
    public List<ObjectToTotalOperations> readPurchasesTotalWeek(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        List<ObjectToTotalOperations> result = totalRepository.weekTotalPurchases(user.getId());
        if (result.isEmpty())
            return null;
        return result;
    }

    @Override
    public List<ObjectToTotalOperations> readIncomesTotalWeek(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        List<ObjectToTotalOperations> result = totalRepository.weekTotalIncomes(user.getId());
        if (result.isEmpty())
            return null;
        return result;
    }

    @Override
    public List<ObjectToTotalOperations> readPurchasesTotalMonth(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        List<ObjectToTotalOperations> result = totalRepository.monthTotalPurchases(user.getId());
        if (result.isEmpty())
            return null;
        return result;
    }

    @Override
    public List<ObjectToTotalOperations> readIncomesTotalMonth(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        List<ObjectToTotalOperations> result = totalRepository.monthTotalIncomes(user.getId());
        if (result.isEmpty())
            return null;
        return result;
    }

    @Override
    public List<ObjectToTotalOperations> readPurchasesTotalYear(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        List<ObjectToTotalOperations> result = totalRepository.yearTotalPurchases(user.getId());
        if (result.isEmpty())
            return null;
        return result;
    }

    @Override
    public List<ObjectToTotalOperations> readIncomesTotalYear(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        List<ObjectToTotalOperations> result = totalRepository.yearTotalIncomes(user.getId());
        if (result.isEmpty())
            return null;
        return result;
    }
}
