package net.proselyte.jwtappdemo.rest;

import net.proselyte.jwtappdemo.dto.CategoryDto;
import net.proselyte.jwtappdemo.dto.CreateUpdateCategoryDto;
import net.proselyte.jwtappdemo.model.Category;
import net.proselyte.jwtappdemo.service.CategoryService;
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
@RequestMapping(value = "/api/categories/")
public class CategoryRestController {
    private final CategoryService categoryService;
    private final UserService userService;

    public CategoryRestController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping(value = "all/user/{id}")
    public ResponseEntity<List<CategoryDto>> getAllCategoriesById(@RequestHeader Map<String,String> headers, @PathVariable(name = "id") Long id) throws UnsupportedEncodingException, JSONException {
        if(!userService.checkingAccessRights(id, headers))
            return new ResponseEntity("Error: you have no rights",HttpStatus.FORBIDDEN);
        List<Category> categoryList = categoryService.findByUserId(id);
        if (categoryList == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<CategoryDto> result = CategoryDto.fromListCategory(categoryList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping(value = "create/user/{id_user}")
    public ResponseEntity createCategory(@RequestBody CreateUpdateCategoryDto createUpdateCategoryDto, @PathVariable(name = "id_user")
            Long id_user, @RequestHeader Map<String,String> headers) throws UnsupportedEncodingException, JSONException {
        if(!userService.checkingAccessRights(id_user, headers))
            return new ResponseEntity("Error: you have no rights",HttpStatus.FORBIDDEN);
        Map<Object,Object> response = new HashMap<>();
        if(categoryService.findByName(createUpdateCategoryDto.getName(),id_user)!=null){
            response.put("message","Error: category already created");
            return ResponseEntity.badRequest().body(response);
        }
        Category category = categoryService.create(createUpdateCategoryDto.toCategory(),id_user);
        if(category == null){
            response.put("message","Error: failed to create category");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("message", "Category created successfully");
        return ResponseEntity.ok(response);
    }
    @GetMapping(value = "get/id_category/{id_category}")
    public ResponseEntity<CategoryDto> getCategoryByIdUserAndIdCategory(@RequestHeader Map<String,String> headers,
                                                                        @PathVariable(name = "id_category") Long id_category) throws UnsupportedEncodingException, JSONException {
        Category category = categoryService.findById(id_category);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!userService.checkingAccessRights(category.getUser().getId(), headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        CategoryDto result = CategoryDto.fromCategory(category);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @DeleteMapping(value = "delete/id_category/{id_category}")
    public ResponseEntity deleteCategoryById(@RequestHeader Map<String,String> headers,
                                             @PathVariable(name = "id_category") Long id_category) throws UnsupportedEncodingException, JSONException {
        Category category = categoryService.findById(id_category);
        if(category== null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Long id_user = category.getUser().getId();
        if (!userService.checkingAccessRights(id_user, headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        categoryService.delete(id_category);
        return ResponseEntity.ok("Category deleted successfully");
    }
    @PutMapping(value = "update/id_category/{id_category}")
    public ResponseEntity updateCategoryById(@RequestHeader Map<String,String> headers,
                                             @PathVariable(name = "id_category") Long id_category,
                                             @RequestBody CreateUpdateCategoryDto createUpdateCategoryDto
    ) throws UnsupportedEncodingException, JSONException {
        Category category = categoryService.findById(id_category);
        if(category== null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!userService.checkingAccessRights(category.getUser().getId(), headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        CreateUpdateCategoryDto result = CreateUpdateCategoryDto.fromCategory(categoryService.update(createUpdateCategoryDto.toCategory(),id_category,category.getUser()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
