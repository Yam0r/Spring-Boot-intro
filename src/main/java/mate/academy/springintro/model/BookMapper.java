package mate.academy.springintro.mapper;

import mate.academy.springintro.model.BookDto;
import mate.academy.springintro.DTO.BookRequestDto;
import mate.academy.springintro.DTO.BookResponseDto;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public BookDto toEntity(BookRequestDto dto) {
        BookDto book = new BookDto();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPrice(dto.getPrice());
        book.setDescription(dto.getDescription());
        book.setCoverImage(dto.getCoverImage());
        return book;
    }

    public BookResponseDto toDto(BookDto book) {
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

    public void updateEntity(BookRequestDto dto, BookDto book) {
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPrice(dto.getPrice());
        book.setDescription(dto.getDescription());
        book.setCoverImage(dto.getCoverImage());
    }
}
