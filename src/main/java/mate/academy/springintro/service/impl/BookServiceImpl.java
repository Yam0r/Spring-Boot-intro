package mate.academy.springintro.service.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.service.BookRepository;
import mate.academy.springintro.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findByDeletedFalse();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findByIdAndDeletedFalse(id);
    }

    @Override
    public void delete(Long id) {
        bookRepository.findById(id).ifPresent(book -> {
            book.setDeleted(true);
            bookRepository.save(book);
        });
    }
}
