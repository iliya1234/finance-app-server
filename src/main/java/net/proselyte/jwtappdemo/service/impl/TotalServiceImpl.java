package net.proselyte.jwtappdemo.service.impl;


import lombok.extern.slf4j.Slf4j;
import net.proselyte.jwtappdemo.dto.ObjectToTotalOperations;
import net.proselyte.jwtappdemo.repository.TotalRepository;
import net.proselyte.jwtappdemo.repository.UserRepository;
import net.proselyte.jwtappdemo.service.TotalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TotalServiceImpl implements TotalService {


    private UserRepository userRepository;

    private TotalRepository totalRepository;


    public TotalServiceImpl(UserRepository userRepository, TotalRepository totalRepository) {
        this.userRepository = userRepository;
        this.totalRepository = totalRepository;
    }

    @Override
    public List<ObjectToTotalOperations> readPurchasesTotalToday(Long idUser) {
        return totalRepository.todayTotalPurchases(idUser);
    }

    @Override
    public List<ObjectToTotalOperations> readIncomesTotalToday(Long idUser) {
        return totalRepository.todayTotalIncomes(idUser);
    }

    @Override
    public List<ObjectToTotalOperations> readPurchasesTotalWeek(Long idUser) {
        return totalRepository.weekTotalPurchases(idUser);
    }

    @Override
    public List<ObjectToTotalOperations> readIncomesTotalWeek(Long idUser) {
        return totalRepository.weekTotalIncomes(idUser);
    }

    @Override
    public List<ObjectToTotalOperations> readPurchasesTotalMonth(Long idUser) {
        return totalRepository.monthTotalPurchases(idUser);
    }

    @Override
    public List<ObjectToTotalOperations> readIncomesTotalMonth(Long idUser) {
        return totalRepository.monthTotalIncomes(idUser);
    }

    @Override
    public List<ObjectToTotalOperations> readPurchasesTotalYear(Long idUser) {
        return totalRepository.yearTotalPurchases(idUser);
    }

    @Override
    public List<ObjectToTotalOperations> readIncomesTotalYear(Long idUser) {
        return totalRepository.yearTotalIncomes(idUser);
    }
}
