package mate.academy.springintro.service;

import mate.academy.springintro.dto.BookDto;
import mate.academy.springintro.dto.BookRequestDto;
import mate.academy.springintro.dto.BookResponseDto;

import java.util.List;

public interface BookService {
    BookDto save(BookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    BookDto updateBookById(Long id, BookResponseDto updateDto);

    void deleteById(Long id);
}
