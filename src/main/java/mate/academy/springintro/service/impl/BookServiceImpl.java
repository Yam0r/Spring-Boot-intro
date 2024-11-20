package mate.academy.springintro.service.impl;

import java.util.HashSet;
import java.util.List;
import lombok.AllArgsConstructor;
import mate.academy.springintro.dto.book.BookDto;
import mate.academy.springintro.dto.book.BookSearchParameters;
import mate.academy.springintro.dto.category.CreateBookRequestDto;
import mate.academy.springintro.exception.EntityNotFoundException;
import mate.academy.springintro.mapper.BookMapper;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.model.Category;
import mate.academy.springintro.repository.CategoryRepository;
import mate.academy.springintro.repository.book.BookRepository;
import mate.academy.springintro.repository.book.BookSpecificationBuilder;
import mate.academy.springintro.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;
    private final CategoryRepository categoryRepository;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        List<Category> categories = categoryRepository.findAllById(requestDto.getCategoryIds());
        if (categories.isEmpty()) {
            throw new EntityNotFoundException("One or more categories not found");
        }
        Book book = bookMapper.toModel(requestDto);
        book.setCategories(new HashSet<>(categories));
        book = bookRepository.save(book);
        return bookMapper.toBookDto(book);
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(bookMapper::toBookDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find book by id " + id));
        return bookMapper.toBookDto(book);
    }

    @Override
    public void updateBookById(Long id, CreateBookRequestDto requestDto) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find book by id " + id));
        bookMapper.updateBookFromDto(requestDto, book);
        bookRepository.save(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> search(BookSearchParameters params, Pageable pageable) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(params);
        return bookRepository.findAll(bookSpecification).stream()
                .map(bookMapper::toBookDto)
                .toList();
    }

    @Override
    public List<BookDto> findByCategoryId(Long categoryId) {
        List<Book> books = bookRepository.findAllByCategoriesId(categoryId);
        return books.stream()
                .map(bookMapper::toBookDto)
                .toList();
    }
}
