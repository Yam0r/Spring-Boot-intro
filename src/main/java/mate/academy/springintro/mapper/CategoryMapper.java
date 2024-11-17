package mate.academy.springintro.mapper;

import mate.academy.springintro.dto.book.CategoryDto;
import mate.academy.springintro.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto categoryDto);
}

