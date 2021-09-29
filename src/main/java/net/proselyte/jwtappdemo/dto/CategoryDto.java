package net.proselyte.jwtappdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import net.proselyte.jwtappdemo.model.*;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDto {
    private Long id;
    private String name;
    private Type type;
    public Category toCategory(){
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setType(type);
        return category;
    }

    public static CategoryDto fromCategory(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setType(category.getType());
        return categoryDto;
    }
    public static List<CategoryDto> fromListCategory(List<Category> categoryList){
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for(Category category : categoryList){
            CategoryDto categoryDto = CategoryDto.fromCategory(category);
            categoryDtoList.add(categoryDto);
        }
        return categoryDtoList;
    }
}
