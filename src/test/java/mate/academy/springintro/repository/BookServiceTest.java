package mate.academy.springintro.repository;

import mate.academy.springintro.repository.book.BookRepository;
import mate.academy.springintro.service.impl.BookServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;
}
