package mate.academy.springintro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mate.academy.springintro.dto.book.BookDto;
import mate.academy.springintro.dto.category.CreateBookRequestDto;
import mate.academy.springintro.exception.EntityNotFoundException;
import mate.academy.springintro.mapper.BookMapper;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.model.Category;
import mate.academy.springintro.repository.CategoryRepository;
import mate.academy.springintro.repository.book.BookRepository;
import mate.academy.springintro.repository.book.BookSpecificationBuilder;
import mate.academy.springintro.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookSpecificationBuilder bookSpecificationBuilder;

    @InjectMocks
    private BookServiceImpl bookService;

    private CreateBookRequestDto createBookRequestDto;
    private Book book;
    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        createBookRequestDto = new CreateBookRequestDto();
        createBookRequestDto.setCategoryIds(Arrays.asList(1L, 2L));

        Category scienceFictionCategory = new Category();
        scienceFictionCategory.setId(1L);

        Category fantasyCategory = new Category();
        fantasyCategory.setId(2L);

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setCategories(Set.of(scienceFictionCategory, fantasyCategory));

        bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Test Book");
    }

    @Test
    void save_WithValidRequest_ReturnSavedBookDto() {
        when(categoryRepository.findAllById(createBookRequestDto.getCategoryIds()))
                .thenReturn(Arrays.asList(new Category(), new Category()));
        when(bookMapper.toModel(createBookRequestDto)).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toBookDto(book)).thenReturn(bookDto);

        BookDto result = bookService.save(createBookRequestDto);

        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void save_WithNonexistentCategoryIds_ThrowEntityNotFoundException() {
        when(categoryRepository.findAllById(createBookRequestDto.getCategoryIds()))
                .thenReturn(Arrays.asList());
        assertThrows(EntityNotFoundException.class, () -> bookService.save(createBookRequestDto));
    }

    @Test
    void findById_WithExistingId_ReturnBookDto() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toBookDto(book)).thenReturn(bookDto);
        BookDto result = bookService.findById(1L);
        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WithNonexistentId_ThrowEntityNotFoundException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> bookService.findById(1L));
    }

    @Test
    void updateBookById_WithValidRequest_UpdateBookAndSaveToRepository() {
        CreateBookRequestDto updateDto = new CreateBookRequestDto();
        updateDto.setCategoryIds(Arrays.asList(1L, 2L));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        bookService.updateBookById(1L, updateDto);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void deleteById_WithExistingId_DeleteBookFromRepository() {
        doNothing().when(bookRepository).deleteById(1L);
        bookService.deleteById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void findByCategoryId_WithValidCategoryId_ReturnBooksList() {
        Category scienceFictionCategory = new Category();
        scienceFictionCategory.setId(1L);
        scienceFictionCategory.setName("Science Fiction");

        Category fantasyCategory = new Category();
        fantasyCategory.setId(2L);
        fantasyCategory.setName("Fantasy");

        Book scienceFictionBook = new Book();
        scienceFictionBook.setId(1L);
        scienceFictionBook.setTitle("Dune");
        scienceFictionBook.setCategories(Set.of(scienceFictionCategory));

        Book fantasyBook = new Book();
        fantasyBook.setId(2L);
        fantasyBook.setTitle("The Hobbit");
        fantasyBook.setCategories(Set.of(fantasyCategory));

        BookDto scienceFictionBookDto = new BookDto();
        scienceFictionBookDto.setId(1L);
        scienceFictionBookDto.setTitle("Dune");

        BookDto fantasyBookDto = new BookDto();
        fantasyBookDto.setId(2L);
        fantasyBookDto.setTitle("The Hobbit");

        when(bookRepository.findAllByCategoriesId(1L)).thenReturn(Arrays.asList(scienceFictionBook,
                fantasyBook));
        when(bookMapper.toBookDto(scienceFictionBook)).thenReturn(scienceFictionBookDto);
        when(bookMapper.toBookDto(fantasyBook)).thenReturn(fantasyBookDto);

        List<BookDto> result = bookService.findByCategoryId(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals("Dune", result.get(0).getTitle());
        assertEquals("The Hobbit", result.get(1).getTitle());

        verify(bookRepository, times(1)).findAllByCategoriesId(1L);
    }
}
