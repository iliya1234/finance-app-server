package net.proselyte.jwtappdemo.service;

import net.proselyte.jwtappdemo.dto.ObjectToTotalOperations;

import java.util.List;

public interface TotalService {

    List<ObjectToTotalOperations> readPurchasesTotalToday(String username);

    List<ObjectToTotalOperations> readIncomesTotalToday(String username);

    List<ObjectToTotalOperations> readPurchasesTotalWeek(String username);

    List<ObjectToTotalOperations> readIncomesTotalWeek(String username);

    List<ObjectToTotalOperations> readPurchasesTotalMonth(String username);

    List<ObjectToTotalOperations> readIncomesTotalMonth(String username);

    List<ObjectToTotalOperations> readPurchasesTotalYear(String username);

    List<ObjectToTotalOperations> readIncomesTotalYear(String username);
}
