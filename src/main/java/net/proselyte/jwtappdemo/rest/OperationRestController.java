package net.proselyte.jwtappdemo.rest;

import net.proselyte.jwtappdemo.dto.*;
import net.proselyte.jwtappdemo.model.Operation;
import net.proselyte.jwtappdemo.model.Subcategory;
import net.proselyte.jwtappdemo.service.OperationService;
import net.proselyte.jwtappdemo.service.SubcategoryService;
import net.proselyte.jwtappdemo.service.UserService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/operations/")
public class OperationRestController {

    private final OperationService operationService;
    private final UserService userService;
    private final SubcategoryService subcategoryService;

    @Autowired
    public OperationRestController(OperationService operationService, UserService userService, SubcategoryService subcategoryService) {
        this.operationService = operationService;
        this.userService = userService;
        this.subcategoryService = subcategoryService;
    }

    @GetMapping(value = "all/user/{id}")
    public ResponseEntity<List<OperationDto>> getAllOperationById(@RequestHeader Map<String,String> headers,
                                                                  @PathVariable(name = "id") Long id)
            throws UnsupportedEncodingException, JSONException {
        if(!userService.checkingAccessRights(id, headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        List<Operation> operationList = operationService.findByUserId(id);
        if (operationList == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<OperationDto> result = OperationDto.fromListOperation(operationList);
        if(result==null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "create/user/{id_user}")
    public ResponseEntity createOperation(@RequestBody CreateUpdateOperationDto createUpdateOperationDto,
                                            @PathVariable(name = "id_user") Long id_user,
                                            @RequestHeader Map<String,String> headers)
            throws UnsupportedEncodingException, JSONException {
        if(!userService.checkingAccessRights(id_user, headers))
            return new ResponseEntity("Error: you have no rights",HttpStatus.FORBIDDEN);
        Map<Object,Object> response = new HashMap<>();
        Subcategory subcategory = subcategoryService.findById(createUpdateOperationDto.getSubcategory_id());
        if(subcategory == null){
            response.put("message","Error: there is no subcategory for operation");
            return ResponseEntity.badRequest().body(response);
        }
        Operation operation = operationService.create(createUpdateOperationDto.toOperation(subcategory),
                id_user);
        if(operation == null){
            response.put("message","Error: failed to create operation");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("message", "Operation created successfully");
        return ResponseEntity.ok(response);
    }
    @GetMapping(value = "get/id_operation/{id_operation}")
    public ResponseEntity<OperationDto> getOperationByIdUserAndIdCategory(@RequestHeader Map<String,String> headers,
                                                                        @PathVariable(name = "id_operation")
                                                                                Long id_operation)
            throws UnsupportedEncodingException, JSONException {
        Operation operation = operationService.findById(id_operation);
        if (operation == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!userService.checkingAccessRights(operation.getUser().getId(), headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        OperationDto result = OperationDto.fromOperation(operation);
        return new ResponseEntity(result, HttpStatus.OK);
    }
    @DeleteMapping(value = "delete/id_operation/{id_operation}")
    public ResponseEntity deleteOperationById(@RequestHeader Map<String,String> headers,
                                                @PathVariable(name = "id_operation") Long id_operation)
            throws UnsupportedEncodingException, JSONException {
        Operation operation = operationService.findById(id_operation);
        if(operation== null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Long id_user = operation.getUser().getId();
        if (!userService.checkingAccessRights(id_user, headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        operationService.delete(id_operation);
        return ResponseEntity.ok("Operation deleted successfully");
    }
    @PutMapping(value = "update/id_operation/{id_operation}")
    public ResponseEntity updateOperationById(@RequestHeader Map<String,String> headers,
                                                @PathVariable(name = "id_operation") Long id_operation,
                                                @RequestBody CreateUpdateOperationDto createUpdateOperationDto
    ) throws UnsupportedEncodingException, JSONException {
        Operation operation = operationService.findById(id_operation);
        if(operation== null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Map<Object,Object> response = new HashMap<>();
        if (!userService.checkingAccessRights(operation.getUser().getId(), headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        Subcategory subcategory = subcategoryService.findById(createUpdateOperationDto.getSubcategory_id());
        if(subcategory == null){
            response.put("message","Error: there is no subcategory for operation");
            return ResponseEntity.badRequest().body(response);
        }
        CreateUpdateOperationDto result = CreateUpdateOperationDto.fromOperation(operationService.update(
                createUpdateOperationDto.toOperation(subcategory),
                id_operation,subcategory.getUser()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
