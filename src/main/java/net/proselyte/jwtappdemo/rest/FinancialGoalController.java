package net.proselyte.jwtappdemo.rest;

import net.proselyte.jwtappdemo.dto.CreateUpdateFinancialGoalDto;
import net.proselyte.jwtappdemo.dto.FinancialGoalDto;
import net.proselyte.jwtappdemo.model.FinancialGoal;
import net.proselyte.jwtappdemo.service.FinancialGoalService;
import net.proselyte.jwtappdemo.service.UserService;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/financial_goals/")
public class FinancialGoalController {
    private final FinancialGoalService financialGoalService;
    private final UserService userService;

    public FinancialGoalController(FinancialGoalService financialGoalService, UserService userService) {
        this.financialGoalService = financialGoalService;
        this.userService = userService;
    }

    @GetMapping(value = "all/user/{id}")
    public ResponseEntity<List<FinancialGoalDto>> getAllFinancialGoalById(@RequestHeader Map<String,String> headers,
                                                                          @PathVariable(name = "id") Long id)
            throws UnsupportedEncodingException, JSONException {
        if(!userService.checkingAccessRights(id, headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        List<FinancialGoal> financialGoalList = financialGoalService.findByUserId(id);
        if (financialGoalList == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<FinancialGoalDto> result = FinancialGoalDto.fromListFinancialGoal(financialGoalList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping(value = "create/user/{id_user}")
    public ResponseEntity createFinancialGoal(@RequestBody CreateUpdateFinancialGoalDto createUpdateFinancialGoalDto,
                                              @PathVariable(name = "id_user")
            Long id_user, @RequestHeader Map<String,String> headers) throws UnsupportedEncodingException, JSONException {
        if(!userService.checkingAccessRights(id_user, headers))
            return new ResponseEntity("Error: you have no rights",HttpStatus.FORBIDDEN);
        Map<Object,Object> response = new HashMap<>();
        FinancialGoal financialGoal = financialGoalService.create(createUpdateFinancialGoalDto.toFinancialGoal(),id_user);
        if(financialGoal == null){
            response.put("message","Error: failed to create financial goal");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("message", "Financial goal created successfully");
        return ResponseEntity.ok(response);
    }
    @GetMapping(value = "get/id_financial_goal/{id_financial_goal}")
    public ResponseEntity<FinancialGoalDto> getFinancialGoalByIdUserAndIdFinancialGoal(@RequestHeader Map<String,String> headers,
                                                                        @PathVariable(name = "id_financial_goal") Long id_financial_goal)
            throws UnsupportedEncodingException, JSONException {
        FinancialGoal financialGoal = financialGoalService.findById(id_financial_goal);
        if (financialGoal == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!userService.checkingAccessRights(financialGoal.getUser().getId(), headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        FinancialGoalDto result = FinancialGoalDto.fromFinancialGoal(financialGoal);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @DeleteMapping(value = "delete/id_financial_goal/{id_financial_goal}")
    public ResponseEntity deleteFinancialGoalById(@RequestHeader Map<String,String> headers,
                                             @PathVariable(name = "id_financial_goal") Long id_financial_goal)
            throws UnsupportedEncodingException, JSONException {
        FinancialGoal financialGoal = financialGoalService.findById(id_financial_goal);
        if(financialGoal== null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Long id_user = financialGoal.getUser().getId();
        if (!userService.checkingAccessRights(id_user, headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        financialGoalService.delete(id_financial_goal);
        return ResponseEntity.ok("Financial goal deleted successfully");
    }
    @PutMapping(value = "update/id_financial_goal/{id_financial_goal}")
    public ResponseEntity updateFinancialGoalById(@RequestHeader Map<String,String> headers,
                                             @PathVariable(name = "id_financial_goal") Long id_financial_goal,
                                             @RequestBody CreateUpdateFinancialGoalDto createUpdateFinancialGoalDto
    ) throws UnsupportedEncodingException, JSONException {
        FinancialGoal financialGoal = financialGoalService.findById(id_financial_goal);
        if(financialGoal== null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!userService.checkingAccessRights(financialGoal.getUser().getId(), headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        FinancialGoal result = financialGoalService.update(createUpdateFinancialGoalDto.toFinancialGoal(),
                financialGoal.getId(),financialGoal.getUser(),financialGoal.getDateStart());
        if(result == null){
            return ResponseEntity.badRequest().body("Error: failed to update financial goal");
        }
        return new ResponseEntity<>(FinancialGoalDto.fromFinancialGoal(result), HttpStatus.OK);
    }
}
