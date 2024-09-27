package mate.academy.springintro.dto;

import lombok.Data;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class BookDto {
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title should not exceed 100 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(max = 100, message = "Author should not exceed 100 characters")
    private String author;

    @NotBlank(message = "ISBN is required")
    @Size(max = 20, message = "ISBN should not exceed 20 characters")
    @Pattern(regexp = "\\d{13}", message = "ISBN must be a 13-digit number")
    private String isbn;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", inclusive = true, message = "Price must be at least 0.01")
    private BigDecimal price;

    @Size(max = 500, message = "Description should not exceed 500 characters")
    private String description;

    @Size(max = 255, message = "Cover image URL should not exceed 255 characters")
    private String coverImage;
}
