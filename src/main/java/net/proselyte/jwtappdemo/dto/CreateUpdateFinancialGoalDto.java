package net.proselyte.jwtappdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import net.proselyte.jwtappdemo.model.FinancialGoal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUpdateFinancialGoalDto {
    private String name;
    private Date dateEnd;
    private Double sumTotal;
    private Double sumUser;

    public FinancialGoal toFinancialGoal(){
        FinancialGoal financialGoal = new FinancialGoal();
        financialGoal.setName(name);
        financialGoal.setDateEnd(dateEnd);
        financialGoal.setSumTotal(sumTotal);
        financialGoal.setSumUser(sumUser);
        return financialGoal;
    }

    public static CreateUpdateFinancialGoalDto fromFinancialGoal (FinancialGoal financialGoal) {
        CreateUpdateFinancialGoalDto createUpdateFinancialGoalDto = new CreateUpdateFinancialGoalDto();
        createUpdateFinancialGoalDto.setName(financialGoal.getName());
        createUpdateFinancialGoalDto.setDateEnd(financialGoal.getDateEnd());
        createUpdateFinancialGoalDto.setSumTotal(financialGoal.getSumTotal());
        createUpdateFinancialGoalDto.setSumUser(financialGoal.getSumUser());
        return createUpdateFinancialGoalDto;
    }
    public static List<FinancialGoalDto> fromListFinancialGoal(List<FinancialGoal> financialGoalList){
        List<FinancialGoalDto> financialGoalDtoList = new ArrayList<>();
        for(FinancialGoal financialGoal : financialGoalList){
            FinancialGoalDto financialGoalDto = FinancialGoalDto.fromFinancialGoal(financialGoal);
            financialGoalDtoList.add(financialGoalDto);
        }
        return financialGoalDtoList;
    }
}
