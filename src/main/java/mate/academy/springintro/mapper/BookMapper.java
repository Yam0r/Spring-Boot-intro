package mate.academy.springintro.mapper;

import mate.academy.springintro.config.MapperConfig;
import mate.academy.springintro.dto.BookDto;
import mate.academy.springintro.dto.CreateBookRequestDto;
import mate.academy.springintro.model.Book;
import org. mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toBookDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    void updateBookFromDto(CreateBookRequestDto requestDto, @MappingTarget Book entity);
}
