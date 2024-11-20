package mate.academy.springintro.service;

import jakarta.validation.Valid;
import java.util.List;
import mate.academy.springintro.dto.category.CategoryRequestDto;
import mate.academy.springintro.dto.category.CategoryResponseDto;

public interface CategoryService {
    List<CategoryResponseDto> findAll();

    CategoryResponseDto getById(Long id);

    CategoryResponseDto save(@Valid CategoryRequestDto categoryResponseDto);

    CategoryResponseDto update(Long id, @Valid CategoryRequestDto categoryResponseDto);

    void deleteById(Long id);
}
