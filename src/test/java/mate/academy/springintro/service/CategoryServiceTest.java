package mate.academy.springintro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import mate.academy.springintro.dto.category.CategoryRequestDto;
import mate.academy.springintro.dto.category.CategoryResponseDto;
import mate.academy.springintro.mapper.CategoryMapper;
import mate.academy.springintro.model.Category;
import mate.academy.springintro.repository.CategoryRepository;
import mate.academy.springintro.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryRequestDto categoryRequestDto;
    private CategoryResponseDto categoryResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryRequestDto = new CategoryRequestDto();
        categoryRequestDto.setName("Science Fiction");

        categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setName("Science Fiction");

        category = new Category();
        category.setId(1L);
        category.setName("Science Fiction");
    }

    @Test
    void getById_Success() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryResponseDto);
        CategoryResponseDto result = categoryService.getById(1L);
        assertNotNull(result);
        assertEquals("Science Fiction", result.getName());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void getById_CategoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> categoryService.getById(1L));
    }

    @Test
    void save_Success() {
        when(categoryMapper.toEntity(categoryRequestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryResponseDto);
        CategoryResponseDto result = categoryService.save(categoryRequestDto);
        assertNotNull(result);
        assertEquals("Science Fiction", result.getName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void update_CategoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> categoryService.update(1L, categoryRequestDto));
    }

    @Test
    void deleteById_Success() {
        when(categoryRepository.existsById(1L)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(1L);
        categoryService.deleteById(1L);
        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_CategoryNotFound() {
        when(categoryRepository.existsById(1L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> categoryService.deleteById(1L));
    }
}
