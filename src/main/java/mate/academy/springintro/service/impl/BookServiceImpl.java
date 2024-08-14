package mate.academy.springintro.service.impl;

import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.CreateBookRequestDto;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.dto.BookDto;
import mate.academy.springintro.exception.EntityNotFoundException;
import mate.academy.springintro.mapper.BookMapper;
import mate.academy.springintro.repository.BookRepository;
import mate.academy.springintro.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository; // Внедрение через final и Lombok
    private final BookMapper bookMapper; // Внедрение через final и Lombok

    @Override
    public BookDto save(CreateBookRequestDto requestDto) { // Изменен параметр на CreateBookRequestDto
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(bookMapper::toBookDto).collect(Collectors.toList());
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id " + id));
        return bookMapper.toBookDto(book);
    }

    @Override
    public BookDto updateBookById(Long id, CreateBookRequestDto requestDto) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id " + id));
        bookMapper.updateBookFromDto(requestDto, book);
        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
