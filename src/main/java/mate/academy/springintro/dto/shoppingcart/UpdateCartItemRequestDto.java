package mate.academy.springintro.dto.shoppingcart;

import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartItemRequestDto {
    @Min(1)
    private int quantity;
}
