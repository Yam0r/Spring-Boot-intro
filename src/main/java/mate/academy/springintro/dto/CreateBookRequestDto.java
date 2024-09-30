package mate.academy.springintro.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequestDto {
    @NotBlank(message = "Title cannot be null")
    private String title;

    @NotBlank(message = "Author cannot be null")
    private String author;

    @NotBlank(message = "ISBN cannot be null")
    private String isbn;

    @NotBlank
    @Min(value = 0)
    private BigDecimal price;

    @NotBlank
    private String description;

    @NotBlank
    private String coverImage;
}
