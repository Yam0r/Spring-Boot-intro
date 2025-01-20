package mate.academy.springintro.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.springintro.dto.book.BookDto;
import mate.academy.springintro.dto.category.CreateBookRequestDto;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.repository.book.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    @DisplayName("Create a new book")
    @Sql(scripts = {"classpath:database/books/add-test-book.sql",
            "classpath:database/categories/add-test-category.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/clear-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createBook_ValidRequest_ShouldReturnCreatedBook() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto()
                .setTitle("Grok algorithms")
                .setAuthor("Aditya Bhargava")
                .setIsbn("978-1612292231")
                .setPrice(BigDecimal.valueOf(9.99))
                .setCategoryIds(List.of(1L));

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(post("/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);

        BookDto expected = new BookDto()
                .setId(actual.getId())
                .setTitle(requestDto.getTitle())
                .setAuthor(requestDto.getAuthor())
                .setIsbn(requestDto.getIsbn())
                .setPrice(requestDto.getPrice())
                .setCategoryIds(requestDto.getCategoryIds());

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(authorities = {"USER", "ADMIN"})
    @DisplayName("Get all books")
    @Sql(scripts = "classpath:database/books/add-test-book.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/clear-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllBooks_WithExistingBooks_ReturnBooksList() throws Exception {
        mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Grok algorithms"));
    }

    @Test
    @WithMockUser(authorities = {"USER", "ADMIN"})
    @DisplayName("Get book by ID")
    @Sql(scripts = {"classpath:database/clear-table.sql",
            "classpath:database/books/add-test-book.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/clear-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getBookById_WithValidId_ReturnBookDto() throws Exception {
        BookDto expected = new BookDto()
                .setId(1L)
                .setTitle("Grok algorithms")
                .setAuthor("Aditya Bhargava")
                .setIsbn("978-1612292231")
                .setPrice(BigDecimal.valueOf(9.99))
                .setDescription("Learn algorithms visually!")
                .setCoverImage("image_url")
                .setCategoryIds(List.of(1L));

        MvcResult result = mockMvc.perform(get("/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);

        assertEquals(expected.getId(), actual.getId(), "The book ID should match");
        assertEquals(expected.getTitle(), actual.getTitle(), "The book title should match");
        assertEquals(expected.getAuthor(), actual.getAuthor(), "The book author should match");
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    @DisplayName("Update a book")
    @Sql(scripts = {"classpath:database/books/add-test-book.sql",
            "classpath:database/categories/add-test-category.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/clear-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void updateBook_WithValidRequest_UpdateBookAndReturnUpdatedDto() throws Exception {
        CreateBookRequestDto updateDto = new CreateBookRequestDto()
                .setTitle("Updated Title")
                .setAuthor("Updated Author")
                .setIsbn("1234567890")
                .setPrice(BigDecimal.valueOf(19.99))
                .setCategoryIds(List.of(1L));

        String jsonRequest = objectMapper.writeValueAsString(updateDto);

        mockMvc.perform(put("/books/1")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Optional<Book> updatedBook = bookRepository.findById(1L);
        assertTrue(updatedBook.isPresent(), "Book should be present in the repository");

        Book actualBook = updatedBook.get();
        assertEquals("Updated Title", actualBook.getTitle());
        assertEquals("Updated Author", actualBook.getAuthor());
        assertEquals("1234567890", actualBook.getIsbn());
        assertEquals(BigDecimal.valueOf(19.99), actualBook.getPrice());
    }
}
