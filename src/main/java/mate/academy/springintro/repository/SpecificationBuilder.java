package mate.academy.springintro.repository;

import mate.academy.springintro.dto.BookSearchParameters;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(
            BookSearchParameters searchParameters);
}
