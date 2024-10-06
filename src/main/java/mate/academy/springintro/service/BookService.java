package mate.academy.springintro.service;

import java.util.List;
import mate.academy.springintro.dto.book.BookDto;
import mate.academy.springintro.dto.book.BookSearchParameters;
import mate.academy.springintro.dto.book.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    void updateBookById(Long id, CreateBookRequestDto updateDto);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters searchParameters);
}
