package mate.academy.springintro.service;

import java.util.List;
import java.util.Optional;
import mate.academy.springintro.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByDeletedFalse();

    Optional<Book> findByIdAndDeletedFalse(Long id);
}
