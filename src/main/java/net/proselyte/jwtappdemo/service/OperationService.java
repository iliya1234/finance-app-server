package net.proselyte.jwtappdemo.service;

import net.proselyte.jwtappdemo.dto.ObjectToTotalOperations;
import net.proselyte.jwtappdemo.model.Operation;
import net.proselyte.jwtappdemo.model.User;

import java.util.List;

public interface OperationService {
    List<Operation> getAll();

    Operation findById(Long id);

    List<Operation> findByUserId(Long id);

    Operation create(Operation operation, Long idUser);

    Operation update(Operation operation, Long id, User user);

    void delete(Long id);

}
