package mate.academy.springintro.service;

import mate.academy.springintro.model.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
