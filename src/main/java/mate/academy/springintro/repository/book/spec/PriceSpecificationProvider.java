package mate.academy.springintro.repository.book.spec;

import java.util.Arrays;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PriceSpecificationProvider implements SpecificationProvider<Book> {
    private final String priceMessage = "price";

    public Specification<Book> getSpecification(String[] params) {
        return (root,
                query,
                criteriaBuilder) -> root.get(priceMessage)
                .in(Arrays.stream(params).toArray());
    }

    @Override
    public String getKey() {
        return priceMessage;
    }
}
