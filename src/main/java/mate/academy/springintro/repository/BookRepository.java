package mate.academy.springintro.repository;

import mate.academy.springintro.model.BookDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookDto, Long> {
}
