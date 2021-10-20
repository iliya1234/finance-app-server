package net.proselyte.jwtappdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class TotalOperationsDto implements ObjectToTotalOperations {
    private String name;
    private Double total;
}

