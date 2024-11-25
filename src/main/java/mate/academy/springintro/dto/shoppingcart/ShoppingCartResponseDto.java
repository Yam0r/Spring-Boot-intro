package mate.academy.springintro.dto.shoppingcart;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import mate.academy.springintro.model.ShoppingCart;

@Getter
@Setter
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private List<CartItemResponseDto> cartItems;

    public ShoppingCartResponseDto(ShoppingCart shoppingCart) {
    }
}
