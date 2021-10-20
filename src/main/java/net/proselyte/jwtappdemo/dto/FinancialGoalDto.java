package net.proselyte.jwtappdemo.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import net.proselyte.jwtappdemo.model.FinancialGoal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FinancialGoalDto {

    private Long id;
    private String name;
    private Date dateStart;
    private Date dateEnd;
    private Double sumTotal;
    private Double sumUser;

    public FinancialGoal toFinancialGoal() {
        FinancialGoal financialGoal = new FinancialGoal();
        financialGoal.setId(id);
        financialGoal.setName(name);
        financialGoal.setDateStart(dateStart);
        financialGoal.setDateEnd(dateEnd);
        financialGoal.setSumTotal(sumTotal);
        financialGoal.setSumUser(sumUser);
        return financialGoal;
    }

    public static FinancialGoalDto fromFinancialGoal(FinancialGoal financialGoal) {
        FinancialGoalDto financialGoalDto = new FinancialGoalDto();
        financialGoalDto.setId(financialGoal.getId());
        financialGoalDto.setName(financialGoal.getName());
        financialGoalDto.setDateStart(financialGoal.getDateStart());
        financialGoalDto.setDateEnd(financialGoal.getDateEnd());
        financialGoalDto.setSumTotal(financialGoal.getSumTotal());
        financialGoalDto.setSumUser(financialGoal.getSumUser());
        return financialGoalDto;
    }

    public static List<FinancialGoalDto> fromListFinancialGoal(List<FinancialGoal> financialGoalList) {
        List<FinancialGoalDto> financialGoalDtoList = new ArrayList<>();
        for (FinancialGoal financialGoal : financialGoalList) {
            FinancialGoalDto financialGoalDto = FinancialGoalDto.fromFinancialGoal(financialGoal);
            financialGoalDtoList.add(financialGoalDto);
        }
        return financialGoalDtoList;
    }
}
