package mate.academy.springintro.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springintro.model.CreatedBookDto;
import mate.academy.springintro.dto.BookDto;
import mate.academy.springintro.dto.BookRequestDto;
import mate.academy.springintro.dto.BookResponseDto;
import mate.academy.springintro.exception.EntityNotFoundException;
import mate.academy.springintro.mapper.BookMapper;
import mate.academy.springintro.repository.BookRepository;
import mate.academy.springintro.service.BookService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private static final String MESSAGE = "Can't find book by id";

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(BookRequestDto requestDto) {
        CreatedBookDto book = bookMapper.toModel(requestDto);
        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        List<CreatedBookDto> books = bookRepository.findAll();
        return books.stream().map(bookMapper::toBookDto).collect(Collectors.toList());
    }

    @Override
    public BookDto findById(Long id) {
        CreatedBookDto book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MESSAGE + id));
        return bookMapper.toBookDto(book);
    }

    @Override
    public BookDto updateBookById(Long id, BookResponseDto updateDto) {
        CreatedBookDto book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MESSAGE + id));
        bookMapper.updateBookFromDto(updateDto, book);
        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
