package net.proselyte.jwtappdemo.repository;

import net.proselyte.jwtappdemo.dto.ObjectToTotalOperations;
import net.proselyte.jwtappdemo.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OperationRepository extends JpaRepository<Operation,Long> {
    List<Operation> findByUserId(Long id);








}
