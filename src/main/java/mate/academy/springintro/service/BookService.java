package mate.academy.springintro.service;

import java.util.List;
import mate.academy.springintro.dto.BookDto;
import mate.academy.springintro.dto.BookSearchParameters;
import mate.academy.springintro.dto.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void updateBookById(Long id, CreateBookRequestDto updateDto);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters searchParameters);
}
