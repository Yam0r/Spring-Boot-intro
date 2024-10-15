package mate.academy.springintro.repository.book;

import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.book.BookSearchParameters;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.repository.SpecificationBuilder;
import mate.academy.springintro.repository.SpecificationProviderManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {

    private static final String AUTHOR_PARAMETER = "author";
    private static final String PRICE_PARAMETER = "price";
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters bookSearchParameters) {
        Specification<Book> spec = Specification.where(null);
        if (bookSearchParameters.author() != null && bookSearchParameters.author().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider(AUTHOR_PARAMETER)
                    .getSpecification(bookSearchParameters.author()));
        }
        if (bookSearchParameters.price() != null && bookSearchParameters.price().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider(PRICE_PARAMETER)
                    .getSpecification(bookSearchParameters.price()));
        }
        return spec;
    }
}
