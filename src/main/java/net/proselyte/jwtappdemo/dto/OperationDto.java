package net.proselyte.jwtappdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import net.proselyte.jwtappdemo.model.Operation;
import net.proselyte.jwtappdemo.model.Subcategory;
import net.proselyte.jwtappdemo.model.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperationDto {
    private Long id;
    private Date created;
    private String description;
    private Double total;
    private Long subcategory_id;
    private Type type;

    public Operation toOperation(Subcategory subcategory){
        Operation operation = new Operation();
        operation.setId(id);
        operation.setCreated(created);
        operation.setDescription(description);
        operation.setTotal(total);
        operation.setSubcategory(subcategory);
        operation.setType(type);
        return operation;
    }
    public static OperationDto fromOperation(Operation operation){
        OperationDto operationDto = new OperationDto();
        operationDto.setId(operation.getId());
        operationDto.setCreated(operation.getCreated());
        operationDto.setDescription(operation.getDescription());
        operationDto.setTotal(operation.getTotal());
        operationDto.setSubcategory_id(operation.getSubcategory().getId());
        operationDto.setType(operation.getType());
        return operationDto;
    }
    public static List<OperationDto> fromListOperation(List<Operation> operationList){
        List<OperationDto> operationDtoList = new ArrayList<>();
        for(Operation operation : operationList){
            OperationDto operationDto = OperationDto.fromOperation(operation);
            operationDtoList.add(operationDto);
        }
        return operationDtoList;
    }
}
