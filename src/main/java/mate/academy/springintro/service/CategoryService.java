package mate.academy.springintro.service;

import java.util.List;
import mate.academy.springintro.dto.category.CategoryRequestDto;
import mate.academy.springintro.dto.category.CategoryResponseDto;

public interface CategoryService {
    List<CategoryResponseDto> findAll(int page, int size);

    CategoryResponseDto getById(Long id);

    CategoryResponseDto save(CategoryRequestDto categoryResponseDto);

    CategoryResponseDto update(Long id, CategoryRequestDto categoryResponseDto);

    void deleteById(Long id);
}
