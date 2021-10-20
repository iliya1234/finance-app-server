package net.proselyte.jwtappdemo.rest;


import com.google.common.base.Strings;
import net.proselyte.jwtappdemo.dto.CreateUpdateFinancialGoalDto;
import net.proselyte.jwtappdemo.dto.FinancialGoalDto;
import net.proselyte.jwtappdemo.model.FinancialGoal;
import net.proselyte.jwtappdemo.security.jwt.JwtTokenProvider;
import net.proselyte.jwtappdemo.service.FinancialGoalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/financial_goals")
public class FinancialGoalController {
    private final FinancialGoalService financialGoalService;
    private final JwtTokenProvider jwtTokenProvider;

    public FinancialGoalController(FinancialGoalService financialGoalService, JwtTokenProvider jwtTokenProvider) {
        this.financialGoalService = financialGoalService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private String dataChecking(CreateUpdateFinancialGoalDto result) {
        String message;
        if (Strings.isNullOrEmpty(result.getName())) {
            message = "Название цели не может быть пустым";
            return message;
        }
        if (result.getDateEnd() == null) {
            message = "Дата окончания цели не может быть пустым";
            return message;
        }
        if (result.getSumTotal() == null) {
            message = "Итоговая сумма не должна быть пустой";
            return message;
        }
        if (result.getSumUser() == null) {
            message = "Сумма пользователя не может быть пустой";
            return message;
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<List<FinancialGoalDto>> getAllFinancialGoalById(HttpServletRequest request) {
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        List<FinancialGoal> financialGoalList = financialGoalService.findByUserUsername(username);
        if (financialGoalList == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<FinancialGoalDto> result = FinancialGoalDto.fromListFinancialGoal(financialGoalList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createFinancialGoal(HttpServletRequest request,
                                              @RequestBody CreateUpdateFinancialGoalDto createUpdateFinancialGoalDto) {
        Map<String, String> response = new HashMap<>();
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        String message = dataChecking(createUpdateFinancialGoalDto);
        if (message != null) {
            response.put("message", message);
            return ResponseEntity.badRequest().body(response);
        }
        FinancialGoal financialGoal = financialGoalService
                .create(createUpdateFinancialGoalDto.toFinancialGoal(), username);
        if (financialGoal == null) {
            response.put("message", "Цель не может быть достигнута в прошлом");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("message", "Финансовая цель успешно создана!");
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id_financial_goal}")
    public ResponseEntity<FinancialGoalDto> getFinancialGoalByIdUserAndIdFinancialGoal(
            HttpServletRequest request, @PathVariable(name = "id_financial_goal") Long id_financial_goal) {
        Map<String, String> response = new HashMap<>();
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        FinancialGoal financialGoal = financialGoalService.findById(id_financial_goal);
        if (financialGoal == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!username.equals(financialGoal.getUser().getUsername())) {
            response.put("message", "У вас недостаточно прав");
            return new ResponseEntity(response, HttpStatus.FORBIDDEN);
        }
        FinancialGoalDto result = FinancialGoalDto.fromFinancialGoal(financialGoal);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id_financial_goal}")
    public ResponseEntity deleteFinancialGoalById(HttpServletRequest request,
                                                  @PathVariable(name = "id_financial_goal") Long id_financial_goal) {
        Map<String, String> response = new HashMap<>();
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        FinancialGoal financialGoal = financialGoalService.findById(id_financial_goal);
        if (financialGoal == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!username.equals(financialGoal.getUser().getUsername())) {
            response.put("message", "У вас недостаточно прав");
            return new ResponseEntity(response, HttpStatus.FORBIDDEN);
        }
        financialGoalService.delete(id_financial_goal);
        response.put("message", "Финансовая цель успешно удалена");
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id_financial_goal}")
    public ResponseEntity updateFinancialGoalById
            (HttpServletRequest request, @PathVariable(name = "id_financial_goal") Long id_financial_goal,
             @RequestBody CreateUpdateFinancialGoalDto createUpdateFinancialGoalDto) {
        Map<String, String> response = new HashMap<>();
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        String message = dataChecking(createUpdateFinancialGoalDto);
        if (message != null) {
            response.put("message", message);
            return ResponseEntity.badRequest().body(response);
        }
        FinancialGoal financialGoal = financialGoalService.findById(id_financial_goal);
        if (financialGoal == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!username.equals(financialGoal.getUser().getUsername())) {
            response.put("message", "У вас недостаточно прав");
            return new ResponseEntity(response, HttpStatus.FORBIDDEN);
        }
        FinancialGoal result = financialGoalService.update(createUpdateFinancialGoalDto.toFinancialGoal(),
                financialGoal.getId(), financialGoal.getUser(), financialGoal.getDateStart());
        if (result == null) {
            response.put("message", "Цель не может быть достигнута в прошлом");
            return ResponseEntity.badRequest().body(response);
        }
        return new ResponseEntity<>(FinancialGoalDto.fromFinancialGoal(result), HttpStatus.OK);
    }
}
