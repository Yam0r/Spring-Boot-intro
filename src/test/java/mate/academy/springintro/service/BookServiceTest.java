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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        MockitoAnnotations.openMocks(this);

        createBookRequestDto = new CreateBookRequestDto();
        createBookRequestDto.setCategoryIds(Arrays.asList(1L, 2L));

        Category category1 = new Category();
        category1.setId(1L);
        Category category2 = new Category();
        category2.setId(2L);

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setCategories(Set.of(category1, category2));

        bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Test Book");
    }

    @Test
    void save_Success() {
        when(categoryRepository.findAllById(createBookRequestDto.getCategoryIds()))
                .thenReturn(Arrays.asList(new Category(), new Category()));
        when(bookMapper.toModel(createBookRequestDto)).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toBookDto(book)).thenReturn(bookDto);

        BookDto result = bookService.save(createBookRequestDto);

        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void save_CategoryNotFound() {
        when(categoryRepository.findAllById(createBookRequestDto.getCategoryIds()))
                .thenReturn(Arrays.asList());
        assertThrows(EntityNotFoundException.class, () -> bookService.save(createBookRequestDto));
    }

    @Test
    void findById_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toBookDto(book)).thenReturn(bookDto);
        BookDto result = bookService.findById(1L);
        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void findById_BookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> bookService.findById(1L));
    }

    @Test
    void updateBookById_Success() {
        CreateBookRequestDto updateDto = new CreateBookRequestDto();
        updateDto.setCategoryIds(Arrays.asList(1L, 2L));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toModel(updateDto)).thenReturn(book);
        bookService.updateBookById(1L, updateDto);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void deleteById_Success() {
        doNothing().when(bookRepository).deleteById(1L);
        bookService.deleteById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void findByCategoryId_Success() {
        // Подготовка данных: книги с несколькими категориями
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Science Fiction");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Fantasy");

        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book 1");
        book1.setCategories(Set.of(category1));

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book 2");
        book2.setCategories(Set.of(category2));

        BookDto bookDto1 = new BookDto();
        bookDto1.setId(1L);
        bookDto1.setTitle("Book 1");

        BookDto bookDto2 = new BookDto();
        bookDto2.setId(2L);
        bookDto2.setTitle("Book 2");

        when(bookRepository.findAllByCategoriesId(1L)).thenReturn(Arrays.asList(book1, book2));
        when(bookMapper.toBookDto(book1)).thenReturn(bookDto1);
        when(bookMapper.toBookDto(book2)).thenReturn(bookDto2);

        List<BookDto> result = bookService.findByCategoryId(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals("Book 1", result.get(0).getTitle());
        assertEquals("Book 2", result.get(1).getTitle());

        verify(bookRepository, times(1)).findAllByCategoriesId(1L);
    }
}
