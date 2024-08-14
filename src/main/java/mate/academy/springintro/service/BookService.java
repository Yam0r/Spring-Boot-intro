package mate.academy.springintro.service;

import mate.academy.springintro.dto.BookDto;
import mate.academy.springintro.dto.CreateBookRequestDto;


import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    BookDto updateBookById(Long id, CreateBookRequestDto updateDto);

    void deleteById(Long id);
}
