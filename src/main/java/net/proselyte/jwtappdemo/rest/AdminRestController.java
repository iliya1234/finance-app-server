package net.proselyte.jwtappdemo.rest;

import net.proselyte.jwtappdemo.dto.AdminUserDto;
import net.proselyte.jwtappdemo.dto.CategoryDto;
import net.proselyte.jwtappdemo.dto.UserRegisterDto;
import net.proselyte.jwtappdemo.model.Category;
import net.proselyte.jwtappdemo.model.User;
import net.proselyte.jwtappdemo.service.CategoryService;
import net.proselyte.jwtappdemo.service.OperationService;
import net.proselyte.jwtappdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping(value = "/api/admin/")
public class AdminRestController {

    private final UserService userService;
    private final OperationService operationService;
    private final CategoryService categoryService;

    @Autowired
    public AdminRestController(UserService userService, OperationService operationService, CategoryService categoryService) {
        this.userService = userService;
        this.operationService = operationService;
        this.categoryService = categoryService;
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        AdminUserDto result = AdminUserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping(value = "users/all")
    public ResponseEntity<List<UserRegisterDto>> getAllUsers() {
        List<User> listUsers = userService.getAll();
        if (listUsers == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<UserRegisterDto> result = UserRegisterDto.fromListUser(listUsers);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping(value = "users/category/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(name = "id") Long id) {
        Category category = categoryService.findById(id);
        if(category == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        CategoryDto result =  CategoryDto.fromCategory(category);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
