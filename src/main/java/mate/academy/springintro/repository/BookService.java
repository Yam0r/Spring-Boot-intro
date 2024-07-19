package mate.academy.springintro.repository;

import java.util.List;
import java.util.Optional;
import mate.academy.springintro.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();

    Optional<Book> findById(Long id);

    void delete(Long id);
}
