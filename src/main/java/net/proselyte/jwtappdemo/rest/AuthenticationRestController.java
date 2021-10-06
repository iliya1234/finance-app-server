package net.proselyte.jwtappdemo.rest;

import net.proselyte.jwtappdemo.dto.AuthenticationRequestDto;
import net.proselyte.jwtappdemo.dto.UserRegisterDto;
import net.proselyte.jwtappdemo.model.User;
import net.proselyte.jwtappdemo.security.jwt.JwtTokenProvider;
import net.proselyte.jwtappdemo.service.CategoryService;
import net.proselyte.jwtappdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/auth/")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, CategoryService categoryService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.categoryService = categoryService;
    }
    @PostMapping("register")
    public ResponseEntity register(@RequestBody UserRegisterDto userRegisterDto){
        try {
            Map<Object,Object> response = new HashMap<>();
            if(userService.findByUsername(userRegisterDto.getUsername())!=null){
                response.put("message","Error: username is exist");
                return ResponseEntity.badRequest().body(response);
            }
            if(userService.findByEmail(userRegisterDto.getEmail())!=null){
                response.put("message","Error: Email is exist");
                return ResponseEntity.badRequest().body(response);
            }
            User user = userService.register(userRegisterDto.toUser());
            if(user == null){
                response.put("message", "Registration error");
                return ResponseEntity.badRequest().body(response);
            }
            authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(
                            userRegisterDto.getUsername(), userRegisterDto.getPassword()));
            String token = jwtTokenProvider.createToken(userRegisterDto.getUsername(), user.getRoles());

            response.put("username", userRegisterDto.getUsername());
            response.put("token", token);
            return ResponseEntity.ok(response);

        }catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid registered");
        }
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
