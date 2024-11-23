package mate.academy.springintro.mapper;

import mate.academy.springintro.dto.category.CategoryRequestDto;
import mate.academy.springintro.dto.category.CategoryResponseDto;
import mate.academy.springintro.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDto toDto(Category category);

    Category toEntity(CategoryRequestDto categoryResponseDto);

    void updateCategoryFromDto(CategoryRequestDto categoryResponseDto,
                               @MappingTarget Category category);
}
