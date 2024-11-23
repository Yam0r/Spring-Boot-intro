package mate.academy.springintro.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.category.CategoryRequestDto;
import mate.academy.springintro.dto.category.CategoryResponseDto;
import mate.academy.springintro.mapper.CategoryMapper;
import mate.academy.springintro.model.Category;
import mate.academy.springintro.repository.CategoryRepository;
import mate.academy.springintro.service.CategoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponseDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return categoryRepository.findAll(pageable)
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with id "
                        + id
                        + " not found"));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryResponseDto save(CategoryRequestDto categoryResponseDto) {
        Category category = categoryMapper.toEntity(categoryResponseDto);
        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryResponseDto update(Long id, CategoryRequestDto categoryResponseDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with id "
                        + id
                        + " not found"));
        categoryMapper.updateCategoryFromDto(categoryResponseDto, category);

        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category with id " + id + " not found");
        }
        categoryRepository.deleteById(id);
    }
}
