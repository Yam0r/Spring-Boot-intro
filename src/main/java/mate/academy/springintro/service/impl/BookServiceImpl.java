package mate.academy.springintro.service.impl;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mate.academy.springintro.model.CreatedBookDto;
import mate.academy.springintro.repository.BookRepository;
import mate.academy.springintro.service.BookService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public CreatedBookDto save(CreatedBookDto book) {
        return bookRepository.save(book);
    }

    @Override
    public List<CreatedBookDto> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<CreatedBookDto> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void updateBook(Long id) {
        bookRepository.findById(id).ifPresent(book -> {
            book.setDeleted(true);
            bookRepository.save(book);
        });
    }
}

