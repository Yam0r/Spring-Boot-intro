package mate.academy.springintro.mapper;

import jakarta.validation.Valid;
import mate.academy.springintro.dto.category.CategoryRequestDto;
import mate.academy.springintro.dto.category.CategoryResponseDto;
import mate.academy.springintro.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDto toDto(Category category);

    Category toEntity(CategoryRequestDto categoryResponseDto);

    void updateCategoryFromDto(@Valid CategoryRequestDto categoryResponseDto,
                               @MappingTarget Category category);
}
