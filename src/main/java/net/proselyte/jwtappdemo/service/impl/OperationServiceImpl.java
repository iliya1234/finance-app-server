package net.proselyte.jwtappdemo.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.proselyte.jwtappdemo.model.Operation;
import net.proselyte.jwtappdemo.model.Type;
import net.proselyte.jwtappdemo.model.User;
import net.proselyte.jwtappdemo.repository.OperationRepository;
import net.proselyte.jwtappdemo.repository.UserRepository;
import net.proselyte.jwtappdemo.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final UserRepository userRepository;

    @Autowired
    public OperationServiceImpl(OperationRepository operationRepository, UserRepository userRepository) {
        this.operationRepository = operationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Operation> getAll() {
        List<Operation> result = operationRepository.findAll();
        log.info("IN getAll - {} operations found", result.size());
        return result;
    }

    @Override
    public List<Operation> getAllPurchaseOrIncomes(Type type, String username) {
        List<Operation> result = operationRepository.findByTypeAndUserUsername(type, username);
        log.info("IN getAllPurchase - {} operations found", result.size());
        if (result.size() == 0) {
            log.info("IN getAllPurchase - no operations found by username: {}", username);
            return null;
        }
        return result;
    }

    @Override
    public Operation findById(Long id) {
        Operation result = operationRepository.findById(id).orElse(null);
        if (result == null) {
            log.warn("IN findById - no operation found by id: {}", id);
            return null;
        }
        log.info("IN findById - operation: {} found by id: {}", result, id);
        return result;

    }

    @Override
    public List<Operation> findByUserId(Long id) {
        List<Operation> result = operationRepository.findByUserId(id);
        log.info("IN getByUserId - {} operation found with id: {}", result.size(), id);
        return result;
    }

    @Override
    public Operation create(Operation operation, String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            log.warn("IN findById - no user found by username: {}", username);
            return null;
        }
        operation.setUser(user);
        operationRepository.save(operation);
        log.info("IN create - operation: {} successfully created", operation);
        return operation;
    }

    @Override
    public Operation update(Operation operation, Long id, User user) {
        operation.setId(id);
        operation.setUser(user);
        operationRepository.save(operation);
        log.info("IN update - operation: {} successfully updated", operation);
        return operation;
    }

    @Override
    public void delete(Long id) {
        operationRepository.deleteById(id);
    }

    @Override
    public List<Operation> findByUserUsername(String username) {
        List<Operation> result = operationRepository.findByUserUsername(username);
        log.info("IN getAll - {} operations found", result.size());
        return result;
    }


}
