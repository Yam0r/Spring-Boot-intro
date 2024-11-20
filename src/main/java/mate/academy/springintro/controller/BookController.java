package mate.academy.springintro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.book.BookDto;
import mate.academy.springintro.dto.book.BookSearchParameters;
import mate.academy.springintro.dto.category.CreateBookRequestDto;
import mate.academy.springintro.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Bookshop management", description = "Endpoints for managing books in the bookshop")
@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping
    @Operation(summary = "Get all books",
            description = "Retrieve a paginated list of all available books")
    public List<BookDto> getAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Retrieve a single book by its ID")
    public BookDto getBookById(@Parameter(description = "ID of the book to retrieve")
                               @PathVariable Long id) {
        return bookService.findById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @Operation(summary = "Create a new book", description = "Add a new book to the system")
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update a book", description = "Update an existing book by its ID")
    public void updateBookById(@Parameter(description = "ID of the book to update")
                               @PathVariable Long id,
                               @RequestBody @Valid CreateBookRequestDto updateDto) {
        bookService.updateBookById(id, updateDto);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book", description = "Delete an existing book by its ID")
    public void deleteBookById(@Parameter(description = "ID of the book to delete")
                               @PathVariable Long id) {
        bookService.deleteById(id);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping("/search")
    @Operation(summary = "Search for books", description = "Search for books by various criteria")
    public List<BookDto> search(
            @Parameter(description = "Search parameters")
            @ModelAttribute BookSearchParameters searchParameters,
            @Parameter(description = "Pagination information") Pageable pageable) {
        return bookService.search(searchParameters, pageable);
    }
}
