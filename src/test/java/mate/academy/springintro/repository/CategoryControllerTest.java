package mate.academy.springintro.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import mate.academy.springintro.dto.category.CategoryRequestDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    @DisplayName("Create a new category")
    @Sql(scripts = {"classpath:database/books/add-test-book.sql",
            "classpath:database/categories/add-test-category.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/clear-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void save_ValidRequest_Success() throws Exception {
        CategoryRequestDto requestDto = new CategoryRequestDto();
        requestDto.setName("Detective");
        requestDto.setDescription("Mystery books");
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/categories")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Detective"))
                .andExpect(jsonPath("$.description").value("Mystery books"));

        MvcResult result = mockMvc.perform(post("/categories")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    @DisplayName("Get all categories")
    @Sql(scripts = "classpath:database/categories/add-test-category.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/clear-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAll_Success() throws Exception {
        mockMvc.perform(get("/categories?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Fantasy"))
                .andExpect(jsonPath("$[0].description").value("Mystery books"));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    @DisplayName("Get category by ID")
    @Sql(scripts = "classpath:database/categories/add-test-category.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/clear-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getCategoryById_Success() throws Exception {
        mockMvc.perform(get("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fantasy"))
                .andExpect(jsonPath("$.description").value("Mystery books"));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    @DisplayName("Update category")
    @Sql(scripts = "classpath:database/categories/add-test-category.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/clear-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void updateCategory_Success() throws Exception {
        CategoryRequestDto requestDto = new CategoryRequestDto();
        requestDto.setName("Updated Detective");
        requestDto.setDescription("Updated Mystery books");
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(patch("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Detective"))
                .andExpect(jsonPath("$.description").value("Updated Mystery books"));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    @DisplayName("Delete category")
    @Sql(scripts = "classpath:database/categories/add-test-category.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/clear-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteCategory_Success() throws Exception {
        mockMvc.perform(delete("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
