package mate.academy.springintro.mapper;

import mate.academy.springintro.config.MapperConfig;
import mate.academy.springintro.dto.book.BookDto;
import mate.academy.springintro.dto.category.CreateBookRequestDto;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toBookDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    void updateBookFromDto(CreateBookRequestDto requestDto, @MappingTarget Book entity);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        if (book.getCategories() == null) {
            return;
        }
        bookDto.setCategoryIds(
                book.getCategories().stream()
                        .map(Category::getId)
                        .toList()
        );
    }
}
