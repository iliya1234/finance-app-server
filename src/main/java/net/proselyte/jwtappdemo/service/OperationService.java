package net.proselyte.jwtappdemo.service;

import net.proselyte.jwtappdemo.model.Operation;
import net.proselyte.jwtappdemo.model.Type;
import net.proselyte.jwtappdemo.model.User;

import java.util.List;

public interface OperationService {
    List<Operation> getAll();

    List<Operation> getAllPurchaseOrIncomes(Type type, String username);

    Operation findById(Long id);

    List<Operation> findByUserId(Long id);

    Operation create(Operation operation, String username);

    Operation update(Operation operation, Long id, User user);

    void delete(Long id);

    List<Operation> findByUserUsername(String username);

}
