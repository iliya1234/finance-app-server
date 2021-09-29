package net.proselyte.jwtappdemo.rest;

import net.proselyte.jwtappdemo.dto.CategoryDto;
import net.proselyte.jwtappdemo.dto.CreateUpdateSubcategoryDto;
import net.proselyte.jwtappdemo.dto.SubcategoryDto;
import net.proselyte.jwtappdemo.model.Category;
import net.proselyte.jwtappdemo.model.Subcategory;
import net.proselyte.jwtappdemo.service.CategoryService;
import net.proselyte.jwtappdemo.service.SubcategoryService;
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
@RequestMapping(value = "/api/subcategories/")
public class SubcategoryRestController {
    private final SubcategoryService subcategoryService;
    private final UserService userService;
    private final CategoryService categoryService;

    public SubcategoryRestController(SubcategoryService subcategoryService, UserService userService, CategoryService categoryService) {
        this.subcategoryService = subcategoryService;
        this.userService = userService;
        this.categoryService = categoryService;
    }
    @GetMapping(value = "all/user/{id}")
    public ResponseEntity<List<SubcategoryDto>> getAllSubcategoriesById(@RequestHeader Map<String,String> headers, @PathVariable(name = "id") Long id) throws UnsupportedEncodingException, JSONException {
        if(!userService.checkingAccessRights(id, headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        List<Subcategory> subcategoryList = subcategoryService.findByUserId(id);
        if (subcategoryList == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<SubcategoryDto> result = SubcategoryDto.fromListSubcategory(subcategoryList);
        if(result==null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping(value = "create/user/{id_user}")
    public ResponseEntity createSubcategory(@RequestBody CreateUpdateSubcategoryDto createUpdateSubcategoryDto,
                                            @PathVariable(name = "id_user") Long id_user,
                                            @RequestHeader Map<String,String> headers)
            throws UnsupportedEncodingException, JSONException {
        if(!userService.checkingAccessRights(id_user, headers))
            return new ResponseEntity("Error: you have no rights",HttpStatus.FORBIDDEN);
        Map<Object,Object> response = new HashMap<>();
        if(subcategoryService.findByName(createUpdateSubcategoryDto.getName(),id_user)!=null){
            response.put("message","Error: subcategory already created");
            return ResponseEntity.badRequest().body(response);
        }
        Category category = categoryService.findById(createUpdateSubcategoryDto.getId_category());
        if(category == null){
            response.put("message","Error: there is no category for subcategory");
            return ResponseEntity.badRequest().body(response);
        }
        Subcategory subcategory = subcategoryService.create(createUpdateSubcategoryDto.toSubcategory(category),
                id_user);
        if(subcategory == null){
            response.put("message","Error: failed to create subcategory");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("message", "Subcategory created successfully");
        return ResponseEntity.ok(response);
    }
    @GetMapping(value = "get/id_category/{id_subcategory}")
    public ResponseEntity<CategoryDto> getCategoryByIdUserAndIdCategory(@RequestHeader Map<String,String> headers,
                                                                        @PathVariable(name = "id_subcategory")
                                                                                Long id_subcategory)
            throws UnsupportedEncodingException, JSONException {
        Subcategory subcategory = subcategoryService.findById(id_subcategory);
        if (subcategory == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!userService.checkingAccessRights(subcategory.getUser().getId(), headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        SubcategoryDto result = SubcategoryDto.fromSubcategory(subcategory);
        return new ResponseEntity(result, HttpStatus.OK);
    }
    @DeleteMapping(value = "delete/id_subcategory/{id_subcategory}")
    public ResponseEntity deleteSubcategoryById(@RequestHeader Map<String,String> headers,
                                                @PathVariable(name = "id_subcategory") Long id_subcategory)
            throws UnsupportedEncodingException, JSONException {
        Subcategory subcategory = subcategoryService.findById(id_subcategory);
        if(subcategory== null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Long id_user = subcategory.getUser().getId();
        if (!userService.checkingAccessRights(id_user, headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        subcategoryService.delete(id_subcategory);
        return ResponseEntity.ok("Subcategory deleted successfully");
    }
    @PutMapping(value = "update/id_subcategory/{id_subcategory}")
    public ResponseEntity updateSubcategoryById(@RequestHeader Map<String,String> headers,
                                             @PathVariable(name = "id_subcategory") Long id_subcategory,
                                             @RequestBody CreateUpdateSubcategoryDto createUpdateSubcategoryDto
    ) throws UnsupportedEncodingException, JSONException {
        Subcategory subcategory = subcategoryService.findById(id_subcategory);
        if(subcategory== null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Map<Object,Object> response = new HashMap<>();
        if (!userService.checkingAccessRights(subcategory.getUser().getId(), headers))
            return new ResponseEntity("Error: you have no rights", HttpStatus.FORBIDDEN);
        Category category = categoryService.findById(createUpdateSubcategoryDto.getId_category());
        if(category == null){
            response.put("message","Error: there is no category for subcategory");
            return ResponseEntity.badRequest().body(response);
        }
        CreateUpdateSubcategoryDto result = CreateUpdateSubcategoryDto.fromSubcategory(subcategoryService.update(
                createUpdateSubcategoryDto.toSubcategory(category),
                id_subcategory,subcategory.getUser()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
