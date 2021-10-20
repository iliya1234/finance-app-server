package net.proselyte.jwtappdemo.rest;

import com.google.common.base.Strings;
import net.proselyte.jwtappdemo.dto.CategoryDto;
import net.proselyte.jwtappdemo.dto.CreateUpdateSubcategoryDto;
import net.proselyte.jwtappdemo.dto.SubcategoryDto;
import net.proselyte.jwtappdemo.model.Category;
import net.proselyte.jwtappdemo.model.Subcategory;
import net.proselyte.jwtappdemo.security.jwt.JwtTokenProvider;
import net.proselyte.jwtappdemo.service.CategoryService;
import net.proselyte.jwtappdemo.service.SubcategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/subcategories")
public class SubcategoryRestController {
    private final SubcategoryService subcategoryService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CategoryService categoryService;

    public SubcategoryRestController(SubcategoryService subcategoryService, JwtTokenProvider jwtTokenProvider, CategoryService categoryService) {
        this.subcategoryService = subcategoryService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.categoryService = categoryService;
    }

    private String dataChecking(CreateUpdateSubcategoryDto result){
        String message;
        if (Strings.isNullOrEmpty(result.getName())) {
            message = "Имя подкатегории не может быть пустым";
            return message;
        }
        if (result.getType() == null) {
            message = "Тип подкатегории не может быть пустым";
            return message;
        }
        if (result.getIdCategory() == null) {
            message = "Id категории не может быть пустым";
            return message;
        }
        Category category = categoryService.findById(result.getIdCategory());
        if (category == null) {
            message = "Нет категории для подкатегории";
            return message;
        }
        return null;
    }
    @GetMapping
    public ResponseEntity<List<SubcategoryDto>> getAllSubcategories(HttpServletRequest request) {
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        List<Subcategory> subcategoryList = subcategoryService.findByUserUsername(username);
        if (subcategoryList == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<SubcategoryDto> result = SubcategoryDto.fromListSubcategory(subcategoryList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createSubcategory
            (HttpServletRequest request, @RequestBody CreateUpdateSubcategoryDto createUpdateSubcategoryDto) {
        Map<String, String> response = new HashMap<>();
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        String message = dataChecking(createUpdateSubcategoryDto);
        if (message != null) {
            response.put("message", message);
            return ResponseEntity.badRequest().body(response);
        }
        if (subcategoryService.findByNameAndUsername(createUpdateSubcategoryDto.getName(), username) != null) {
            response.put("message", "Подкатегория уже создана");
            return ResponseEntity.badRequest().body(response);
        }
        Category category = categoryService.findById(createUpdateSubcategoryDto.getIdCategory());
        Subcategory result = subcategoryService.create(createUpdateSubcategoryDto.toSubcategory(category),
                username);
        if (result == null) {
            response.put("message", "Ошибка при создании подкатегории");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("message", "Подкатегория успешно создана!");
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id_subcategory}")
    public ResponseEntity<CategoryDto> getSubcategory(HttpServletRequest request,
                                                                          @PathVariable(name = "id_subcategory")
                                                                                  Long id_subcategory) {
        Map<String, String> response = new HashMap<>();
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        Subcategory subcategory = subcategoryService.findById(id_subcategory);
        if (subcategory == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!username.equals(subcategory.getUser().getUsername())) {
            response.put("message", "У вас недостаточно прав");
            return new ResponseEntity(response, HttpStatus.FORBIDDEN);
        }
        SubcategoryDto result = SubcategoryDto.fromSubcategory(subcategory);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id_subcategory}")
    public ResponseEntity deleteSubcategory(HttpServletRequest request,
                                                @PathVariable(name = "id_subcategory") Long id_subcategory) {
        Map<String, String> response = new HashMap<>();
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        Subcategory subcategory = subcategoryService.findById(id_subcategory);
        if (subcategory == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!username.equals(subcategory.getUser().getUsername())) {
            response.put("message", "У вас недостаточно прав");
            return new ResponseEntity(response, HttpStatus.FORBIDDEN);
        }
        subcategoryService.delete(id_subcategory);
        response.put("message", "Подкатегория успешно удалена!");
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id_subcategory}")
    public ResponseEntity updateSubcategory(HttpServletRequest request,
                                                @PathVariable(name = "id_subcategory") Long id_subcategory,
                                                @RequestBody CreateUpdateSubcategoryDto createUpdateSubcategoryDto) {
        Map<String, String> response = new HashMap<>();
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        String message = dataChecking(createUpdateSubcategoryDto);
        if(message != null){
            response.put("message", message);
            return ResponseEntity.badRequest().body(response);
        }
        Subcategory subcategory = subcategoryService.findById(id_subcategory);
        if (subcategory == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!username.equals(subcategory.getUser().getUsername())) {
            response.put("message", "У вас недостаточно прав");
            return new ResponseEntity(response, HttpStatus.FORBIDDEN);
        }
        Category category = categoryService.findById(createUpdateSubcategoryDto.getIdCategory());
        Subcategory updateSubcategory = subcategoryService.update(createUpdateSubcategoryDto.toSubcategory(category),
                id_subcategory,
                subcategory.getUser());
        if (updateSubcategory == null) {
            response.put("message", "Такая подкатегория уже существует");
            return ResponseEntity.badRequest().body(response);
        }
        CreateUpdateSubcategoryDto result = CreateUpdateSubcategoryDto.fromSubcategory(updateSubcategory);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
