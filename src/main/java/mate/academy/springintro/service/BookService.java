package mate.academy.springintro.service;

import java.util.List;
import java.util.Optional;
import mate.academy.springintro.model.BookDto;

public interface BookService {
    BookDto save(BookDto book);

    List<BookDto> findAll();

    Optional<BookDto> findById(Long id);

    void delete(Long id);
}
