package mate.academy.springintro.repository.book.spec;

import java.util.Arrays;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    private static final String AUTHOR_PARAMETER = "author";

    public Specification<Book> getSpecification(String[] params) {
        return (root,
                query,
                criteriaBuilder) -> root.get(AUTHOR_PARAMETER)
                .in(Arrays.stream(params).toArray());
    }

    @Override
    public String getKey() {
        return AUTHOR_PARAMETER;
    }
}
