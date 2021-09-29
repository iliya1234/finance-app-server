package net.proselyte.jwtappdemo.rest;

import net.proselyte.jwtappdemo.dto.ObjectToTotalOperations;
import net.proselyte.jwtappdemo.service.TotalService;
import net.proselyte.jwtappdemo.service.UserService;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/total/")
public class TotalRestController {

    private final UserService userService;

    private final TotalService totalService;

    public TotalRestController(UserService userService, TotalService totalService) {
        this.userService = userService;
        this.totalService = totalService;
    }

    @GetMapping(value = "today/purchases/user/{id}")
    public ResponseEntity<List<ObjectToTotalOperations>> getTotalPurchasesTodayById(@RequestHeader Map<String,String> headers,
                                                                                    @PathVariable(name = "id") Long id)
            throws UnsupportedEncodingException, JSONException {
        if(!userService.checkingAccessRights(id, headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        List<ObjectToTotalOperations> list = totalService.readPurchasesTotalToday(id);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "today/incomes/user/{id}")
    public ResponseEntity<List<ObjectToTotalOperations>> getTotalIncomesTodayById(@RequestHeader Map<String,String> headers,
                                                                                  @PathVariable(name = "id") Long id)
            throws UnsupportedEncodingException, JSONException {
        if(!userService.checkingAccessRights(id, headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        List<ObjectToTotalOperations> list = totalService.readIncomesTotalToday(id);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "week/purchases/user/{id}")
    public ResponseEntity<List<ObjectToTotalOperations>> getTotalPurchasesWeekById(@RequestHeader Map<String,String> headers,
                                                                                   @PathVariable(name = "id") Long id)
            throws UnsupportedEncodingException, JSONException {
        if(!userService.checkingAccessRights(id, headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        List<ObjectToTotalOperations> list = totalService.readPurchasesTotalWeek(id);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "week/incomes/user/{id}")
    public ResponseEntity<List<ObjectToTotalOperations>> getTotalIncomesWeekById(@RequestHeader Map<String,String> headers,
                                                                                 @PathVariable(name = "id") Long id)
            throws UnsupportedEncodingException, JSONException {
        if(!userService.checkingAccessRights(id, headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        List<ObjectToTotalOperations> list = totalService.readIncomesTotalWeek(id);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping(value = "month/purchases/user/{id}")
    public ResponseEntity<List<ObjectToTotalOperations>> getTotalPurchasesMonthById(@RequestHeader Map<String,String> headers,
                                                                                 @PathVariable(name = "id") Long id)
            throws UnsupportedEncodingException, JSONException {
        if(!userService.checkingAccessRights(id, headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        List<ObjectToTotalOperations> list = totalService.readPurchasesTotalMonth(id);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping(value = "month/incomes/user/{id}")
    public ResponseEntity<List<ObjectToTotalOperations>> getTotalIncomesMonthById(@RequestHeader Map<String,String> headers,
                                                                                    @PathVariable(name = "id") Long id)
            throws UnsupportedEncodingException, JSONException {
        if(!userService.checkingAccessRights(id, headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        List<ObjectToTotalOperations> list = totalService.readIncomesTotalMonth(id);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "year/purchases/user/{id}")
    public ResponseEntity<List<ObjectToTotalOperations>> getTotalPurchasesYearById(@RequestHeader Map<String,String> headers,
                                                                                  @PathVariable(name = "id") Long id)
            throws UnsupportedEncodingException, JSONException {
        if(!userService.checkingAccessRights(id, headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        List<ObjectToTotalOperations> list = totalService.readPurchasesTotalYear(id);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "year/incomes/user/{id}")
    public ResponseEntity<List<ObjectToTotalOperations>> getTotalIncomesYearById(@RequestHeader Map<String,String> headers,
                                                                                   @PathVariable(name = "id") Long id)
            throws UnsupportedEncodingException, JSONException {
        if(!userService.checkingAccessRights(id, headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        List<ObjectToTotalOperations> list = totalService.readIncomesTotalYear(id);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
