package mate.academy.springintro.service;

import java.util.List;
import mate.academy.springintro.dto.BookDto;
import mate.academy.springintro.dto.BookSearchParameters;
import mate.academy.springintro.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    void updateBookById(Long id, CreateBookRequestDto updateDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters searchParameters);
}
