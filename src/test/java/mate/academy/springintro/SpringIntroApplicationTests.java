package mate.academy.springintro;

import mate.academy.springintro.model.Book;
import mate.academy.springintro.repository.BookRepository;
import mate.academy.springintro.repository.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SpringIntroApplicationTests {

    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    public void testSaveBook() {
        // Создаем тестовую книгу
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("1234567890123");
        book.setPrice(BigDecimal.valueOf(19.99));
        book.setDescription("Test Description");
        book.setCoverImage("test_cover.jpg");

        // Указываем, что при вызове save в репозитории должен вернуться тот же объект
        when(bookRepository.save(book)).thenReturn(book);

        // Вызываем метод save у сервиса
        Book savedBook = (Book) bookService.save(book);

        // Проверяем, что сохраненная книга вернулась корректно
        assertEquals(book, savedBook);

        // Проверяем, что метод save был вызван ровно один раз в репозитории
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testFindAllBooks() {
        // Создаем список тестовых книг
        List<Book> books = new ArrayList<>();
        Book book1 = new Book();
        book1.setTitle("Test Book 1");
        book1.setAuthor("Test Author 1");
        book1.setIsbn("1234567890123");
        book1.setPrice(BigDecimal.valueOf(19.99));
        book1.setDescription("Test Description 1");
        book1.setCoverImage("test_cover_1.jpg");
        books.add(book1);

        Book book2 = new Book();
        book2.setTitle("Test Book 2");
        book2.setAuthor("Test Author 2");
        book2.setIsbn("1234567890124");
        book2.setPrice(BigDecimal.valueOf(29.99));
        book2.setDescription("Test Description 2");
        book2.setCoverImage("test_cover_2.jpg");
        books.add(book2);

        // Указываем, что при вызове findAll в репозитории должен вернуться этот список
        when(bookRepository.findAll()).thenReturn(books);

        // Вызываем метод findAll у сервиса
        List<java.awt.print.Book> foundBooks = bookService.findAll();

        // Проверяем, что список книг, возвращенный сервисом, соответствует ожидаемому списку
        assertEquals(books, foundBooks);

        // Проверяем, что метод findAll был вызван ровно один раз в репозитории
        verify(bookRepository, times(1)).findAll();
    }
}
