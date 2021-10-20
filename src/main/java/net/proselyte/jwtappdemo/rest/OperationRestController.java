package net.proselyte.jwtappdemo.rest;

import net.proselyte.jwtappdemo.dto.*;
import net.proselyte.jwtappdemo.model.Operation;
import net.proselyte.jwtappdemo.model.Subcategory;
import net.proselyte.jwtappdemo.security.jwt.JwtTokenProvider;
import net.proselyte.jwtappdemo.service.OperationService;
import net.proselyte.jwtappdemo.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/operations")
public class OperationRestController {

    private final OperationService operationService;
    private final SubcategoryService subcategoryService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public OperationRestController(OperationService operationService, SubcategoryService subcategoryService,
                                   JwtTokenProvider jwtTokenProvider) {
        this.operationService = operationService;
        this.subcategoryService = subcategoryService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private String dataChecking(CreateUpdateOperationDto result) {
        String message;
        if (result.getCreated() == null) {
            message = "Дата операции не может быть пустой";
            return message;
        }
        if (result.getSubcategoryId() == null) {
            message = "Id подкатегории не может быть пустым";
            return message;
        }
        if (result.getTotal() == null) {
            message = "Сумма операции не может быть пустой";
            return message;
        }
        if (result.getType() == null) {
            message = "Тип операции не может быть пустым";
            return message;
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<List<OperationDto>> getAllOperation(HttpServletRequest request) {
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        List<Operation> operationList = operationService.findByUserUsername(username);
        if (operationList == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<OperationDto> result = OperationDto.fromListOperation(operationList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createOperation
            (HttpServletRequest request, @RequestBody CreateUpdateOperationDto createUpdateOperationDto) {
        Map<String, String> response = new HashMap<>();
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        String message = dataChecking(createUpdateOperationDto);
        if (message != null) {
            response.put("message", message);
            return ResponseEntity.badRequest().body(response);
        }
        Subcategory subcategory = subcategoryService.findById(createUpdateOperationDto.getSubcategoryId());
        if (subcategory == null) {
            response.put("message", "Нет подкатегории для операции");
            return ResponseEntity.badRequest().body(response);
        }
        Operation operation = operationService.create(createUpdateOperationDto.toOperation(subcategory), username);
        if (operation == null) {
            response.put("message", "Ошибка при добавлении операции");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("message", "Операция успешно создана!");
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id_operation}")
    public ResponseEntity<OperationDto> getOperation(HttpServletRequest request,
                                                     @PathVariable(name = "id_operation")
                                                             Long id_operation) {
        Map<String, String> response = new HashMap<>();
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        Operation operation = operationService.findById(id_operation);
        if (operation == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!username.equals(operation.getUser().getUsername())) {
            response.put("message", "У вас недостаточно прав");
            return new ResponseEntity(response, HttpStatus.FORBIDDEN);
        }
        OperationDto result = OperationDto.fromOperation(operation);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id_operation}")
    public ResponseEntity<Map<String, String>> deleteOperation(HttpServletRequest request,
                                                               @PathVariable(name = "id_operation") Long id_operation) {
        Map<String, String> response = new HashMap<>();
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        Operation operation = operationService.findById(id_operation);
        if (operation == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!username.equals(operation.getUser().getUsername())) {
            response.put("message", "У вас недостаточно прав");
            return new ResponseEntity(response, HttpStatus.FORBIDDEN);
        }
        operationService.delete(id_operation);
        response.put("message", "Операция успешно удалена!");
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id_operation}")
    public ResponseEntity updateOperation(HttpServletRequest request,
                                          @PathVariable(name = "id_operation") Long id_operation,
                                          @RequestBody CreateUpdateOperationDto createUpdateOperationDto) {
        Map<String, String> response = new HashMap<>();
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        String message = dataChecking(createUpdateOperationDto);
        if (message != null) {
            response.put("message", message);
            return ResponseEntity.badRequest().body(response);
        }
        Operation operation = operationService.findById(id_operation);
        if (operation == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!username.equals(operation.getUser().getUsername())) {
            response.put("message", "У вас недостаточно прав");
            return new ResponseEntity(response, HttpStatus.FORBIDDEN);
        }
        Subcategory subcategory = subcategoryService.findById(createUpdateOperationDto.getSubcategoryId());
        if (subcategory == null) {
            response.put("message", "Нет подкатегории для операции");
            return ResponseEntity.badRequest().body(response);
        }
        CreateUpdateOperationDto result = CreateUpdateOperationDto.fromOperation(operationService.update(
                createUpdateOperationDto.toOperation(subcategory),
                id_operation, subcategory.getUser()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
