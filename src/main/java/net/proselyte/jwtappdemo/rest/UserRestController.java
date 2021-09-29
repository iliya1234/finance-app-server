package net.proselyte.jwtappdemo.rest;

import net.proselyte.jwtappdemo.dto.UserDto;
import net.proselyte.jwtappdemo.model.User;
import net.proselyte.jwtappdemo.service.UserService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/users/")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id){


        User user = userService.findById(id);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping(value = "decode/{id}")
    public ResponseEntity<String> getDecodeJwt(@RequestHeader Map<String,String> headers,
                                         @PathVariable(name = "id") Long id) throws UnsupportedEncodingException, JSONException {
        if(userService.checkingAccessRights(id,headers)==false)
            return new ResponseEntity<>("Error: you have no rights",HttpStatus.FORBIDDEN);
        else return new ResponseEntity<>(HttpStatus.OK);
    }
}
