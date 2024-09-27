package mate.academy.springintro.repository;

import mate.academy.springintro.model.CreatedBookDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<CreatedBookDto, Long> {

}
