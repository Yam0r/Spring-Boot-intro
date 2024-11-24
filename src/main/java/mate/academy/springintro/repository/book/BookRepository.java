package mate.academy.springintro.repository.book;

import java.util.List;
import mate.academy.springintro.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long>,
        JpaSpecificationExecutor<Book> {
    List<Book> findAllByCategoriesId(Long categoryId);
}
