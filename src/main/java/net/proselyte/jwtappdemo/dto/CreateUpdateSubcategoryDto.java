package net.proselyte.jwtappdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import net.proselyte.jwtappdemo.model.Category;
import net.proselyte.jwtappdemo.model.Subcategory;
import net.proselyte.jwtappdemo.model.Type;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUpdateSubcategoryDto {
    private String name;
    private Long id_category;
    private Type type;

    public Subcategory toSubcategory(Category category){
        Subcategory subcategory = new Subcategory();
        subcategory.setName(name);
        subcategory.setCategory(category);
        subcategory.setType(type);
        return subcategory;
    }

    public static CreateUpdateSubcategoryDto fromSubcategory(Subcategory subcategory) {
        CreateUpdateSubcategoryDto createUpdateSubcategoryDto = new CreateUpdateSubcategoryDto();
        createUpdateSubcategoryDto.setName(subcategory.getName());
        createUpdateSubcategoryDto.setId_category(subcategory.getCategory().getId());
        createUpdateSubcategoryDto.setType(subcategory.getType());
        return createUpdateSubcategoryDto;
    }
    public static List<SubcategoryDto> fromListSubcategory(List<Subcategory> subcategoryList){
        List<SubcategoryDto> subcategoryDtoList = new ArrayList<>();
        for(Subcategory subcategory : subcategoryList){
            SubcategoryDto subcategoryDto = SubcategoryDto.fromSubcategory(subcategory);
            subcategoryDtoList.add(subcategoryDto);
        }
        return subcategoryDtoList;
    }
}
