package mate.academy.springintro.mapper;

import mate.academy.springintro.dto.shoppingcart.CartItemResponseDto;
import mate.academy.springintro.model.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItemResponseDto cartItemToCartItemResponseDto(CartItem cartItem);
}
