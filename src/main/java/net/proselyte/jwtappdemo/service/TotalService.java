package net.proselyte.jwtappdemo.service;

import net.proselyte.jwtappdemo.dto.ObjectToTotalOperations;

import java.util.List;

public interface TotalService {

    List<ObjectToTotalOperations> readPurchasesTotalToday(Long idUser);

    List<ObjectToTotalOperations> readIncomesTotalToday(Long idUser);

    List<ObjectToTotalOperations> readPurchasesTotalWeek(Long idUser);

    List<ObjectToTotalOperations> readIncomesTotalWeek(Long idUser);

    List<ObjectToTotalOperations> readPurchasesTotalMonth(Long idUser);

    List<ObjectToTotalOperations> readIncomesTotalMonth(Long idUser);

    List<ObjectToTotalOperations> readPurchasesTotalYear(Long idUser);

    List<ObjectToTotalOperations> readIncomesTotalYear(Long idUser);
}
