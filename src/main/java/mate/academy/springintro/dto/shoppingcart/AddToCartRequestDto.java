package mate.academy.springintro.dto.shoppingcart;

import jakarta.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequestDto {
    @NotNull
    private Long bookId;

    @Min(1)
    private int quantity;
}
