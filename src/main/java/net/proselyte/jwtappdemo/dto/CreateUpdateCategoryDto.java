package net.proselyte.jwtappdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import net.proselyte.jwtappdemo.model.Category;
import net.proselyte.jwtappdemo.model.Type;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUpdateCategoryDto {
    private String name;
    private Type type;

    public Category toCategory() {
        Category category = new Category();
        category.setName(name);
        category.setType(type);
        return category;
    }

    public static CreateUpdateCategoryDto fromCategory(Category category) {
        CreateUpdateCategoryDto createUpdateCategoryDto = new CreateUpdateCategoryDto();
        createUpdateCategoryDto.setName(category.getName());
        createUpdateCategoryDto.setType(category.getType());
        return createUpdateCategoryDto;
    }
}
