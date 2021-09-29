package net.proselyte.jwtappdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import net.proselyte.jwtappdemo.model.*;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubcategoryDto {
    private Long id;
    private String name;
    private Long id_category;
    private Type type;

    public Subcategory toSubcategory(Category category){
        Subcategory subcategory = new Subcategory();
        subcategory.setId(id);
        subcategory.setName(name);
        subcategory.setCategory(category);
        subcategory.setType(type);
        return subcategory;
    }

    public static SubcategoryDto fromSubcategory(Subcategory subcategory) {
        SubcategoryDto subcategoryDto = new SubcategoryDto();
        subcategoryDto.setId(subcategory.getId());
        subcategoryDto.setName(subcategory.getName());
        subcategoryDto.setId_category(subcategory.getCategory().getId());
        subcategoryDto.setType(subcategory.getType());
        return subcategoryDto;
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
