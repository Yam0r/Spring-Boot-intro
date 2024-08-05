package mate.academy.springintro.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.springintro.model.CreatedBookDto;
import mate.academy.springintro.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<CreatedBookDto> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreatedBookDto> findById(@PathVariable Long id) {
        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CreatedBookDto create(@RequestBody CreatedBookDto book) {
        return bookService.save(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreatedBookDto> update(@PathVariable Long id,
                                                 @RequestBody CreatedBookDto book) {
        return bookService.findById(id)
                .map(existingBook -> {
                    existingBook.setTitle(book.getTitle());
                    existingBook.setAuthor(book.getAuthor());
                    existingBook.setIsbn(book.getIsbn());
                    existingBook.setPrice(book.getPrice());
                    existingBook.setDescription(book.getDescription());
                    existingBook.setCoverImage(book.getCoverImage());
                    return ResponseEntity.ok(bookService.save(existingBook));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return bookService.findById(id)
                .map(book -> {
                    bookService.updateBook(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
