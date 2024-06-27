package mate.academy.springintro.service;

import mate.academy.springintro.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
    Book save(Book book);

    List<Book> findAll();
}
