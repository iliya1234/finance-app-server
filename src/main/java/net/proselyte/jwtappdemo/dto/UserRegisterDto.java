package net.proselyte.jwtappdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import net.proselyte.jwtappdemo.model.User;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegisterDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    public static UserRegisterDto fromUser(User user) {
        UserRegisterDto userDto = new UserRegisterDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
    public static List<UserRegisterDto> fromListUser(List<User> userList){
        List<UserRegisterDto> userRegisterDtoList = new ArrayList<>();
        for(User user : userList){
            UserRegisterDto userRegisterDto = UserRegisterDto.fromUser(user);
            userRegisterDtoList.add(userRegisterDto);
        }
        return userRegisterDtoList;
    }
}
