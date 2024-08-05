package mate.academy.springintro.service;

import java.util.List;
import java.util.Optional;
import mate.academy.springintro.model.CreatedBookDto;

public interface BookService {
    CreatedBookDto save(CreatedBookDto book);

    List<CreatedBookDto> findAll();

    Optional<CreatedBookDto> findById(Long id);

    void delete(Long id);

    void updateBook(Long id);
}

