package mate.academy.springintro.repository;

import mate.academy.springintro.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    List<Category> findAllByDeletedFalse();
    Optional<Category> findByName(String name);
}

