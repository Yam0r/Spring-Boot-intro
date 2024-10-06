package mate.academy.springintro.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequestDto {
    @NotNull(message = "Title cannot be null")
    private String title;

    @NotNull(message = "Author cannot be null")
    private String author;

    @NotNull(message = "ISBN cannot be null")
    private String isbn;

    @NotNull
    @Min(value = 0)
    private BigDecimal price;

    private String description;

    private String coverImage;
}
