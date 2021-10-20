package net.proselyte.jwtappdemo.rest;

import net.proselyte.jwtappdemo.dto.ObjectToTotalOperations;
import net.proselyte.jwtappdemo.security.jwt.JwtTokenProvider;
import net.proselyte.jwtappdemo.service.TotalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/api/total")
public class TotalRestController {

    private final TotalService totalService;
    private final JwtTokenProvider jwtTokenProvider;

    public TotalRestController(TotalService totalService, JwtTokenProvider jwtTokenProvider) {
        this.totalService = totalService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private ResponseEntity chekForNullList(List<ObjectToTotalOperations> result) {
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/purchases/today")
    public ResponseEntity getTotalPurchasesToday(HttpServletRequest request) {
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        List<ObjectToTotalOperations> result = totalService.readPurchasesTotalToday(username);
        return chekForNullList(result);
    }

    @GetMapping(value = "/incomes/today")
    public ResponseEntity getTotalIncomesToday(HttpServletRequest request) {
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        List<ObjectToTotalOperations> result = totalService.readIncomesTotalToday(username);
        return chekForNullList(result);
    }

    @GetMapping(value = "/purchases/week")
    public ResponseEntity getTotalPurchasesWeek(HttpServletRequest request) {
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        List<ObjectToTotalOperations> result = totalService.readPurchasesTotalWeek(username);
        return chekForNullList(result);
    }

    @GetMapping(value = "/incomes/week")
    public ResponseEntity getTotalIncomesWeek(HttpServletRequest request) {
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        List<ObjectToTotalOperations> result = totalService.readIncomesTotalWeek(username);
        return chekForNullList(result);
    }

    @GetMapping(value = "/purchases/month")
    public ResponseEntity getTotalPurchasesMonth(HttpServletRequest request) {
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        List<ObjectToTotalOperations> result = totalService.readPurchasesTotalMonth(username);
        return chekForNullList(result);
    }

    @GetMapping(value = "/incomes/month")
    public ResponseEntity getTotalIncomesMonth(HttpServletRequest request) {
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        List<ObjectToTotalOperations> result = totalService.readIncomesTotalMonth(username);
        return chekForNullList(result);
    }

    @GetMapping(value = "/purchases/year")
    public ResponseEntity getTotalPurchasesYear(HttpServletRequest request) {
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        List<ObjectToTotalOperations> result = totalService.readPurchasesTotalYear(username);
        return chekForNullList(result);
    }

    @GetMapping(value = "/incomes/year")
    public ResponseEntity getTotalIncomesYear(HttpServletRequest request) {
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        List<ObjectToTotalOperations> result = totalService.readIncomesTotalYear(username);
        return chekForNullList(result);
    }
}
