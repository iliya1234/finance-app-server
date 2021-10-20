package net.proselyte.jwtappdemo.rest;

import com.google.common.base.Strings;
import net.proselyte.jwtappdemo.dto.CategoryDto;
import net.proselyte.jwtappdemo.dto.CreateUpdateCategoryDto;
import net.proselyte.jwtappdemo.model.Category;
import net.proselyte.jwtappdemo.security.jwt.JwtTokenProvider;
import net.proselyte.jwtappdemo.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/categories")
public class CategoryRestController {
    private final CategoryService categoryService;
    private final JwtTokenProvider jwtTokenProvider;

    public CategoryRestController(CategoryService categoryService, JwtTokenProvider jwtTokenProvider) {
        this.categoryService = categoryService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private String dataChecking(CreateUpdateCategoryDto createUpdateCategoryDto) {
        String message;
        if (Strings.isNullOrEmpty(createUpdateCategoryDto.getName())) {
            message = "Название категории не может быть пустым";
            return message;
        }
        if (createUpdateCategoryDto.getType() == null) {
            message = "Тип категории не может быть пустым";
            return message;
        }
        return null;
    }

    @GetMapping
    public ResponseEntity getAllCategories(HttpServletRequest request) {
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        List<Category> categoryList = categoryService.findByUserName(username);
        if (categoryList == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<CategoryDto> result = CategoryDto.fromListCategory(categoryList);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createCategory(@RequestBody CreateUpdateCategoryDto createUpdateCategoryDto,
                                         HttpServletRequest request) {
        Map<String, String> response = new HashMap<>();
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        String message = dataChecking(createUpdateCategoryDto);
        if (message != null) {
            response.put("message", message);
            return ResponseEntity.badRequest().body(response);
        }
        if (categoryService.findByNameAndUsername(createUpdateCategoryDto.getName(), username) != null) {
            response.put("message", "Категория уже создана");
            return ResponseEntity.badRequest().body(response);
        }
        Category category = categoryService.create(createUpdateCategoryDto.toCategory(), username);
        if (category == null) {
            response.put("message", "Ошибка при создании категории");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("message", "Категория успешно создана!");
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id_category}")
    public ResponseEntity getCategory(HttpServletRequest request,
                                      @PathVariable(name = "id_category") Long id_category) {
        Map<String, String> response = new HashMap<>();
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        Category category = categoryService.findById(id_category);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!username.equals(category.getUser().getUsername())) {
            response.put("message", "У вас недостаточно прав");
            return new ResponseEntity(response, HttpStatus.FORBIDDEN);
        }
        CategoryDto result = CategoryDto.fromCategory(category);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id_category}")
    public ResponseEntity deleteCategory(HttpServletRequest request,
                                         @PathVariable(name = "id_category") Long id_category) {
        Map<String, String> response = new HashMap<>();
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        Category category = categoryService.findById(id_category);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!username.equals(category.getUser().getUsername())) {
            response.put("message", "У вас недостаточно прав");
            return new ResponseEntity(response, HttpStatus.FORBIDDEN);
        }
        categoryService.delete(id_category);
        response.put("message", "Категория успешно удалена!");
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id_category}")
    public ResponseEntity updateCategory(HttpServletRequest request,
                                         @PathVariable(name = "id_category") Long id_category,
                                         @RequestBody CreateUpdateCategoryDto createUpdateCategoryDto
    ) {
        Map<String, String> response = new HashMap<>();
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        Category category = categoryService.findById(id_category);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!username.equals(category.getUser().getUsername())) {
            response.put("message", "У вас недостаточно прав");
            return new ResponseEntity(response, HttpStatus.FORBIDDEN);
        }
        String message = dataChecking(createUpdateCategoryDto);
        if (message != null) {
            response.put("message", message);
            return ResponseEntity.badRequest().body(response);
        }
        Category updateCategory = categoryService.update(
                createUpdateCategoryDto.toCategory(), id_category, category.getUser());
        if (updateCategory == null) {
            response.put("message", "Такая категория уже соществует");
            return ResponseEntity.badRequest().body(response);
        }
        CreateUpdateCategoryDto result = CreateUpdateCategoryDto.fromCategory(updateCategory);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
