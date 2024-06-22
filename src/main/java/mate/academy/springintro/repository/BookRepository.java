package mate.academy.springintro.repository;

import java.awt.print.Book;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository {
    Book save(Book book);

    List findAll();
}
