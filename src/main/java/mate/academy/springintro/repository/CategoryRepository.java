package mate.academy.springintro.repository;

import java.util.List;
import java.util.Optional;
import mate.academy.springintro.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Modifying
    @Query("UPDATE Category c SET c.deleted = true WHERE c.id = :id")
    void softDeleteById(@Param("id") Long id);

    List<Category> findAll();
}
