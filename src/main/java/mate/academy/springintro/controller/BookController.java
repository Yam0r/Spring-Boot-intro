package mate.academy.springintro.controller;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springintro.DTO.BookRequestDto;
import mate.academy.springintro.DTO.BookResponseDto;
import mate.academy.springintro.mapper.BookMapper;
import mate.academy.springintro.service.BookService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping
    public List<BookResponseDto> findAll() {
        return bookService.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> findById(@PathVariable Long id) {
        return bookService.findById(id)
                .map(bookMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public BookResponseDto create(@RequestBody BookRequestDto book) {
        return bookMapper.toDto(bookService.save(bookMapper.toEntity(book)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> update(@PathVariable Long id, @RequestBody BookRequestDto book) {
        return bookService.findById(id)
                .map(existingBook -> {
                    bookMapper.updateEntity(book, existingBook);
                    return ResponseEntity.ok(bookMapper.toDto(bookService.save(existingBook)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return bookService.findById(id)
                .map(book -> {
                    bookService.delete(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
