package mate.academy.springintro.repository;

import mate.academy.springintro.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    void softDeleteById(@Param("id") Long id);

    boolean existsByIdAndDeletedFalse(Long id);

}
