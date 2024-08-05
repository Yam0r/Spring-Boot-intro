package mate.academy.springintro.model;

import mate.academy.springintro.dto.BookRequestDto;
import mate.academy.springintro.dto.BookResponseDto;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public CreatedBookDto toEntity(BookRequestDto dto) {
        CreatedBookDto book = new CreatedBookDto();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPrice(dto.getPrice());
        book.setDescription(dto.getDescription());
        book.setCoverImage(dto.getCoverImage());
        return book;
    }

    public BookResponseDto toDto(CreatedBookDto book) {
        BookResponseDto dto = new BookResponseDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setPrice(book.getPrice());
        dto.setDescription(book.getDescription());
        dto.setCoverImage(book.getCoverImage());
        return dto;
    }

    public void updateEntity(BookRequestDto dto, CreatedBookDto book) {
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPrice(dto.getPrice());
        book.setDescription(dto.getDescription());
        book.setCoverImage(dto.getCoverImage());
    }
}
