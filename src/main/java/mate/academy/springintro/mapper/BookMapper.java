package mate.academy.springintro.mapper;

import java.util.List;
import mate.academy.springintro.dto.BookDto;
import mate.academy.springintro.dto.CreateBookRequestDto;
import mate.academy.springintro.model.Book; // Изменено на правильную модель Book
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {
    BookDto toBookDto(Book book);

    List<BookDto> toBookDto(List<Book> book);

    Book toModel(CreateBookRequestDto requestDto); // Изменен параметр на CreateBookRequestDto

    void updateBookFromDto(CreateBookRequestDto book, @MappingTarget Book entity);
}
