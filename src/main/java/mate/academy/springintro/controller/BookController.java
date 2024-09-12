package mate.academy.springintro.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.BookDto;
import mate.academy.springintro.dto.CreateBookRequestDto;
import mate.academy.springintro.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookshop")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    public BookDto createBook( @RequestBody CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @PutMapping("/{id}")
    public void updateBookById(@PathVariable Long id,@RequestBody CreateBookRequestDto updateDto) {
        bookService.updateBookById(id, updateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookById(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
